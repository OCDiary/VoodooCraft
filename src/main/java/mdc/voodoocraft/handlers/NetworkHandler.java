package mdc.voodoocraft.handlers;

import mdc.voodoocraft.VoodooCraft;
import mdc.voodoocraft.messages.MessageCapability;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandler {

    public static SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(VoodooCraft.MODID.toLowerCase());
    private static int packedID = 0;

    public static void init()
    {
        //Register messages
        regPacket(MessageCapability.Handler.class, MessageCapability.class, Side.CLIENT);
    }

    /**
     * Register a packet and it's handler using the simple network implementation
     * 
     * @param handler the message handler
     * @param message the message
     * @param side the receiving {@link Side}
     */
    private static <REQ extends IMessage, REPLY extends IMessage> void regPacket(Class<? extends IMessageHandler<REQ, REPLY>> handler, Class<REQ> message, Side side) {
        INSTANCE.registerMessage(handler, message, packedID++, side);
    }

}
