package mdc.voodoocraft.rituals;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import mdc.voodoocraft.blocks.BlockGlyph;
import mdc.voodoocraft.init.VCBlocks;
import mdc.voodoocraft.util.EnumGlyphType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class SandToSoulsand {

	@SubscribeEvent
	public static void rightClickBlock(PlayerInteractEvent e)
	{
		World world = e.getWorld();
		if(!world.isRemote && e.getEntityPlayer().getHeldItem(e.getHand()) == null)
		{
			BlockPos pos = e.getPos();
			if(world.getBlockState(pos).getBlock()==VCBlocks.GLYPH && world.getBlockState(pos).getValue(BlockGlyph.TYPE) == EnumGlyphType.BASIC)
			{
				for(EntityItem entity : e.getWorld().getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos.add(-1, 0, -1), pos.add(1, 1, 1))))
				{
					ItemStack stack = entity.getEntityItem();
					if(stack.getItem() == Item.getItemFromBlock(Blocks.NETHERRACK))
					{
						stack.stackSize--;
						entity.setEntityItemStack(stack);
						setSandBelowToSoul(world, pos);
						break;
					}
				}
			}
		}	
	}
	
	public static void setSandBelowToSoul(World world, BlockPos pos)
	{
		List<BlockPos> list = new ArrayList<BlockPos>();
		list.addAll(threebyThreeAroundPos(pos.down()));
		list.addAll(threebyThreeAroundPos(pos.down(2)));
		list.addAll(threebyThreeAroundPos(pos.down(3)));
		
		for(BlockPos bPos : list)
		{
			if(world.getBlockState(bPos).getBlock()==Blocks.SAND)
			{
				world.setBlockState(bPos, Blocks.SOUL_SAND.getDefaultState());
			}
		}
	}
	public static List<BlockPos> threebyThreeAroundPos(BlockPos pos)
	{
		return Lists.<BlockPos>newArrayList(
				pos,
				pos.east(),
				pos.east().north(),
				pos.east().south(),
				pos.west(),
				pos.west().north(),
				pos.west().south(),
				pos.north(),
				pos.south()
				);
	}
}
