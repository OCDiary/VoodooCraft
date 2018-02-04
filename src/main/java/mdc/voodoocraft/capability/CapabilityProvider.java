package mdc.voodoocraft.capability;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nullable;

public class CapabilityProvider<C extends ICapability> implements ICapabilitySerializable<NBTTagCompound>
{
    protected C capI;
    protected Capability<? extends ICapability> capability;

    public CapabilityProvider(Capability<C> capability)
    {
        this.capI = capability.getDefaultInstance();
        this.capability = capability;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
    {
        return capability == this.capability;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
    {
        return hasCapability(capability, facing) ? (T) capI : null;
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        return capI.serializeNBT();
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
        capI.deserializeNBT(nbt);
    }
}
