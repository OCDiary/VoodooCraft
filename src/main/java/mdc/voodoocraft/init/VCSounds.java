package mdc.voodoocraft.init;

import mdc.voodoocraft.VoodooCraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class VCSounds
{
    public static List<SoundEvent> SOUNDS = new ArrayList<>();

    public static SoundEvent lightritual, rainritual, spiritguideritual, summonritual, templeritual, transformation;

    private static void init()
    {
        lightritual = new SoundEvent(new ResourceLocation(VoodooCraft.MODID, "lightritual")).setRegistryName("lightritual");
        rainritual = new SoundEvent(new ResourceLocation(VoodooCraft.MODID, "rainritual")).setRegistryName("rainritual");
        spiritguideritual = new SoundEvent(new ResourceLocation(VoodooCraft.MODID, "spiritguideritual")).setRegistryName("spiritguideritual");
        summonritual = new SoundEvent(new ResourceLocation(VoodooCraft.MODID, "summonritual")).setRegistryName("summonritual");
        templeritual = new SoundEvent(new ResourceLocation(VoodooCraft.MODID, "templeritual")).setRegistryName("templeritual");
        transformation = new SoundEvent(new ResourceLocation(VoodooCraft.MODID, "transformation")).setRegistryName("transformation");
    }

    public static SoundEvent[] getSounds()
    {
        if(SOUNDS.isEmpty()) init();
        return SOUNDS.toArray(new SoundEvent[SOUNDS.size()]);
    }
}
