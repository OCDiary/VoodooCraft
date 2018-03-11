package mdc.voodoocraft.items;

import mdc.voodoocraft.VoodooCraft;
import net.minecraft.item.Item;

public class VCItem extends Item
{
    public VCItem(String name)
    {
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(VoodooCraft.VOODOO_TAB);
    }
}
