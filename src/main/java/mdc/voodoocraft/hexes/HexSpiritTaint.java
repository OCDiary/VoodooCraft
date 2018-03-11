package mdc.voodoocraft.hexes;

import mdc.voodoocraft.init.VCSoundHandler;
import mdc.voodoocraft.util.ClientUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class HexSpiritTaint extends HexEntry {
    public HexSpiritTaint() {
        super("spirit_taint");
    }

    @Override
    public ItemStack activeUse(ItemStack stackIn, World world, EntityPlayer player, EnumHand hand, int strength, @Nullable EntityLivingBase target)
    {
        if(world.isRemote || !(target instanceof EntitySkeleton))
            return super.activeUse(stackIn, world, player, hand, strength, target);

        EntityWitherSkeleton skeleton = new EntityWitherSkeleton(world);
        skeleton.copyLocationAndAnglesFrom(target);
        skeleton.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(skeleton)), null);
        world.removeEntity(target);
        world.spawnEntity(skeleton);
        ClientUtil.spawnParticle(world, EnumParticleTypes.EXPLOSION_NORMAL, target.getPosition());
        player.playSound(VCSoundHandler.transformation, 1.0F, 1.0F);

        return super.activeUse(stackIn, world, player, hand, strength, target);
    }
}
