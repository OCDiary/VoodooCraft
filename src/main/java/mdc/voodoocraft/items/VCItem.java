package mdc.voodoocraft.items;

import mdc.voodoocraft.VCTabs;
import net.minecraft.item.Item;

public class VCItem extends Item
{
    public VCItem(String name)
    {
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(VCTabs.VOODOO_TAB);
    }
}
