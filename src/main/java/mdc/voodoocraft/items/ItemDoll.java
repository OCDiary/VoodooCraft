package mdc.voodoocraft.items;

import java.util.List;

import mdc.voodoocraft.init.VCAchievements;
import mdc.voodoocraft.hexes.Hex;
import mdc.voodoocraft.hexes.HexEntry;
import mdc.voodoocraft.init.VCHexes;
import mdc.voodoocraft.util.HexHelper;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDoll extends VCItem
{
	public static final String KEY_HEX = "hex";
	
    public ItemDoll()
    {
        super("doll");
        setMaxStackSize(1);
        setMaxDamage(100);
        setHasSubtypes(true);
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn)
    {
        if(!worldIn.isRemote)
            playerIn.addStat(VCAchievements.achievementHexFirstTime);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems)
    {
        //Doll without a Hex
        subItems.add(new ItemStack(this));

        //Add a doll for every Hex
        for(HexEntry entry : VCHexes.HEXES.values())
        {
            ItemStack dollWithHex = HexHelper.setHexes(new ItemStack(itemIn), new Hex(entry));
            subItems.add(dollWithHex);
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        itemStackIn = HexHelper.activate(itemStackIn, worldIn, playerIn, hand);
        if(!playerIn.capabilities.isCreativeMode)
            playerIn.getCooldownTracker().setCooldown(this, 30); //1.5 seconds cooldown after use
        return new ActionResult<>(EnumActionResult.SUCCESS, itemStackIn);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
    {
    	super.addInformation(stack, playerIn, tooltip, advanced);
    	List<Hex> hexes = HexHelper.getHexes(stack);
    	tooltip.add(I18n.format("desc.hex.name"));
    	if(!hexes.isEmpty()) {
    		for(Hex h : hexes) {
    			tooltip.add(h.getLocalisedName());
    			if(h.getDescription() != null && GuiScreen.isShiftKeyDown()) {
    				tooltip.add(h.getDescription());
    			}
    		}
   	 	}
    	else {
          	 tooltip.add(I18n.format("hex.none.name"));
        }
    	if(!GuiScreen.isShiftKeyDown()) tooltip.add(TextFormatting.AQUA + I18n.format("press.for.info.name", "SHIFT")); //TODO: configurable key?
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
        HexHelper.activate(stack, playerIn.world, playerIn, hand, target, false);
        if(!playerIn.capabilities.isCreativeMode)
            playerIn.getCooldownTracker().setCooldown(this, 30); //1.5 seconds cooldown after use
        return true;
    }
}
