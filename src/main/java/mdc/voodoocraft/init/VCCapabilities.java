package mdc.voodoocraft.init;

import mdc.voodoocraft.capability.ICapability;
import mdc.voodoocraft.capability.Storage;
import mdc.voodoocraft.capability.glyphs.CapabilityGlyphs;
import mdc.voodoocraft.capability.glyphs.ICapGlyphs;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class VCCapabilities
{
    @CapabilityInject(ICapGlyphs.class)
    public static Capability<ICapGlyphs> GLYPHS = null;

    private static ArrayList<Capability<? extends ICapability>> CAPABILITIES = new ArrayList<>();

    /**
     * Returns a list of all of the VoodooCraft capabilities
     * Will initialise the list if not done so already
     */
    public static ArrayList<Capability<? extends ICapability>> getCapabilities()
    {
        if(CAPABILITIES.isEmpty())
        {
            //Any new capabilities should be added to the list here
            CAPABILITIES.add(GLYPHS);
        }
        return CAPABILITIES;
    }

    /**
     * Gets the position of the capability in the list
     * Returns -1 if not found
     */
    public static int getCapabilityID(Capability<? extends ICapability> capability)
    {
        return getCapabilities().indexOf(capability);
    }

    /**
     * Gets the capability at the given position in the list
     */
    public static Capability<? extends ICapability> getCapabilityFromID(int ID)
    {
        ArrayList<Capability<? extends ICapability>> capList = getCapabilities();
        if(ID < 0) ID = 0;
        else if(ID >= capList.size()) ID = capList.size() - 1;
        return capList.get(ID);
    }

    /**
     * Helper method for registering new capabilities
     *
     * @param capInterface The interface for the capability
     * @param capFactory A {@link Callable} which returns a new instance of the default implementation of the interface
     */
    private static <T extends ICapability> void regCapability(Class<T> capInterface, Callable<? extends T> capFactory)
    {
        CapabilityManager.INSTANCE.register(capInterface, new Storage<>(), capFactory);
    }

    public static void init()
    {
        //Register capabilities
        regCapability(ICapGlyphs.class, CapabilityGlyphs::new);
    }
}
