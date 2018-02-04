package mdc.voodoocraft.handlers;

import mdc.voodoocraft.capability.ICapability;
import mdc.voodoocraft.init.VCCapabilities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

@Mod.EventBusSubscriber
public class CapabilityHandler
{
    @SubscribeEvent
    public static void attachCapability(AttachCapabilitiesEvent<Entity> event)
    {
        //Attach our capabilities to all players
        Entity entity = event.getObject();
        if(entity instanceof EntityPlayer)
        {
            for(Capability<? extends ICapability> cap : VCCapabilities.getCapabilities())
            {
                ICapability icap = cap.getDefaultInstance();
                if(icap == null) continue;
                if(!entity.hasCapability(cap, null))
                    event.addCapability(icap.getKey(), icap.getProvider());
            }
        }
    }

    @SuppressWarnings("all")
    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event)
    {
        //Send the client capability details
        if(event.player instanceof EntityPlayerMP)
        {
            EntityPlayerMP player = (EntityPlayerMP) event.player;
            for(Capability<? extends ICapability> cap : VCCapabilities.getCapabilities())
            {
                ICapability icap = player.getCapability(cap, null);
                if(icap != null) icap.dataChanged(player);
            }
        }
    }

    @SuppressWarnings("all")
    @SubscribeEvent
    public static void onClonePlayer(net.minecraftforge.event.entity.player.PlayerEvent.Clone event)
    {
        //Copy capability on player death to new player
        if(event.isWasDeath() && (event.getEntityPlayer() instanceof EntityPlayerMP))
        {
            EntityPlayerMP player = (EntityPlayerMP) event.getEntityPlayer();
            for(Capability<? extends ICapability> cap : VCCapabilities.getCapabilities())
            {
                ICapability oldicap = event.getOriginal().getCapability(cap, null);
                ICapability icap = player.getCapability(cap, null);
                if(oldicap == null || icap == null) continue;
                icap.deserializeNBT(oldicap.serializeNBT());
                icap.dataChanged(player);
            }
        }
    }
}
