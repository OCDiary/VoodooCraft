package mdc.voodoocraft.hexes;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class HexFreeze extends HexEntry{
    public HexFreeze() {
        super("freeze");
    }

    @Override
    public ItemStack activeUse(ItemStack stackIn, World world, EntityPlayer player, int strength, @Nullable EntityLivingBase target) {
        if(world.isRemote) return super.activeUse(stackIn, world, player, strength, target);

        if(target.motionX != 0 || (target.motionY + target.getYOffset()) != 0 || target.motionZ != 0){
            target.motionX = 0;
            target.motionY = 0;
            target.motionZ = 0;
        }

        return super.activeUse(stackIn, world, player, strength, target);
    }
}
