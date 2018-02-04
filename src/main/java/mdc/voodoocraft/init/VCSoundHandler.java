package mdc.voodoocraft.init;

import mdc.voodoocraft.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class VCSoundHandler {

    public static SoundEvent lightritual, rainritual, spiritguideritual, summonritual, templeritual, transformation;

    private static int ID = SoundEvent.REGISTRY.getKeys().size();

    public static void init()
    {
        lightritual = register("lightritual");
        rainritual = register("rainritual");
        spiritguideritual = register("spiritguideritual");
        summonritual = register("summonritual");
        templeritual = register("templeritual");
        transformation = register("transformation");
    }

    public static SoundEvent register(String name)
    {
        ResourceLocation loc = new ResourceLocation(Reference.MODID, name);
        SoundEvent sEvent = new SoundEvent(loc);
        SoundEvent.REGISTRY.register(ID++, loc, sEvent);
        return sEvent;
    }
}
