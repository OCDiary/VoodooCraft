package mdc.voodoocraft.proxy;

import mdc.voodoocraft.commands.*;
import mdc.voodoocraft.config.*;
import mdc.voodoocraft.handlers.NetworkHandler;
import mdc.voodoocraft.init.VCAchievements;
import mdc.voodoocraft.init.*;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.*;

import java.util.Random;

public class CommonProxy
{
    public void preInit(FMLPreInitializationEvent e)
    {
    	VoodooConfig.init(e.getSuggestedConfigurationFile());
    	VCHexes.init();
        VCEntities.init();
        VCSoundHandler.init();
        VCBlocks.registerTileEntities();
        VCCapabilities.init();

        NetworkHandler.init();
    }

    public void init(FMLInitializationEvent e)
    {
        VCAchievements.registerAchievements();
        VCRecipes.init();
    }

    public void postInit(FMLPostInitializationEvent e){
    	
    }
    
    public void serverStarting(FMLServerStartingEvent e) {
    	e.registerServerCommand(new CommandVCHelp());
    }
    
    public void serverStopping(FMLServerStoppingEvent e) {
    	
    }

    public void spawnParticle(World world, EnumParticleTypes type, Double posX, Double posY, Double posZ) {
    }
}
