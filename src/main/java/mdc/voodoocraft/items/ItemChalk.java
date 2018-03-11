package mdc.voodoocraft.items;

import java.util.List;

import mdc.voodoocraft.init.VCBlocks;
import mdc.voodoocraft.util.EnumGlyphType;
import mdc.voodoocraft.util.NBTHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
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

import javax.annotation.Nullable;

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
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		int glyph_index = NBTHelper.getTagCompound(stack).getInteger(KEY_GLYPH);
		tooltip.add(I18n.format("type.glyph." + glyph_index + ".name"));
	}

	/**
	 * Changes glyphtype NBT, cycles through all in the {@link EnumGlyphType}
	 */
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		ItemStack stack = player.getHeldItem(hand);
		if (player.isSneaking()) {
			NBTTagCompound nbt = NBTHelper.getTagCompound(stack);
			EnumGlyphType newtype = EnumGlyphType.byIndex(nbt.getInteger(KEY_GLYPH)).next();
			nbt.setInteger(KEY_GLYPH, newtype.ordinal());
			stack.setTagCompound(nbt);
			return new ActionResult<>(EnumActionResult.SUCCESS, stack);
		}
		return super.onItemRightClick(world, player, hand);
	}

	/**
	 * Places Glyph on block you right click
	 */
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		ItemStack stack = player.getHeldItem(hand);
		if (facing != EnumFacing.UP || !worldIn.isSideSolid(pos, facing, false))
			return EnumActionResult.PASS;
		if (worldIn.isAirBlock(pos.up()) && worldIn.isSideSolid(pos, EnumFacing.UP) && !worldIn.isRemote) {
			worldIn.setBlockState(pos.up(), VCBlocks.GLYPH.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, stack.getMetadata(), player, hand));
			stack.damageItem(1, player);
			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.PASS;
	}
}
