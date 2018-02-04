package mdc.voodoocraft.hexes;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber
public class HexEntry
{
	private String unlocalizedName;
    
    public HexEntry(String name)
    {
    	unlocalizedName = name;
    }

    @Nonnull
    @SideOnly(Side.CLIENT)
    public String getDescription()
    {
        return I18n.format("desc." + getUnlocalizedName());
    }
    
    @Nonnull
    public String getUnlocalizedName()
    {
    	return "hex." + unlocalizedName + ".name";
    }

    public String getRawName()
    {
        return unlocalizedName;
    }
    
    @SideOnly(Side.CLIENT)
    public String getLocalisedName()
    {
    	return I18n.format(getUnlocalizedName());
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
	public ItemStack activeUse(ItemStack stackIn, World world, EntityPlayer player, int strength, @Nullable EntityLivingBase target)
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
