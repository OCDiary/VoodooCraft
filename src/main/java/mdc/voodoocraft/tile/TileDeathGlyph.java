package mdc.voodoocraft.tile;

import com.sun.istack.internal.NotNull;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.*;

public class TileDeathGlyph extends TileEntity
{
    private NBTTagCompound playerInventory;
    private int requiredXPLevels;
    private List<ItemStack> requiredStacks = new ArrayList<>(3);
    private boolean hasUsedCoin = false;

    public TileDeathGlyph()
    {
        Random rand = new Random();

        //Pick random amount of XP levels
        requiredXPLevels = rand.nextInt(20) + 1; //1 - 20

        //Pick 3 random items or blocks
        while(requiredStacks.size() < 3)
        {
            Item item;
            if(rand.nextBoolean())
            {
                //Item
                item = Item.REGISTRY.getRandomObject(rand);
            }
            else
            {
                //Block
                Block block = Block.REGISTRY.getRandomObject(rand);
                item = Item.getItemFromBlock(block);
            }
            if(item == null || item == Items.AIR) continue;
            NonNullList<ItemStack> subItems = NonNullList.create();
            item.getSubItems(CreativeTabs.SEARCH, subItems);
            ItemStack stack = subItems.get(rand.nextInt(subItems.size()));
            if(hasRecipe(stack)) requiredStacks.add(stack);
        }
    }

    /**
     * Checks the CraftingManager and the FurnaceRecipes for a recipe for the ItemStack and returns true if it finds one
     */
    private static boolean hasRecipe(ItemStack stack)
    {
        if(stack == null) return false;

        //Check crafting recipes
        for(IRecipe r : CraftingManager.REGISTRY)
            if(r.getRecipeOutput().isItemEqual(stack))
                return true;

        //Check furnace recipes
        Collection<ItemStack> furnaceResults = FurnaceRecipes.instance().getSmeltingList().values();
        for(ItemStack s : furnaceResults)
            if(s.isItemEqual(stack))
                return true;

        return false;
    }

    public boolean hasSubmittedAll()
    {
        return requiredXPLevels == 0 && requiredStacks.isEmpty();
    }

    public boolean submitStack(ItemStack stack)
    {
        int index = -1;
        for(int i = 0; i < requiredStacks.size(); i++)
        {
            ItemStack s = requiredStacks.get(i);
            if(s.isItemEqual(stack))
            {
                index = i;
                break;
            }
        }
        if(index < 0) return false;
        requiredStacks.remove(index);
        markDirty();
        return true;
    }

    /**
     * Returns each required ItemStack's name in a String where each are on their own line
     */
    @SideOnly(Side.CLIENT)
    public String getStackList()
    {
        if(requiredStacks.isEmpty()) return "All Items Provided!";

        StringBuilder s = new StringBuilder();
        for(int i = 0; i < requiredStacks.size(); i++)
        {
            if(i > 0) s.append("\n");
            s.append(requiredStacks.get(i).getDisplayName());
        }
        return s.toString();
    }

    public void submitXPLevels()
    {
        requiredXPLevels = 0;
        markDirty();
    }

    /**
     * Returns the amount of XP levels required
     */
    public int getXPLevels()
    {
        return requiredXPLevels;
    }

    public ItemStack substitute()
    {
        if(hasUsedCoin) return null;
        hasUsedCoin = true;
        markDirty();
        return requiredStacks.remove(world.rand.nextInt(requiredStacks.size()));
    }

    public boolean hasUsedCoin()
    {
        return hasUsedCoin;
    }

    /**
     * Saves the player's inventory to this tile entity
     */
    public void savePlayerInventory(EntityPlayer player)
    {
        playerInventory = new NBTTagCompound();
        InventoryPlayer playerInv = player.inventory;
        writeStacksToNBT(playerInventory, "main", playerInv.mainInventory);
        //playerInv.mainInventory.clear();
        writeStacksToNBT(playerInventory, "armour", playerInv.armorInventory);
        //playerInv.armorInventory.clear();
        writeStacksToNBT(playerInventory, "offhand", playerInv.offHandInventory);
        //playerInv.offHandInventory.clear();
    }

