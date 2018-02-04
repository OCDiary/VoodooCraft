package mdc.voodoocraft.hexes;

import mdc.voodoocraft.VoodooCraft;
import mdc.voodoocraft.init.VCSoundHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class HexZombify extends HexEntry {
    public HexZombify(){
        super("zombify");
    }

    @Override
    public ItemStack activeUse(ItemStack stackIn, World world, EntityPlayer player, int strength, @Nullable EntityLivingBase target) {
        if(!world.isRemote) {
            if (target instanceof EntityZombie) {
                EntityVillager villager = new EntityVillager(world);
                villager.copyLocationAndAnglesFrom(target);
                villager.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(villager)), null);
                world.removeEntity(target);
                world.spawnEntity(villager);

            } else if (target instanceof EntityVillager) {
                EntityZombie zombie = new EntityZombie(world);
                zombie.copyLocationAndAnglesFrom(target);
                zombie.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(zombie)), null);
                world.removeEntity(target);
                world.spawnEntity(zombie);
            }
        }

        if(target instanceof EntityZombie || target instanceof EntityVillager)
        {
            VoodooCraft.proxy.spawnParticle(world, EnumParticleTypes.EXPLOSION_NORMAL, target.posX, target.posY, target.posZ);
            player.playSound(VCSoundHandler.transformation, 1.0F, 1.0F);
        }

        return super.activeUse(stackIn, world, player, strength, target);
    }
}
