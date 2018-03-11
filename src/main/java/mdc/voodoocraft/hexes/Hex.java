package mdc.voodoocraft.hexes;

import javax.annotation.Nullable;

import mdc.voodoocraft.init.VCHexes;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;

public class Hex implements INBTSerializable<NBTTagCompound>
{
	private HexEntry entry;
	private int strength;

	public Hex(HexEntry entry)
	{
		this(entry, 0);
	}

	public Hex(HexEntry entry, int strength)
	{
		this.entry = entry;
		this.strength = strength;
	}

	public Hex(NBTTagCompound nbt)
	{
		deserializeNBT(nbt);
	}

	public HexEntry getEntry()
	{
		return entry;
	}

	public int getStrength()
	{
		return strength;
	}

	/**
	 * Get this hex's additional cost multiplier, or 0 if no cost is defined.
	 */
	public int getCost()
	{
		return getEntry().getCost(getStrength());
	}

	public ItemStack activeUse(ItemStack stack, World world, EntityPlayer player, EnumHand hand, @Nullable EntityLivingBase target)
	{
		return entry.activeUse(stack, world, player, hand, getStrength(), target);
	}

	@Override
	public NBTTagCompound serializeNBT()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setString("entry", entry.getName());
		nbt.setInteger("strength", strength);
		return nbt;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt)
	{
		entry = VCHexes.getHex(nbt.getString("entry"));
		strength = nbt.getInteger("strength");
	}
}
