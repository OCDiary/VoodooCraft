package mdc.voodoocraft.hexes;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class HexFreeze extends HexEntry{
    public HexFreeze() {
        super("freeze");
    }

    @Override
    public ItemStack activeUse(ItemStack stackIn, World world, EntityPlayer player, EnumHand hand, int strength, @Nullable EntityLivingBase target)
    {
        if(world.isRemote || target == null)
            return super.activeUse(stackIn, world, player, hand, strength, target);

        //TODO: This probably needs to add a potion effect for the freezing instead
        if(target.motionX != 0 || (target.motionY + target.getYOffset()) != 0 || target.motionZ != 0){
            target.motionX = 0;
            target.motionY = 0;
            target.motionZ = 0;
        }

        return super.activeUse(stackIn, world, player, hand, strength, target);
    }
}
