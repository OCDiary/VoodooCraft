package mdc.voodoocraft.blocks;

import java.util.List;

import mdc.voodoocraft.init.VCItems;
import mdc.voodoocraft.items.ItemChalk;
import mdc.voodoocraft.util.EnumGlyphType;
import mdc.voodoocraft.util.NBTHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BlockGlyph extends VCModelBlock{

	public static final AxisAlignedBB hitbox = new AxisAlignedBB(0.0, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D);
	public static final PropertyEnum<EnumGlyphType> TYPE = PropertyEnum.create("type", EnumGlyphType.class); 
	
	public BlockGlyph()
	{
		super("glyph", Material.CIRCUITS);
		setHardness(0);
		setDefaultState(blockState.getBaseState().withProperty(TYPE, EnumGlyphType.BASIC));
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, TYPE);
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
	{
		ItemStack stack = placer.getHeldItem(hand);
		return stack.getItem() == VCItems.CHALK_BASIC ?
				getDefaultState().withProperty(TYPE, EnumGlyphType.byIndex(NBTHelper.getTagCompound(stack).getInteger(ItemChalk.KEY_GLYPH))) :
				getDefaultState();
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(TYPE, EnumGlyphType.byIndex(meta));
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(TYPE).ordinal();
	}
	
	public IBlockState setGlyphType(EnumGlyphType type) {
		return this.getDefaultState().withProperty(TYPE, type);
	}
	
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return super.canPlaceBlockAt(worldIn, pos) && this.canBlockStay(worldIn, pos);
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
	{
		if(!canBlockStay(worldIn, pos))
			worldIn.destroyBlock(pos, false);
	}

	public boolean canBlockStay(World worldIn, BlockPos pos)
    {
        return worldIn.isSideSolid(pos.down(), EnumFacing.UP, false);
    }
	
	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
		return null;
    }
	
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return hitbox;
	}

	@Nullable
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
	{
		return NULL_AABB;
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		ItemStack stack = new ItemStack(VCItems.CHALK_BASIC);
		NBTTagCompound nbt = NBTHelper.getTagCompound(stack);
		nbt.setInteger(ItemChalk.KEY_GLYPH, state.getValue(TYPE).ordinal());
		stack.setTagCompound(nbt);
		return stack;
	}
}
