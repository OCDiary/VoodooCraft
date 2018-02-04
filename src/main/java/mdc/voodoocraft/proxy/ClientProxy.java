package mdc.voodoocraft.proxy;

import mdc.voodoocraft.init.VCBlocks;
import mdc.voodoocraft.init.VCEntities;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class ClientProxy extends CommonProxy
{

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
        VCBlocks.registerTileEntityRenders();
    }

    @Override
    public void init(FMLInitializationEvent e)
    {
        super.init(e);
        VCEntities.initRenders();
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
    }
    
    @Override
    public void serverStarting(FMLServerStartingEvent e) {
    	super.serverStarting(e);
    }
    
    @Override
    public void serverStopping(FMLServerStoppingEvent e) {
    	super.serverStopping(e);
    }

    @Override
    public void spawnParticle(World world, EnumParticleTypes type, Double posX, Double posY, Double posZ) {
        Random random = new Random();
        world.spawnParticle(type, false, posX + random.nextDouble(), posY + 0.1D, posZ + random.nextDouble(), 0.001D, 0.002D, 0.001D);
    }
}
