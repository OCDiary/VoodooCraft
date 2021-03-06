package mdc.voodoocraft.blocks;

import mdc.voodoocraft.tile.TileDollPedestal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDollPedestal extends VCModelBlock implements ITileEntityProvider
{
	private final static AxisAlignedBB hitbox = new AxisAlignedBB(0.1875, 0.0D, 0.1875D, 0.8125D, 0.875D, 0.8125D);
	//private final static AxisAlignedBB aboveBlock = new AxisAlignedBB(0.1875, 0.0D, 0.1875D, 0.8125D, 1.875D, 0.8125D);
	
	public BlockDollPedestal()
	{
		super("dollpedestal");
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return hitbox;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileDollPedestal();
	}

	/**
	 * Puts dolls in and out of the pedestal.
	 */
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		TileDollPedestal tile = (TileDollPedestal) world.getTileEntity(pos);
		if(tile == null) return false;
		ItemStack heldItem = player.getHeldItem(hand);
		ItemStack dollStack = tile.getDollStack();
		if(dollStack.isEmpty())
		{
			//Add the held doll to the pedestal
			if(!heldItem.isEmpty() && tile.putDollStack(heldItem, player))
				player.setHeldItem(hand, ItemStack.EMPTY);
		}
		else
		{
			//Remove the doll from the pedestal
			if(heldItem.isEmpty())
				player.setHeldItem(hand, dollStack);
			else if(!player.inventory.addItemStackToInventory(dollStack))
				player.dropItem(dollStack, true);
			tile.clearDollStack();
		}
        return true;
    }

	/**
	 * Drops item in the pedestal
	 */
	public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
		TileDollPedestal tile = (TileDollPedestal) world.getTileEntity(pos);
		if(tile != null)
		{
			ItemStack stack = tile.getDollStack();
			if(stack != null) InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack);
		}
		super.breakBlock(world, pos, state);
    }

	/*
	public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }
    */
}