    /**
     * Reads the player's inventory from this tile entity and saves it to the player given
     */
    public void readPlayerInventory(EntityPlayer player)
    {
        InventoryPlayer playerInv = player.inventory;
        List<ItemStack> excessStacks = new ArrayList<>();
        excessStacks.addAll(setInvStackList(playerInv.mainInventory, readStacksFromNBT(playerInventory, "main")));
        excessStacks.addAll(setInvStackList(playerInv.armorInventory, readStacksFromNBT(playerInventory, "armour")));
        excessStacks.addAll(setInvStackList(playerInv.offHandInventory, readStacksFromNBT(playerInventory, "offhand")));
        //Try add the rest of the items which couldn't be placed
        for(ItemStack stack : excessStacks)
            if(playerInv.addItemStackToInventory(stack))
                //Drop item if can't fit in inventory
                player.dropItem(stack, true);
    }

    private static List<ItemStack> setInvStackList(NonNullList<ItemStack> invStackList, ItemStack[] stacksToSet)
    {
        if(invStackList.size() != stacksToSet.length)
            throw new IllegalArgumentException("Stack arrays are not of equal lengths! -> InvArray: " + invStackList.size() + "   ToSetArray: " + stacksToSet.length);
        List<ItemStack> excessStacks = new ArrayList<>();
        for(int i = 0; i < invStackList.size(); i++)
        {
            if(invStackList.get(i).isEmpty())
                invStackList.set(i, stacksToSet[i]);
            else
                excessStacks.add(stacksToSet[i]);
        }
        return excessStacks;
    }

    private static void writeStacksToNBT(@NotNull NBTTagCompound nbt, @NotNull String key, NonNullList<ItemStack> stacks)
    {
        NBTTagList list = new NBTTagList();
        for(int i = 0; i < stacks.size(); i++)
        {
            ItemStack stack = stacks.get(i);
            NBTTagCompound stackTag = new NBTTagCompound();
            stackTag.setInteger("index", i);
            stack.writeToNBT(stackTag);
            list.appendTag(stackTag);
            //stacks.set(i, ItemStack.EMPTY);
        }
        nbt.setInteger(key + "Size", stacks.size());
        nbt.setTag(key, list);
    }

    private static ItemStack[] readStacksFromNBT(@NotNull NBTTagCompound nbt, @NotNull String key)
    {
        NBTTagList list = nbt.getTagList(key, Constants.NBT.TAG_COMPOUND);
        int size = nbt.getInteger(key + "Size");
        ItemStack[] stacks = new ItemStack[size];
        for(int i = 0; i < list.tagCount(); i++)
        {
            NBTTagCompound stackTag = list.getCompoundTagAt(i);
            int index = stackTag.getInteger("index");
            ItemStack stack = new ItemStack(stackTag);
            stacks[index] = stack;
        }
        return stacks;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        hasUsedCoin = nbt.getBoolean("coin");
        requiredXPLevels = nbt.getInteger("xp");

        requiredStacks.clear();
        NBTTagList stackList = nbt.getTagList("stacks", Constants.NBT.TAG_COMPOUND);
        for(int i = 0; i < stackList.tagCount(); i++)
            requiredStacks.add(new ItemStack(stackList.getCompoundTagAt(i)));

        playerInventory = nbt.getCompoundTag("player");

        super.readFromNBT(nbt);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        nbt.setBoolean("coin", hasUsedCoin);
        nbt.setInteger("xp", requiredXPLevels);

        NBTTagList stackList = new NBTTagList();
        for(ItemStack stack : requiredStacks)
            stackList.appendTag(stack.serializeNBT());
        nbt.setTag("stacks", stackList);

        nbt.setTag("player", playerInventory);

        return super.writeToNBT(nbt);
    }
}
