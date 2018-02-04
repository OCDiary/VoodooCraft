package mdc.voodoocraft.hexes;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import mdc.voodoocraft.config.VoodooConfig;
import mdc.voodoocraft.init.VCAchievements;
import mdc.voodoocraft.init.VCHexes;
import mdc.voodoocraft.items.ItemDoll;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Hex
{
	private static final String KEY_COMPOUND_TAG = "compoundTag";
	private static final String KEY_COST = "cost";
	private static final String KEY_NAME = "hex_entry";
	private static final String KEY_STRENGTH = "strength";
	private static final String KEY_DISPLAY_NAME = "display_name";

	private HexEntry entry;
	private NBTTagCompound hexNBT;
	
	//internal use only!
	private Hex()
	{
		hexNBT = new NBTTagCompound();
		hexNBT.setInteger(KEY_STRENGTH, 0);
	}
	
	public Hex(String name, int strength)
	{
		this(VCHexes.getHex(name), strength);
	}
	
	public Hex(String name)
	{
		this(VCHexes.getHex(name));
	}
	
	public Hex(HexEntry entry)
	{
		this(entry, 0);
	}
	
	public Hex(HexEntry entry, int strength)
	{
		this();
		this.entry = entry;
		hexNBT.setInteger(KEY_STRENGTH, strength);
	}
	
	public Hex(NBTTagCompound compoundTag)
	{
		this();
		if(compoundTag.hasKey(KEY_NAME, NBT.TAG_STRING)) this.entry = VCHexes.getHex(compoundTag.getString(KEY_NAME));
		if(compoundTag.hasKey(KEY_COMPOUND_TAG, NBT.TAG_COMPOUND)) setTagCompound(compoundTag.getCompoundTag(KEY_COMPOUND_TAG));
	}
	
	@Nullable
	public HexEntry getEntry()
	{
		return entry;
	}
	
	@Nonnull
	public NBTTagCompound getTagCompound()
	{
		return hexNBT;
	}
	
	public boolean hasTagCompound()
	{
		return !hexNBT.hasNoTags();
	}
	
	public NBTTagCompound writeToNBT()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		if(entry != null)
		{
			nbt.setString(KEY_NAME, this.entry.getRawName());
			nbt.setTag(KEY_COMPOUND_TAG, this.getTagCompound());
		}
		return nbt;
	}
	
	public void setTagCompound(NBTTagCompound nbt)
	{
		hexNBT = nbt;
	}

	@Nullable
	public String getDescription()
	{
		if(entry != null) return entry.getDescription();
		return null;
	}
	
	/**
	 * @return the hex's strength
	 */
	public int getStrength()
	{
		return hexNBT.getInteger(KEY_STRENGTH);
	}
	
	/**
     * Get this hex's additional cost multiplier, or 0 if no cost is defined.
     */
    public int getCost()
    {
    	if(hasTagCompound() && getTagCompound().hasKey(KEY_COST, NBT.TAG_INT))
    		return getTagCompound().getInteger(KEY_COST);
    	if(getEntry() != null)
    		return getEntry().getCost(getStrength());
    	return 0;
    }

    /**
     * Set this hex's repair cost.
     */
    public void setCost(int cost) //do we need this? ~Upcraft
    {
        if (!hasTagCompound())
            hexNBT = new NBTTagCompound();

        hexNBT.setInteger(KEY_COST, cost);
    }

    public static boolean isHexEqualTo(ItemStack stack1, ItemStack stack2)
	{
    	if((stack1.getItem() instanceof ItemDoll) && (stack2.getItem() instanceof ItemDoll))
    	{
    		if(stack1.hasTagCompound() && stack2.hasTagCompound())
				return stack1.getTagCompound().getCompoundTag(KEY_NAME) == stack2.getTagCompound().getCompoundTag(KEY_NAME);
			else
    			return false;
		}
		return false;
	}
    
    /**
	 * @return the formatted name of this Hex's {@link HexEntry} for simplification
	 */
	@SideOnly(Side.CLIENT)
	@Nullable
	public String getLocalisedName()
	{
		if(hasCustomDisplayName()) return getDisplayName();
		if(entry != null) return entry.getLocalisedName();
		return null;
	}
	
	public boolean hasCustomDisplayName()
	{
		return !getDisplayName().equals("");
	}
	
	@Nonnull
	public String getDisplayName()
	{
		return hexNBT.getString(KEY_DISPLAY_NAME);
	}
	
	public void setDisplayName(String name)
	{
		hexNBT.setString(KEY_DISPLAY_NAME, name);
	}

	public ItemStack activeUse(ItemStack stack, World world, EntityPlayer player, @Nullable EntityLivingBase target)
	{
		player.addStat(VCAchievements.achievementHexFirstUse);
		return entry.activeUse(stack, world, player, getStrength(), target);
	}

	public ItemStack passiveUse(ItemStack stack, World world, EntityPlayer player)
	{
		return entry.passiveUse(stack, world, player, getStrength());
	}
}
