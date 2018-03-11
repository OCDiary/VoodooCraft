package mdc.voodoocraft.init;

import mdc.voodoocraft.advancement.VCTrigger;
import mdc.voodoocraft.advancement.VoidTrigger;
import net.minecraft.advancements.CriteriaTriggers;

public class VCAdvancementTriggers
{
    public static VoidTrigger hex;

    private static void register(VCTrigger trigger)
    {
        CriteriaTriggers.register(trigger);
    }

    public static void init()
    {
        //TODO: Trigger the Hex trigger when applying a hex to a doll
        register(hex = new VoidTrigger("hex"));
    }
}
