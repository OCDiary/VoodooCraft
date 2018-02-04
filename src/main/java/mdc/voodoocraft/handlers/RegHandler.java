package mdc.voodoocraft.handlers;

import mdc.voodoocraft.init.VCBlocks;
import mdc.voodoocraft.init.VCItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.IForgeRegistry;

/**
 * put all registries here
 * @author UpcraftLP
 *
 */
@EventBusSubscriber
public class RegHandler
{
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
        //Register blocks
		IForgeRegistry<Block> registry = event.getRegistry();
		VCBlocks.BLOCKS.forEach(registry::register);
		//Above is the same as:
		//for(Block block : VCBlocks.BLOCKS)
		//	registry.register(block);
    }
    
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
	{
        //Register items
		IForgeRegistry<Item> registry = event.getRegistry();
		VCItems.ITEMS.forEach(registry::register);
		//Above is the same as:
		//for(Item item : VCItems.ITEMS)
		//	registry.register(item);
        VCBlocks.ITEM_BLOCKS.forEach(registry::register);
        //Above is the same as:
        //for(ItemBlock itemBlock : VCBlocks.ITEM_BLOCKS)
        //	registry.register(itemBlock);
    }
}
