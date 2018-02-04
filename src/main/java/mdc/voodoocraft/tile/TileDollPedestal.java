package mdc.voodoocraft.tile;

import mdc.voodoocraft.items.ItemDoll;
import mdc.voodoocraft.util.HexHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileDollPedestal extends TileEntity implements ITickable
{
    private ItemStackHandler itemStackHandler = new ItemStackHandler(1);
    private EntityPlayer player;

	public TileDollPedestal()
    {
        super();
    }

    public ItemStack getDollStack()
    {
        return itemStackHandler.getStackInSlot(0);
    }

    public boolean putDollStack(ItemStack stack, EntityPlayer player)
    {
        if(getDollStack() != null || stack == null || !(stack.getItem() instanceof ItemDoll)) return false;
        this.player = player;
        markDirty();
        return itemStackHandler.insertItem(0, stack, false) == null;
    }

    public void clearDollStack()
    {
        itemStackHandler.setStackInSlot(0, null);
        player = null;
        markDirty();
    }

	/**
	 * Should be used to tick the doll inside of it.
	 */
	@Override
	public void update()
	{
		if(world.isRemote) return;
		ItemStack stack = itemStackHandler.getStackInSlot(0);
		if(stack != null && stack.getItem() instanceof ItemDoll)
        {
            //Tick the doll inside
            HexHelper.activatePassive(stack, world, player);
            //Check doll damage
            if(stack.stackSize <= 0) clearDollStack();
            markDirty();
        }
	}

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        if(compound.hasKey("items"))
            itemStackHandler.deserializeNBT((NBTTagCompound) compound.getTag("items"));
        if(compound.hasKey("uuid"))
            player = world.getPlayerEntityByUUID(NBTUtil.getUUIDFromTag(compound.getCompoundTag("uuid"))); //TODO: Throwing NPE
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound.setTag("items", itemStackHandler.serializeNBT());
        if(player != null)
            compound.setTag("uuid", NBTUtil.createUUIDTag(player.getUniqueID()));
        return super.writeToNBT(compound);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @SuppressWarnings("unchecked") //need this so eclipse doesn't complain
	@Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return (T) itemStackHandler;
        return super.getCapability(capability, facing);
    }

}
