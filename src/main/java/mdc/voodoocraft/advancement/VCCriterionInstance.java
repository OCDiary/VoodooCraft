package mdc.voodoocraft.advancement;

import net.minecraft.advancements.critereon.AbstractCriterionInstance;
import net.minecraft.util.ResourceLocation;

public abstract class VCCriterionInstance<T> extends AbstractCriterionInstance
{
    protected final T object;

    public VCCriterionInstance(ResourceLocation id, T object)
    {
        super(id);
        this.object = object;
    }

    public abstract boolean test(T object);
}
