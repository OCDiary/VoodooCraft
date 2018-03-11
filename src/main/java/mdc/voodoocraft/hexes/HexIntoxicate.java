package mdc.voodoocraft.hexes;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class HexIntoxicate extends HexEntry {
    public HexIntoxicate(){
        super("intoxicate");
    }

    @Override
    public ItemStack activeUse(ItemStack stackIn, World world, EntityPlayer player, EnumHand hand, int strength, @Nullable EntityLivingBase target)
    {
        if(!world.isRemote && target != null)
        {
            target.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 1000, 3));
            target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 1000, 3));
            target.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 1000, 3));
        }
        return super.activeUse(stackIn, world, player, hand, strength, target);
    }
}
