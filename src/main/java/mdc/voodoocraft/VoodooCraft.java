package mdc.voodoocraft;

import mdc.voodoocraft.handlers.NetworkHandler;
import mdc.voodoocraft.init.VCCapabilities;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = VoodooCraft.MODID, name = VoodooCraft.NAME, version = VoodooCraft.VERSION, acceptedMinecraftVersions = VoodooCraft.MCVERSIONS)
public class VoodooCraft {

    public static final String MODID = "voodoocraft";
    public static final String NAME = "VoodooCraft";
    public static final String VERSION = "@VERSION@";
    public static final String MCVERSIONS = "[1.12.2]";

    @Mod.Instance(VoodooCraft.MODID)
    public static VoodooCraft instance;

    public static Logger LOGGER;

    public static final CreativeTabs VOODOO_TAB = new CreativeTabs(MODID)
    {
        @Override
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem()
        {
            return new ItemStack(Items.SKULL);
        }

        @Override
        public boolean hasSearchBar() {
            return true;
        }
    }.setBackgroundImageName("item_search.png");

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e){
        LOGGER = e.getModLog();
        VCCapabilities.init();
        NetworkHandler.init();
    }
}
