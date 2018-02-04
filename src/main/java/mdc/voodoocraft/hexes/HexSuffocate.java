package mdc.voodoocraft.hexes;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class HexSuffocate extends HexEntry
{
    public HexSuffocate() {
        super("suffocate");
    }

    @Override
    public ItemStack activeUse(ItemStack stackIn, World world, EntityPlayer player, int strength, @Nullable EntityLivingBase target)
    {
        if(world.isRemote) return super.activeUse(stackIn, world, player, strength, target);

        if(target instanceof EntityPlayer)
        {
            EntityPlayer playerTarget = (EntityPlayer) target;
            if(!playerTarget.capabilities.isCreativeMode)
            {
                int dif = world.getDifficulty().getDifficultyId();
                playerTarget.attackEntityFrom(DamageSource.inWall, dif);
            }
        }

        return super.activeUse(stackIn, world, player, strength, target);
    }
}
