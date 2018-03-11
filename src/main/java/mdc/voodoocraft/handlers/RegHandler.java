package mdc.voodoocraft.handlers;

import mdc.voodoocraft.VoodooCraft;
import mdc.voodoocraft.hexes.HexEntry;
import mdc.voodoocraft.init.VCBlocks;
import mdc.voodoocraft.init.VCHexes;
import mdc.voodoocraft.init.VCItems;
import mdc.voodoocraft.init.VCSounds;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;

@EventBusSubscriber
public class RegHandler
{
    private static <T extends IForgeRegistryEntry<T>> IForgeRegistry<T> addRegistry(String registryName, Class<T> type)
    {
        return new RegistryBuilder<T>()
                .setName(new ResourceLocation(VoodooCraft.MODID, registryName))
                .setType(type)
                .disableSaving()
                .allowModification()
                .create();
    }

    public static void registerRegistry(RegistryEvent.NewRegistry event)
    {
        //Register registries
        VCHexes.REGISTRY = addRegistry("hexes", HexEntry.class);
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
        //Register blocks
        event.getRegistry().registerAll(VCBlocks.getBlocks());

        VCBlocks.initTEs();
    }
    
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
	{
        //Register items
		IForgeRegistry<Item> registry = event.getRegistry();
        registry.registerAll(VCItems.getItems());
        registry.registerAll(VCBlocks.getItemBlocks());
    }

    @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> event)
    {
        //Register sounds
        event.getRegistry().registerAll(VCSounds.getSounds());
    }

    @SubscribeEvent
    public static void registerHexes(RegistryEvent.Register<HexEntry> event)
    {
        //Register hexes
        event.getRegistry().registerAll(VCHexes.getHexes());
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void regModels(ModelRegistryEvent event)
    {
        VCItems.ITEMS.forEach(RegHandler::regModel);
        VCBlocks.ITEM_BLOCKS.forEach(RegHandler::regModel);
        VCBlocks.initTESRs();
    }

    @SideOnly(Side.CLIENT)
    private static void regModel(Item item)
    {
        regModel(item, 0);
    }

    @SideOnly(Side.CLIENT)
    private static void regModel(Item item, int meta)
    {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }
}
