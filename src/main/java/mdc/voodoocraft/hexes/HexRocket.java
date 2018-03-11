package mdc.voodoocraft.hexes;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class HexRocket extends HexEntry{
    public HexRocket(){
        super("rocket");
    }

    @Override
    public ItemStack activeUse(ItemStack stackIn, World world, EntityPlayer player, EnumHand hand, int strength, @Nullable EntityLivingBase target)
    {
        if(!world.isRemote && target instanceof EntityLiving)
            target.motionY += world.rand.nextDouble();
        return super.activeUse(stackIn, world, player, hand, strength, target);
    }
}
