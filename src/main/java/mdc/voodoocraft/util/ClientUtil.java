package mdc.voodoocraft.util;

import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ClientUtil
{
    public static void spawnParticle(World world, EnumParticleTypes type, BlockPos pos)
    {
        world.spawnParticle(type, pos.getX(), pos.getY(), pos.getZ(), 0D, 0D, 0D);
    }
}
