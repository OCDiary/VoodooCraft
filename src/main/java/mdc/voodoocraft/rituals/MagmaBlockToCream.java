package mdc.voodoocraft.rituals;

import mdc.voodoocraft.blocks.BlockGlyph;
import mdc.voodoocraft.init.VCBlocks;
import mdc.voodoocraft.util.EnumGlyphType;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class MagmaBlockToCream {

	@SubscribeEvent
	public static void rightClickBlock(PlayerInteractEvent e)
	{
		EntityPlayer player = e.getEntityPlayer();
		if(!e.getWorld().isRemote && player.getHeldItem(e.getHand()) == null)
		{
			World world = e.getWorld();
			BlockPos pos = e.getPos();
			if(world.getBlockState(pos).getBlock() == Blocks.MAGMA && hasGlyphs(world, pos))
			{
				world.setBlockToAir(pos); //TODO: custom sound
				EntityItem entitem = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(Items.MAGMA_CREAM, 2));
				world.spawnEntity(entitem);
			}
		}
	}
	/**
	 * Checks for a 3x3 area, x = glyph, z=magma block <br>
	 * zxz <br>
	 * xzx <br>
	 * zxz <br>
	 * <p>
	 * Pos given is the middle magma block, and it is the only one consumed.
	 * @param world
	 * @param pos
	 * @return
	 */
	public static boolean hasGlyphs(World world, BlockPos pos)
	{
		Block magma = Blocks.MAGMA;
		return (world.getBlockState(pos.east()).getValue(BlockGlyph.TYPE)==EnumGlyphType.FIRE&&
				world.getBlockState(pos.west()).getValue(BlockGlyph.TYPE)==EnumGlyphType.FIRE&&
				world.getBlockState(pos.north()).getValue(BlockGlyph.TYPE)==EnumGlyphType.FIRE&&
				world.getBlockState(pos.south()).getValue(BlockGlyph.TYPE)==EnumGlyphType.FIRE&&
				world.getBlockState(pos.south().east()).getBlock()==magma&&
				world.getBlockState(pos.south().west()).getBlock()==magma&&
				world.getBlockState(pos.north().east()).getBlock()==magma&&
				world.getBlockState(pos.north().west()).getBlock()==magma);
	}
}
