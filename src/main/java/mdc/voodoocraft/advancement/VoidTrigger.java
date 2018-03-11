package mdc.voodoocraft.advancement;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;

public class VoidTrigger extends VCTrigger<VoidCriterionInstance, Void>
{
    public VoidTrigger(String name)
    {
        super(name, null);
    }

    @Override
    public VoidCriterionInstance deserializeInstance(JsonObject json, JsonDeserializationContext context)
    {
        return new VoidCriterionInstance(ID);
    }
}
