package mdc.voodoocraft.advancement;

import net.minecraft.util.ResourceLocation;

public class VoidCriterionInstance extends VCCriterionInstance<Void>
{
    public VoidCriterionInstance(ResourceLocation id)
    {
        super(id, null);
    }

    @Override
    public boolean test(Void object)
    {
        return true;
    }
}
