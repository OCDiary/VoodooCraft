package mdc.voodoocraft.hexes;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class HexSuffocate extends HexEntry
{
    public HexSuffocate() {
        super("suffocate");
    }

    @Override
    public ItemStack activeUse(ItemStack stackIn, World world, EntityPlayer player, EnumHand hand, int strength, @Nullable EntityLivingBase target)
    {
        if(!world.isRemote && target instanceof EntityPlayer)
        {
            EntityPlayer playerTarget = (EntityPlayer) target;
            if(!playerTarget.capabilities.isCreativeMode)
            {
                int dif = world.getDifficulty().getDifficultyId();
                playerTarget.attackEntityFrom(DamageSource.IN_WALL, dif);
            }
        }

        return super.activeUse(stackIn, world, player, hand, strength, target);
    }
}
