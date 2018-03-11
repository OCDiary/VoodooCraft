package mdc.voodoocraft.handlers;

import mdc.voodoocraft.VoodooCraft;
import mdc.voodoocraft.VoodooConfig;
import mdc.voodoocraft.init.VCBlocks;
import mdc.voodoocraft.tile.TileDeathGlyph;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.*;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.event.entity.*;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.eventhandler.*;

@Mod.EventBusSubscriber
public class VCEventHandler {

	@SubscribeEvent
	public static void displayWelcomeChat(EntityJoinWorldEvent event)
    {
    	//TODO: This displays when the player dies
		if(event.getEntity().world.isRemote && event.getEntity() instanceof EntityPlayer)
		{
            EntityPlayer player = (EntityPlayer) event.getEntity();
            player.sendMessage(new TextComponentString(TextFormatting.GOLD + "Welcome to VoodooCraft by ModDev-Cafe Modding Team!"));
            player.sendMessage(new TextComponentString(TextFormatting.GOLD + "For Tips and Tricks About the Mod"));
            player.sendMessage(new TextComponentString(TextFormatting.GOLD + "Use the Following Command: "));
            player.sendMessage(new TextComponentString(TextFormatting.GOLD + "/vdcraft help <feature>"));
		}
	}

	/**
	 * Spawn a death glyph where (or near) the player has died which stores their items
	 */
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onPlayerDeath(LivingDeathEvent event)
	{
		if(!(event.getEntityLiving() instanceof EntityPlayer)) return;
		EntityPlayer player = (EntityPlayer) event.getEntityLiving();
		BlockPos playerPos = player.getPosition();
		World world = player.world;

		if(playerPos.getY() < 0)
			playerPos = new BlockPos(playerPos.getX(), 5, playerPos.getZ());

		//Try spawn where the player is
		if(canPlaceGlyph(world, playerPos) && placeGlyph(world, playerPos, player))
			return;

		//If in liquid, spawn on surface
		if(world.getBlockState(playerPos).getBlock() instanceof BlockLiquid)
		{
			BlockPos pos = new BlockPos(playerPos);
			do
				pos = pos.up();
			while(world.getBlockState(pos).getBlock() instanceof BlockLiquid && pos.getY() < 255);

			BlockPos closestPos = findClosestEmpty(world, pos, 1);
			if(closestPos != null && placeGlyph(world, closestPos, player))
				return;
		}

		//Find closest space in 5x5x5
		BlockPos closestPos = findClosestEmpty(world, playerPos, 2);
		if(closestPos != null && placeGlyph(world, closestPos, player))
			return;

		//Try find closest space on surface in 5x5
		closestPos = findClosestSurface(world, playerPos, 2);
		if(closestPos != null && placeGlyph(world, closestPos, player))
			return;

		//Try replace the block where the player was standing
		if(VoodooConfig.replaceOnDeath && placeGlyph(world, playerPos, player))
			return;

		player.sendMessage(new TextComponentString(TextFormatting.RED + "Death Glyph couldn't be placed!"));
	}

	/**
	 * Searches an area of a defined radius around the playerPos for the closest space which a glyph can be placed
	 */
	private static BlockPos findClosestEmpty(World world, BlockPos playerPos, int radius)
	{
		BlockPos closest = null;
		double closestDist = Double.MAX_VALUE;
		BlockPos closestEmpty = null;
		double closestEmptyDist = Double.MAX_VALUE;

		for(int x = -radius; x <= radius; x++)
			for(int z = -radius; z <= radius; z++)
				for(int y = -radius; y <= radius; y++)
				{
					BlockPos pos = playerPos.add(x, y, z);
					double distance = playerPos.getDistance(pos.getX(), pos.getY(), pos.getZ());
					if(canPlaceGlyph(world, pos) && distance < closestDist)
					{
						closest = pos;
						closestDist = distance;
					}

					//Check for a replaceable block below for a fallback position
					BlockPos posDown = pos.down();
					double distanceDown = playerPos.getDistance(posDown.getX(), posDown.getY(), posDown.getZ());
					if(canPlaceGlyph(world, pos, false) && isReplaceable(world, posDown) && distanceDown < closestEmptyDist)
					{
						closestEmpty = pos;
						closestEmptyDist = distanceDown;
					}
				}
		return closest != null ? closest : closestEmpty;
	}

	private static BlockPos findClosestSurface(World world, BlockPos playerPos, int radius)
	{
		BlockPos surfacePos = world.getTopSolidOrLiquidBlock(playerPos);
		BlockPos closest = null;
		double closestDist = Double.MAX_VALUE;
		for(int x = -radius; x <= radius; x++)
			for(int z = -radius; z <= radius; z++)
			{
				BlockPos pos = surfacePos.add(x, 0, z);
				double distance = surfacePos.getDistance(pos.getX(), pos.getY(), pos.getZ());
				if(canPlaceGlyph(world, pos) && distance < closestDist)
				{
					closest = pos;
					closestDist = distance;
				}
				else
				{
					//Just check up and down a couple of blocks incase of y level difference
					for(int y = -2; y <= 2; y++)
					{
						pos = surfacePos.add(x, y, z);
						distance = surfacePos.getDistance(pos.getX(), pos.getY(), pos.getZ());
						if(canPlaceGlyph(world, pos) && distance < closestDist)
						{
							closest = pos;
							closestDist = distance;
						}
					}
				}
			}
		return closest;
	}

	/**
	 * Checks if the position contains air or a replaceable block
	 */
	private static boolean isReplaceable(World world, BlockPos pos)
	{
		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		return block.isAir(state, world, pos) || block.isReplaceable(world, pos);
	}

	/**
	 * Tries to place the glyph and returns whether successful
	 */
	private static boolean placeGlyph(World world, BlockPos pos, EntityPlayer player)
	{
		BlockSnapshot snapshot = BlockSnapshot.getBlockSnapshot(world, pos.down());
		if(isReplaceable(world, pos.down()))
			world.setBlockState(pos.down(), Blocks.DIRT.getDefaultState());
		if(world.setBlockState(pos, VCBlocks.DEATH_GLYPH.getDefaultState()))
		{
			TileEntity te = world.getTileEntity(pos);
			if(te == null || !(te instanceof TileDeathGlyph))
			{
				VoodooCraft.LOGGER.error("Failed to get a tile entity for the Death Glyph! Player's inventory not saved!");
				return true;
			}
			((TileDeathGlyph) te).savePlayerInventory(player);
			player.sendMessage(new TextComponentString("Death Glyph placed at " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ()));
			return true;
		}
		else
			snapshot.restore(true, false);
		return false;
	}

	/**
	 * Returns whether the position is suitable to place a glyph at
	 */
	private static boolean canPlaceGlyph(World world, BlockPos pos)
	{
		return canPlaceGlyph(world, pos, true);
	}

	private static boolean canPlaceGlyph(World world, BlockPos pos, boolean checkBlockBelow)
	{
		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		return (block.isAir(state, world, pos) || block.isReplaceable(world, pos)) && !(block.getMaterial(state) instanceof MaterialLiquid) && (checkBlockBelow || world.getBlockState(pos.down()).isFullCube());
	}
}