package mdc.voodoocraft.advancement;

import com.google.gson.JsonObject;
import mdc.voodoocraft.VoodooCraft;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public abstract class VCTrigger<I extends VCCriterionInstance<O>, O> implements ICriterionTrigger<I>
{
    protected final ResourceLocation ID;
    protected final String objectName;
    private final Map<PlayerAdvancements, VCListeners<I, O>> listenersMap = new HashMap<>();

    public VCTrigger(String name, String objectName)
    {
        ID = new ResourceLocation(VoodooCraft.MODID, name);
        this.objectName = objectName;
    }

    @Override
    public ResourceLocation getId()
    {
        return ID;
    }

    @Override
    public void addListener(PlayerAdvancements advancements, Listener<I> listener)
    {
        VCListeners<I, O> listeners = listenersMap.get(advancements);
        if(listeners == null)
        {
            listeners = new VCListeners<>(advancements);
            listenersMap.put(advancements, listeners);
        }
        listeners.add(listener);
    }

    @Override
    public void removeListener(PlayerAdvancements advancements, Listener<I> listener)
    {
        VCListeners<I, O> listeners = listenersMap.get(advancements);
        if(listeners != null)
        {
            listeners.remove(listener);
            if(listeners.isEmpty())
                listenersMap.remove(advancements);
        }
    }

    @Override
    public void removeAllListeners(PlayerAdvancements advancements)
    {
        listenersMap.remove(advancements);
    }

    public void trigger(EntityPlayerMP player, O object)
    {
        VCListeners<I, O> listeners = listenersMap.get(player.getAdvancements());
        if(listeners != null)
            listeners.trigger(object);
    }

    protected String getObjectStringFromJson(JsonObject json)
    {
        return objectName != null && json.has(objectName) ? JsonUtils.getString(json, objectName) : null;
    }
}
