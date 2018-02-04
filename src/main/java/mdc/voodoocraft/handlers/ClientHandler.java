package mdc.voodoocraft.handlers;

import mdc.voodoocraft.init.VCBlocks;
import mdc.voodoocraft.init.VCItems;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientHandler
{
    @SubscribeEvent
    public static void regModels(ModelRegistryEvent event)
    {
        //Register item models
        VCItems.ITEMS.forEach(ClientHandler::regModel);
        //Above is the same as:
        //for(Item item : VCItems.ITEMS)
        //    regModel(item);
        VCBlocks.ITEM_BLOCKS.forEach(ClientHandler::regModel);
        //Above is the same as:
        //for(ItemBlock itemBlock : VCBlocks.ITEM_BLOCKS)
        //    regModel(itemBlock);
    }

    private static void regModel(Item item)
    {
        regModel(item, 0);
    }

    private static void regModel(Item item, int meta)
    {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }
}
