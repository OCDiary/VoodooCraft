package mdc.voodoocraft.items;

import java.util.List;

import mdc.voodoocraft.init.VCBlocks;
import mdc.voodoocraft.util.EnumGlyphType;
import mdc.voodoocraft.util.NBTHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemChalk extends VCItem {

	public static final String KEY_GLYPH = "glyphtype";

	public ItemChalk() {
		super("chalk");
		this.setFull3D();
		this.setMaxStackSize(1);
		this.setMaxDamage(200);
	}

	/**
	 * Shows what type of glyph is on the chalk
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		int glyph_index = NBTHelper.getTagCompound(stack).getInteger(KEY_GLYPH);
		tooltip.add(I18n.format("type.glyph." + glyph_index + ".name"));
	}

	/**
	 * Changes glyphtype NBT, cycles through all in the {@link EnumGlyphType}
	 */
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn,
			EnumHand hand) {
		if (playerIn.isSneaking()) {
			NBTTagCompound nbt = NBTHelper.getTagCompound(itemStackIn);
			EnumGlyphType newtype = EnumGlyphType.byIndex(nbt.getInteger(KEY_GLYPH)).next();
			nbt.setInteger(KEY_GLYPH, newtype.ordinal());
			itemStackIn.setTagCompound(nbt);
			return new ActionResult<>(EnumActionResult.SUCCESS, itemStackIn);
		}
		return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
	}

	/**
	 * Places Glyph on block you right click
	 */
	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (facing != EnumFacing.UP || !worldIn.isSideSolid(pos, facing, false))
			return EnumActionResult.PASS;
		if (worldIn.isAirBlock(pos.up()) && worldIn.isSideSolid(pos, EnumFacing.UP) && !worldIn.isRemote) {
			worldIn.setBlockState(pos.up(), VCBlocks.GLYPH.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ,
					stack.getMetadata(), playerIn, stack));
			stack.damageItem(1, playerIn);
			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.PASS;
	}
}
