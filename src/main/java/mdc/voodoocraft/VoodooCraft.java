package mdc.voodoocraft;

import org.apache.logging.log4j.Logger;

import mdc.voodoocraft.proxy.CommonProxy;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION, acceptedMinecraftVersions = Reference.MCVERSIONS, guiFactory = Reference.GUI_FACTORY, canBeDeactivated = false)
public class VoodooCraft {

    @Mod.Instance(Reference.MODID)
    public static VoodooCraft instance;

    @SidedProxy(clientSide = Reference.CPROXY, serverSide = Reference.SPROXY)
    public static CommonProxy proxy;

    public static final Logger log = FMLLog.getLogger();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e){
        log.info("voodoocraft Pre-Init");
        proxy.preInit(e);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e){
        log.info("voodoocraft Init");
        proxy.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e){
        log.info("voodoocraft Post-Init");
        proxy.postInit(e);
    }
    
    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent e) {
    	proxy.serverStarting(e);
    }
    
    @Mod.EventHandler
    public void serverStopping(FMLServerStoppingEvent e) {
    	proxy.serverStopping(e);
    }
}
