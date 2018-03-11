package mdc.voodoocraft.init;

import mdc.voodoocraft.blocks.*;
import mdc.voodoocraft.tile.TileDeathGlyph;
import mdc.voodoocraft.tile.TileDollPedestal;
import mdc.voodoocraft.tile.TileTotem;
import mdc.voodoocraft.tile.render.TileDollPedestalRender;
import mdc.voodoocraft.tile.render.TileTotemRender;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class VCBlocks
{
    public static List<Block> BLOCKS = new ArrayList<>();
    public static List<ItemBlock> ITEM_BLOCKS = new ArrayList<>();

    public static Block GLYPH, DOLL_PEDESTAL, SHRINE, TOTEM, LIGHT_SOURCE, DEATH_GLYPH;

    private static void addBlock(Block block)
    {
        addBlock(block, (ItemBlock) new ItemBlock(block).setRegistryName(block.getRegistryName()));
    }

    private static void addBlock(Block block, ItemBlock itemBlock)
    {
        BLOCKS.add(block);
        ITEM_BLOCKS.add(itemBlock);
    }

    private static void regTE(Class<? extends TileEntity> teClass, Block block)
    {
        GameRegistry.registerTileEntity(teClass, block.getRegistryName().getResourcePath());
    }

    private static <T extends TileEntity> void regTESR(Class<? extends T> teClass, TileEntitySpecialRenderer<? super T> tesr)
    {
        ClientRegistry.bindTileEntitySpecialRenderer(teClass, tesr);
    }

    private static void init()
    {
        addBlock(GLYPH = new BlockGlyph());
        addBlock(DOLL_PEDESTAL = new BlockDollPedestal());
        addBlock(SHRINE = new BlockShrine());
        addBlock(TOTEM = new BlockTotem());
        addBlock(DEATH_GLYPH = new BlockDeathGlyph());
        addBlock(LIGHT_SOURCE = new BlockLightSource());
    }

    public static void initTEs()
    {
        //Register Tile Entities
        regTE(TileDollPedestal.class, DOLL_PEDESTAL);
        regTE(TileTotem.class, TOTEM);
        regTE(TileDeathGlyph.class, DEATH_GLYPH);
    }

    @SideOnly(Side.CLIENT)
    public static void initTESRs()
    {
        //Register TESRs
        regTESR(TileDollPedestal.class, new TileDollPedestalRender());
        regTESR(TileTotem.class, new TileTotemRender());
    }

    public static Block[] getBlocks()
    {
        if(BLOCKS.isEmpty()) init();
        return BLOCKS.toArray(new Block[BLOCKS.size()]);
    }

    public static ItemBlock[] getItemBlocks()
    {
        if(ITEM_BLOCKS.isEmpty()) init();
        return ITEM_BLOCKS.toArray(new ItemBlock[ITEM_BLOCKS.size()]);
    }
}
