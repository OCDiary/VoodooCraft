package mdc.voodoocraft.hexes;

import mdc.voodoocraft.VoodooCraft;
import mdc.voodoocraft.init.VCSoundHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.SkeletonType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class HexSpiritTaint extends HexEntry {
    public HexSpiritTaint() {
        super("spirit_taint");
    }

    @Override
    public ItemStack activeUse(ItemStack stackIn, World world, EntityPlayer player, int strength, @Nullable EntityLivingBase target)
    {
        if(world.isRemote || !(target instanceof EntitySkeleton))
            return super.activeUse(stackIn, world, player, strength, target);

        SkeletonType skeleType = ((EntitySkeleton) target).getSkeletonType();
        if(skeleType != SkeletonType.STRAY)
        {
            EntitySkeleton skeleton = new EntitySkeleton(world);
            skeleton.setSkeletonType(skeleType);
            skeleton.copyLocationAndAnglesFrom(target);
            skeleton.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(skeleton)), null);
            world.removeEntity(target);
            world.spawnEntity(skeleton);
            VoodooCraft.proxy.spawnParticle(world, EnumParticleTypes.EXPLOSION_NORMAL, target.posX, target.posY, target.posZ);
            player.playSound(VCSoundHandler.transformation, 1.0F, 1.0F);
        }

        return super.activeUse(stackIn, world, player, strength, target);
    }
}
