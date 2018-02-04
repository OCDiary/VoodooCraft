package mdc.voodoocraft.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class VCModelBlock extends VCBlock {

	public VCModelBlock(String name, Material mat)
	{
		super(name, mat);
	}
	public VCModelBlock(String name)
	{
		super(name, Material.ROCK);
	}
	/**
	 * @return the AABB of the block, <b>not</b> the final AABB in the world.
	 */
	@Override
	public abstract AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos);
	
	@SideOnly(Side.CLIENT)
	@Override
	public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
		return this.getBoundingBox(state, worldIn, pos).offset(pos);
	}
	//Commented out because it was causing doll pedestal to have no physical hitbox.
	/*
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos) {
		return this.getBoundingBox(blockState, worldIn, pos).offset(pos);
	}
	*/
	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
    {
        return false;
    }

	@Override
	public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
}
