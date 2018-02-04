package mdc.voodoocraft.hexes;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class HexHeat extends HexEntry{
    public HexHeat() {
        super("heat_protection");
    }

    @Override
    public ItemStack activeUse(ItemStack stackIn, World world, EntityPlayer player, int strength, @Nullable EntityLivingBase target) {
        if(world.isRemote) return super.activeUse(stackIn, world, player, strength, target);

        player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 1000));

        return super.activeUse(stackIn, world, player, strength, target);
    }
}
