package mdc.voodoocraft.init;

import mdc.voodoocraft.items.*;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class VCItems
{
    public static List<Item> ITEMS = new ArrayList<>();

    public static final Item DOLL, CHALK_BASIC, SHARD, TEST, TOOL1, TOOL2, COIN;

    static
    {
        //Add the items - these will get registered later in RegHandler and models registered in ModelHandler
        addItem(DOLL = new ItemDoll());
        addItem(CHALK_BASIC = new ItemChalk());
        addItem(SHARD = new ItemShard());
        addItem(TEST = new TestItem());
        addItem(TOOL1 = new VCItem("tool1"));
        addItem(TOOL2 = new VCItem("tool2"));
        addItem(COIN = new VCItem("coin"));
    }

    private static void addItem(Item item)
    {
        ITEMS.add(item);
    }
}