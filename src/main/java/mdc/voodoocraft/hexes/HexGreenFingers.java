package mdc.voodoocraft.hexes;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class HexGreenFingers extends HexEntry{
    public HexGreenFingers() {
        super("green_fingers");
    }

    @Override
    public ItemStack activeUse(ItemStack stackIn, World world, EntityPlayer player, int strength, @Nullable EntityLivingBase target)
    {
        RayTraceResult ray = player.rayTrace(5f, 0);
        if(ray.typeOfHit != RayTraceResult.Type.BLOCK) return super.activeUse(stackIn, world, player, strength, target);
        BlockPos pos = ray.getBlockPos();
        Block block = world.getBlockState(pos).getBlock();

        if(block instanceof IGrowable)
        {
            ((IGrowable) block).grow(world, world.rand, pos, world.getBlockState(pos));
            spawnParticles(world, pos, 15);
        }

        return super.activeUse(stackIn, world, player, strength, target);
    }

    /**
     * TODO: Spawn particles to emulate fertilizer effects
     */
    @SideOnly(Side.CLIENT)
    private void spawnParticles(World world, BlockPos pos, int amount)
    {
        if(world.isRemote) return;
        if(amount == 0){
            amount = 15;
        }

        IBlockState iblockstate = world.getBlockState(pos);

        if(iblockstate.getMaterial() != Material.AIR){
            for(int i = 0; i < amount; ++i){
                double d0 = world.rand.nextGaussian() * 0.02D;
                double d1 = world.rand.nextGaussian() * 0.02D;
                double d2 = world.rand.nextGaussian() * 0.02D;
                world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, (double)((float)pos.getX() + world.rand.nextFloat()), (double)pos.getY() + (double)world.rand.nextFloat() * iblockstate.getBoundingBox(world, pos).maxY, (double)((float)pos.getZ() + world.rand.nextFloat()), d0, d1, d2);
            }
        }else{
            for(int i = 0; i < amount; ++i){
                double d0 = world.rand.nextGaussian() * 0.02D;
                double d1 = world.rand.nextGaussian() * 0.02D;
                double d2 = world.rand.nextGaussian() * 0.02D;
                world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, (double)((float)pos.getX() + world.rand.nextFloat()), (double)pos.getY() + (double)world.rand.nextFloat() * 1.0F, (double)((float)pos.getZ() + world.rand.nextFloat()), d0, d1, d2);
            }
        }
    }
}
