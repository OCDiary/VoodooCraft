package mdc.voodoocraft.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TestItem extends VCItem{
    public TestItem() {
        super("testitem");
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
        World world = playerIn.world;
        BlockPos pos = target.getPosition();
        EntityVillager villager = new EntityVillager(world);
        EntityZombie zombie = new EntityZombie(world);

        if(!world.isRemote) {
            if (target instanceof EntityZombie) {
                villager.setPosition(pos.getX(), pos.getY(), pos.getZ());
                world.spawnEntity(villager);
                target.setDead();
                return true;
            }else if(target instanceof EntityVillager){
                zombie.setPosition(pos.getX(), pos.getY(), pos.getZ());
                world.spawnEntity(zombie);
                target.setDead();
                return true;
            }
        }
        return false;
    }
}