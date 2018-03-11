package mdc.voodoocraft.items;

import java.util.List;

import mdc.voodoocraft.hexes.Hex;
import mdc.voodoocraft.hexes.HexEntry;
import mdc.voodoocraft.init.VCHexes;
import mdc.voodoocraft.util.HexHelper;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class ItemDoll extends VCItem
{
    public ItemDoll()
    {
        super("doll");
        setMaxStackSize(1);
        setMaxDamage(100);
        setHasSubtypes(true);
    }

    //TODO: Replace with advancements
    /*
    @Override
    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn)
    {
        if(!worldIn.isRemote)
            playerIn.addStat(VCAchievements.achievementHexFirstTime);
    }
    */

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        if(isInCreativeTab(tab))
        {
            //Doll without a Hex
            items.add(new ItemStack(this));

            //Add a doll for every Hex
            for(HexEntry entry : VCHexes.HEXES.values())
            {
                ItemStack dollWithHex = HexHelper.setHexes(new ItemStack(this), new Hex(entry));
                items.add(dollWithHex);
            }
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        stack = HexHelper.activate(stack, world, player, hand);
        if(!player.capabilities.isCreativeMode)
            player.getCooldownTracker().setCooldown(this, 30); //1.5 seconds cooldown after use
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
    {
    	super.addInformation(stack, world, tooltip, flag);
    	List<Hex> hexes = HexHelper.getHexes(stack);
    	tooltip.add(I18n.format("desc.hex.name"));
    	if(!hexes.isEmpty())
    	{
    		for(Hex h : hexes)
    		{
    		    HexEntry entry = h.getEntry();
    			tooltip.add(entry.getLocalisedName());
    			if(entry.getDescription() != null && GuiScreen.isShiftKeyDown())
    				tooltip.add(entry.getDescription());
    		}
   	 	}
    	else
          	 tooltip.add(I18n.format("hex.none.name"));
    	if(!GuiScreen.isShiftKeyDown())
    	    tooltip.add(TextFormatting.AQUA + I18n.format("press.for.info.name", "SHIFT")); //TODO: configurable key?
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand)
    {
        HexHelper.activate(stack, playerIn.world, playerIn, hand, target, false);
        if(!playerIn.capabilities.isCreativeMode)
            playerIn.getCooldownTracker().setCooldown(this, 30); //1.5 seconds cooldown after use
        return true;
    }
}
