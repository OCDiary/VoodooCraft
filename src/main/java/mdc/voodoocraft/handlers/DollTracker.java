package mdc.voodoocraft.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import mdc.voodoocraft.items.ItemDoll;
import mdc.voodoocraft.tile.TileDollPedestal;
import mdc.voodoocraft.util.NBTHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

/**
 * Class for keeping track of dolls in pedestals, so they can be found from events.<br>
 * 
 * @author TheUnderTaker11
 */
public class DollTracker {
	
	/**Any pedestals with a player's doll should be in this list */
	public static List<DollTrackerObj> playerList = new ArrayList<DollTrackerObj>();
	/**All other entities should go in this list */
	public static List<DollTrackerObj> entityList = new ArrayList<DollTrackerObj>();
	
	/**
	 * Gets a list of all respective pedestals from a UUID <br>
	 * @param id UUID of entity
	 * @param isPlayer Give true if the UUID comes from a player
	 * @return List of all DollTrackerObj with the given UUID
	 */
	public static List<DollTrackerObj> getListFromUUID(UUID id, boolean isPlayer)
	{
		List<DollTrackerObj> list = new ArrayList<DollTrackerObj>();
		if(isPlayer){
			for(DollTrackerObj obj : playerList)
			{
				if(obj.ID.equals(id))
				{
					list.add(obj);
				}
			}
		}else{
			for(DollTrackerObj obj : entityList)
			{
				if(obj.ID.equals(id))
				{
					list.add(obj);
				}
			}
		}
		return list;
	}
	/**
	 * Adds tile & UUID to the list if it should be, and removes them if nothing is in the pedestal.
	 * Called every time the TileDollPedestal inventory changes, and when it is loaded in.
	 */
	public static void updateTileEntry(TileDollPedestal tile)
	{
		int dim = tile.getWorld().provider.getDimension();
		IItemHandler inv = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		ItemStack stack = inv.getStackInSlot(0);
        if(stack!=null&&stack.getItem() instanceof ItemDoll&&stack.getTagCompound()!=null
        		&&stack.getTagCompound().hasKey(NBTHelper.KEY_OWNER))
        {
        	if(stack.getTagCompound().getBoolean(NBTHelper.KEY_IS_PLAYER)==true)
        	{
        		addPlayerEntry(NBTUtil.getUUIDFromTag(stack.getTagCompound()), dim, tile.getPos());
        	}else{
        		addEntityEntry(NBTUtil.getUUIDFromTag(stack.getTagCompound()), dim, tile.getPos());
        	}
        }else{
        	for(DollTrackerObj obj : playerList)
        	{
        		if(obj.Pos==tile.getPos()&&obj.Dim==dim)
        			playerList.remove(obj);
        	}
        	for(DollTrackerObj obj : entityList)
        	{
        		if(obj.Pos==tile.getPos()&&obj.Dim==dim)
        			entityList.remove(obj);
        	}
        }
	}
	/**
	 * Adds an entry to the player list, if it is not already there.
	 * @param id UUID of player
	 * @param dim Dimension ID
	 * @param pos BlockPos of the TileDollPedestal the doll is in.
	 */
	public static void addPlayerEntry(UUID id, int dim,BlockPos pos)
	{
		for(DollTrackerObj obj : playerList)
		{
			if(obj.ID.equals(id))
			{
				return;
			}
		}
		playerList.add(new DollTrackerObj(id, dim, pos));
	}
	/**
	 * Adds an entry to the regular entity list, if not already there.
	 * @param id UUID of entity
	 * @param dim Dimension ID
	 * @param pos BlockPos of the TileDollPedestal the doll is in.
	 */
	public static void addEntityEntry(UUID id, int dim,BlockPos pos)
	{
		for(DollTrackerObj obj : entityList)
		{
			if(obj.ID.equals(id))
			{
				return;
			}
		}
		entityList.add(new DollTrackerObj(id, dim, pos));
	}
}

/**
 * Object used to store all 3 of the parameters in one easy list
 * 
 * @author TheUnderTaker11
 */
class DollTrackerObj {

	public final UUID ID;
	public final int Dim;
	public final BlockPos Pos;
	
	public DollTrackerObj(UUID id, int dimension, BlockPos pos)
	{
		ID = id;
		Dim = dimension;
		Pos = pos;
	}
}
