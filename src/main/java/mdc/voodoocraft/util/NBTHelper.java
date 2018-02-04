package mdc.voodoocraft.util;

import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class NBTHelper
{
	public static final String KEY_OWNER = "owner";
	public static final String KEY_OWNER_NAME = "owner_name";
	public static final String KEY_IS_PLAYER = "isPlayer";
	
    /**
     * saves the {@link UUID} and {@code NAME} of an entity to an ItemStack.
     * @param stack The {@link ItemStack} to set the {@link NBTTagCompound}
     * @param entity The owner {@link Entity}
     */
    public static void setOwnerTag(ItemStack stack, EntityLivingBase entity)
	{
    	NBTTagCompound stackNBT = getTagCompound(stack);
    	stackNBT.setTag(KEY_OWNER, NBTUtil.createUUIDTag(entity.getUniqueID()));
    	stackNBT.setString(KEY_OWNER_NAME, entity.getName());
    	if(entity instanceof EntityPlayer) stackNBT.setBoolean(KEY_IS_PLAYER, true);
    	stack.setTagCompound(stackNBT);
    	//System.out.println("NBT HAS BEEN SET**************");
	}
	
    @Nullable
	public static Entity getOwner(@Nonnull ItemStack stack)
	{
		NBTTagCompound stackNBT = getTagCompound(stack);
		if(stackNBT.hasKey(KEY_OWNER)) {
			//FIXME: check if player is online!
			return FMLCommonHandler.instance().getMinecraftServerInstance().getEntityFromUuid(NBTUtil.getUUIDFromTag(stackNBT.getCompoundTag(KEY_OWNER)));
		}
		return null;
	}
    
    public static String getOwnerName(ItemStack stack) {
		String name = getTagCompound(stack).getString(KEY_OWNER_NAME);
		return name.equals("") ? "INVALID" : name;
	}
    
    @Nonnull
    public static UUID getOwnerUUID(ItemStack stack) {
    	return NBTUtil.getUUIDFromTag(getTagCompound(stack).getCompoundTag(KEY_OWNER));
    }

	//method for checking and creating an NBTTag
	public static NBTTagCompound getTagCompound(ItemStack stack) {
		NBTTagCompound tag = stack.getTagCompound();
		if (tag == null) {
			tag = new NBTTagCompound();
			stack.setTagCompound(tag);
		}
		return tag;
	}

	

}
