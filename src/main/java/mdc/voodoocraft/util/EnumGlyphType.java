package mdc.voodoocraft.util;

import mdc.voodoocraft.VoodooCraft;
import mdc.voodoocraft.capability.glyphs.ICapGlyphs;
import mdc.voodoocraft.init.VCCapabilities;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Locale;

public enum EnumGlyphType implements IStringSerializable {
	BASIC,
	AIR,
	EARTH,
	FIRE,
	WATER,
	LIFE,
	DEATH,
	LIGHT,
	DARK,
	TIME,
	SPEED,
	POWER,
	LINK;

	private String name;
	private ResourceLocation textureLocation;

	EnumGlyphType() {
		this.name = this.name().toLowerCase(Locale.ROOT);
	}

	@Override
	public String getName()
	{
		return this.name;
	}

	@SideOnly(Side.CLIENT)
	public String getLocalName()
	{
		return I18n.format("glyph." + getName() + ".name");
	}

	public ResourceLocation getTextureLocation()
	{
		if(textureLocation == null)
			textureLocation = new ResourceLocation(VoodooCraft.MODID, "textures/blocks/glyph_" + getName() + ".png");
		return textureLocation;
	}
	
	public static EnumGlyphType byName(String name) {
		for(EnumGlyphType type : EnumGlyphType.values()) {
			if(type.getName().equals(name)) return type;
		}
		throw new EnumConstantNotPresentException(EnumGlyphType.class, name);
	}
	
	public static EnumGlyphType byIndex(int index) {
		return EnumGlyphType.values()[index];
	}

	/**
	 * Gets the next glyph
	 */
	public EnumGlyphType next() {
		int index = this.ordinal() + 1;
		if(index >= EnumGlyphType.values().length) index = 0;
		return EnumGlyphType.byIndex(index);
	}

	/**
	 * Gets the next glyph that's known by the player
	 */
	public EnumGlyphType nextKnown(EntityPlayer player)
	{
		ICapGlyphs cap = player.getCapability(VCCapabilities.GLYPHS, null);
		if(cap == null || cap.getKnownGlyphs().isEmpty()) return this;
		EnumGlyphType nextGlyph;
		do nextGlyph = next();
		while(!cap.knowsGlyph(nextGlyph));
		return nextGlyph;
	}
}
