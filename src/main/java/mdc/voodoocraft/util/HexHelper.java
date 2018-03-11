package mdc.voodoocraft.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import mdc.voodoocraft.hexes.Hex;
import mdc.voodoocraft.items.ItemDoll;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;

public class HexHelper {

	private static final String KEY_HEXES = "hexes";
	private static final ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(20);
	private static Map<String, ScheduledFuture> threadsRunning = new HashMap<>();
	
	@Nonnull
	public static List<Hex> getHexes(ItemStack stack) {
		List<Hex> hexes = Lists.newArrayList();
		if(stack.hasTagCompound()) {
			NBTTagCompound nbt = stack.getTagCompound();
			if(nbt.hasKey(KEY_HEXES, NBT.TAG_LIST)) {
				NBTTagList hexList = nbt.getTagList(KEY_HEXES, NBT.TAG_COMPOUND);
				if(!hexList.hasNoTags()) {
					for(int i = 0; i < hexList.tagCount(); i++) {
						hexes.add(new Hex(hexList.getCompoundTagAt(i)));
					}
				}
			}
		}
		return hexes;
	}

	public static Hex getHex(ItemStack stack, String hexName) {
		List<Hex> hexes = getHexes(stack);
		for(Hex hex : hexes)
			if(hex.getEntry().getName().equals(hexName))
				return hex;
		return null;
	}
	
	public static ItemStack setHexes(ItemStack stackIn, Hex... hexes) {
		NBTTagCompound stackNBT = stackIn.getTagCompound();
		if(stackNBT == null) stackNBT = new NBTTagCompound();

		NBTTagList hexList = new NBTTagList();
		for(Hex h : hexes)
			hexList.appendTag(h.writeToNBT());

		stackNBT.setTag(KEY_HEXES, hexList);
		stackIn.setTagCompound(stackNBT); //update the actual compound
		return stackIn;
	}

	/**
	 * Should be used to activate a hex passively when in a Doll Pedestal
	 */
	public static ItemStack activatePassive(ItemStack stack, World world, EntityPlayer player)
	{
		return activate(stack, world, player, EnumHand.MAIN_HAND, null, true);
	}

	/**
	 * Should be used for hexes that are fired on right clicking nothing
	 * @return
	 */
	public static ItemStack activate(ItemStack stack, World world, EntityPlayer player, EnumHand hand)
	{
		return activate(stack, world, player, hand, null, false);
	}

	/**
	 * Should be used for hexes that are fired on right clicking entities
	 * @return
	 */
	public static ItemStack activate(ItemStack stack, World world, EntityPlayer player, EnumHand hand, @Nullable EntityLivingBase target, boolean passive)
	{
		//Temporarily set the active hand to which hand is being used
		player.setActiveHand(hand);

		List<Hex> hexes = getHexes(stack);
		if(!hexes.isEmpty())
		{
			int multiplier = 1;
			for(Hex h : hexes)
			{
				if(passive)
					stack = h.passiveUse(stack, world, player);
				else
					stack = h.activeUse(stack, world, player, target);
				multiplier += h.getCost();
	        }
			stack.damageItem(multiplier * hexes.size(), player);
	    }
	    player.stopActiveHand();
		return stack;
	}
  
  	/**
	 * Checks the player's inventory for a Doll with a certain Hex and returns it.
	 */
	public static ItemStack getDollWithHex(EntityPlayer player, String hexName)
	{
		InventoryPlayer playerInv = player.inventory;
		for(ItemStack stack : playerInv.mainInventory)
			if(stack.getItem() instanceof ItemDoll)
				if(getHex(stack, hexName) != null)
					return stack;
		return null;
	}

	/**
	 * Runs a given Runnable periodically.
	 * @param name Name of the thread
	 * @param runnable The task to run
	 * @param delay The initial delay
	 * @param period The period between runs
	 * @param timeUnit The time unit for the above time values
	 * @param repetitions The number of times to run
	 * @return False if a task with the same name is already running
	 */
	public static boolean runPeriodically(IThreadListener mainThread, String name, Runnable runnable, int delay, int period, TimeUnit timeUnit, int repetitions)
	{
		boolean canRun = threadsRunning.containsKey(name);
		if(canRun)
		{
			ScheduledFuture thread = executorService.scheduleAtFixedRate(new PeriodicThread(mainThread, name, runnable, repetitions),
					delay, period, timeUnit);
			threadsRunning.put(name, thread);
		}
		return canRun;
	}

	private static class PeriodicThread implements Runnable
	{
		private IThreadListener mainThread;
		private Runnable runnable;
		private String name;
		private int runsLeft;

		public PeriodicThread(IThreadListener mainThread, String name, Runnable runnable, int repetitions)
		{
			this.mainThread = mainThread;
			this.runnable = runnable;
			this.name = name;
			runsLeft = repetitions;
		}

		@Override
		public void run()
		{
			System.out.println("Running thread " + name);
			mainThread.addScheduledTask(runnable);
			runsLeft--;
			if(runsLeft <= 0)
			{
				ScheduledFuture thisThread = threadsRunning.get(name);
				thisThread.cancel(false);
				threadsRunning.remove(name);
			}
		}
	}
}