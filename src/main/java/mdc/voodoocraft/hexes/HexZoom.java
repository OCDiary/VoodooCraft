package mdc.voodoocraft.hexes;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class HexZoom extends HexEntry{
    public static boolean isZooming = false;

    public HexZoom() {
        super("zoom");
    }

    @Override
    public ItemStack activeUse(ItemStack stackIn, World world, EntityPlayer player, EnumHand hand, int strength, @Nullable EntityLivingBase target) {
        if(world.isRemote)
            //TODO: Not sure, but this might be a bad way of doing this (bright_spark)
            isZooming = !isZooming;

        return super.activeUse(stackIn, world, player, hand, strength, target);
    }
}
