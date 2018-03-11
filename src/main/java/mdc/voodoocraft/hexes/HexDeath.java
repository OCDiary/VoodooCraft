package mdc.voodoocraft.hexes;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class HexDeath extends HexEntry{
    public HexDeath() {
        super("death");
    }

    @Override
    public ItemStack activeUse(ItemStack stackIn, World world, EntityPlayer player, EnumHand hand, int strength, @Nullable EntityLivingBase target)
    {
        if(!world.isRemote && target != null)
            target.setDead();
        return super.activeUse(stackIn, world, player, hand, strength, target);
    }
}
