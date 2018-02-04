package mdc.voodoocraft.hexes;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class HexRocket extends HexEntry{
    public HexRocket(){
        super("rocket");
    }

    @Override
    public ItemStack activeUse(ItemStack stackIn, World world, EntityPlayer player, int strength, @Nullable EntityLivingBase target) {
        if(world.isRemote) return super.activeUse(stackIn, world, player, strength, target);
        if(target instanceof EntityLiving)
            target.motionY += world.rand.nextDouble();
        return super.activeUse(stackIn, world, player, strength, target);
    }
}
