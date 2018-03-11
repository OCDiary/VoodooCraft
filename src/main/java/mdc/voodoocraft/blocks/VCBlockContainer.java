package mdc.voodoocraft.blocks;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;

public abstract class VCBlockContainer extends VCBlock implements ITileEntityProvider
{
    public VCBlockContainer(String name, Material mat)
    {
        super(name, mat);
        hasTileEntity = true;
    }

    public VCBlockContainer(String name)
    {
        this(name, Material.ROCK);
    }
}
