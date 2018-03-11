package mdc.voodoocraft.hexes;

import javax.annotation.Nonnegative;
import javax.annotation.Nullable;

import mdc.voodoocraft.util.HexHelper;
import mdc.voodoocraft.util.Util;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.concurrent.TimeUnit;

@EventBusSubscriber
public class HexEntry extends IForgeRegistryEntry.Impl<HexEntry>
{
	private String unlocalizedName;
    
    public HexEntry(String name)
    {
        setRegistryName(name);
    	unlocalizedName = name;
    }

    @SideOnly(Side.CLIENT)
    public String getDescription()
    {
        return I18n.format("desc." + getUnlocalizedName());
    }
    
    public String getUnlocalizedName()
    {
    	return "hex." + unlocalizedName + ".name";
    }

    public String getName()
    {
        return getRegistryName().getResourcePath();
    }
    
    @SideOnly(Side.CLIENT)
    public String getLocalisedName()
    {
    	return I18n.format(getUnlocalizedName());
    }

    protected String getThreadName(EntityPlayer user)
    {
        return user.getName() + "-" + getName();
    }

    protected boolean runPeriodically(EntityPlayer player, Runnable runnable, int delay, int period, TimeUnit timeUnit, int repetitions)
    {
        //TODO: WIP runPeriodically
        return HexHelper.runPeriodically(Util.getMainThread(player.world), getThreadName(player), runnable, delay, period, timeUnit, repetitions);
    }

    /**
     * @param strength the strength of the {@link Hex}
     * @return the individual damage this single Entry does to the {@link ItemStack}.
     * <br/>will be multiplied with the amount of {@link Hex}es in total.
     * <br/><b>remember: 0 cost means level 1</b>
     */
    @Nonnegative
	public int getCost(int strength) {
		return 0;
	}

    /**
     * Called by the {@link Hex} to execute the active use of this hex
     * @param target null unless an entity has been clicked on
     * @return the ItemStack to update the player's active ItemStack
     */
	public ItemStack activeUse(ItemStack stackIn, World world, EntityPlayer player, EnumHand hand, int strength, @Nullable EntityLivingBase target)
    {
		return stackIn;
	}

    /**
     * Called by the {@link Hex} to execute the passive use of this hex while in a Doll Pedestal
     */
	public ItemStack passiveUse(ItemStack stack, World world, EntityPlayer player, int strength)
    {
        return stack;
    }

}
