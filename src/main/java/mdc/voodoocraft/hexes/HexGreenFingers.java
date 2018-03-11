package mdc.voodoocraft.hexes;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class HexGreenFingers extends HexEntry{
    public HexGreenFingers() {
        super("green_fingers");
    }

    @Override
    public ItemStack activeUse(ItemStack stackIn, World world, EntityPlayer player, EnumHand hand, int strength, @Nullable EntityLivingBase target)
    {
        RayTraceResult ray = player.rayTrace(5f, 0);
        if(ray == null || ray.typeOfHit != RayTraceResult.Type.BLOCK) return super.activeUse(stackIn, world, player, hand, strength, target);
        BlockPos pos = ray.getBlockPos();
        Block block = world.getBlockState(pos).getBlock();

        if(block instanceof IGrowable)
        {
            ((IGrowable) block).grow(world, world.rand, pos, world.getBlockState(pos));
            //Spawn bonemeal particle effects
            if(world.isRemote)
                world.playEvent(2005, pos, 0);
        }

        return super.activeUse(stackIn, world, player, hand, strength, target);
    }
}
