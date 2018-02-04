package mdc.voodoocraft.hexes;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class HexZoom extends HexEntry{
    public static boolean isZooming = false;

    public HexZoom() {
        super("zoom");
    }

    @Override
    public ItemStack activeUse(ItemStack stackIn, World world, EntityPlayer player, int strength, @Nullable EntityLivingBase target) {
        if(!world.isRemote) return super.activeUse(stackIn, world, player, strength, target);

        //TODO: Not sure, but this might be a bad way of doing this (bright_spark)
        isZooming = !isZooming;

        return super.activeUse(stackIn, world, player, strength, target);
    }
}
