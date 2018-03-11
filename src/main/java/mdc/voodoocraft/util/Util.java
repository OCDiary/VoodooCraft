package mdc.voodoocraft.util;

import net.minecraft.util.IThreadListener;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.server.FMLServerHandler;

public class Util
{
    public static IThreadListener getMainThread(World world)
    {
        if(world.isRemote)
            return FMLClientHandler.instance().getClient();
        else
            return FMLServerHandler.instance().getServer();
    }
}
