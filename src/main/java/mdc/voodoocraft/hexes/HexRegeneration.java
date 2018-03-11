package mdc.voodoocraft.hexes;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class HexRegeneration extends HexEntry {

	public HexRegeneration()
	{
		super("regeneration");
	}
	
	@Override
	public ItemStack activeUse(ItemStack stackIn, World world, EntityPlayer player, EnumHand hand, int strength, @Nullable EntityLivingBase target)
	{
		if(!world.isRemote)
			player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 20, 3)); //about 1.5 hearts per use
		return super.activeUse(stackIn, world, player, hand, strength, target);
	}

	@Override
	public ItemStack passiveUse(ItemStack stack, World world, int strength, @Nullable EntityLivingBase target)
	{
		if(!world.isRemote && target != null)
			target.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 20, 3));
		return stack;
	}
}
