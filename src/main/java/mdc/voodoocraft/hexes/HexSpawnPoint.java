package mdc.voodoocraft.hexes;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class HexSpawnPoint extends HexEntry{
    public HexSpawnPoint(){
        super("spawnpoint");
    }

    @Override
    public ItemStack activeUse(ItemStack stackIn, World world, EntityPlayer player, EnumHand hand, int strength, @Nullable EntityLivingBase target)
    {
        if(!world.isRemote)
        {
            BlockPos pos = player.getPosition();
            player.setSpawnPoint(new BlockPos(pos.getX(), pos.getY(), pos.getZ()), true);
            player.sendMessage(new TextComponentString("Spawn point set to {x: " + pos.getX() + ", y: " + pos.getY() + ", z: " + pos.getZ() + "} for " + player.getName()));
        }

        return super.activeUse(stackIn, world, player, hand, strength, target);
    }
}
