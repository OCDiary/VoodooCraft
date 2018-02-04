package mdc.voodoocraft.capability.glyphs;

import mdc.voodoocraft.capability.ICapability;
import mdc.voodoocraft.util.EnumGlyphType;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.List;

public interface ICapGlyphs extends ICapability
{
    /**
     * Returns a list of all the known glyphs for the player
     */
    List<EnumGlyphType> getKnownGlyphs();

    /**
     * Checks if the player knows the given glyph
     */
    boolean knowsGlyph(EnumGlyphType glyph);

    /**
     * Will try to add the given glyph to the player's known glyphs
     * Returns false if the player already knew the glyph
     */
    boolean addGlyph(EnumGlyphType glyph);

    /**
     * Same as {@link #addGlyph(EnumGlyphType)} but will also send data to the client if anything changed
     */
    boolean addGlyph(EnumGlyphType glyph, EntityPlayerMP player);

    /**
     * Will try to remove the glyph from the player's known glyphs
     */
    void removeGlyph(EnumGlyphType glyph);

    /**
     * Same as {@link #removeGlyph(EnumGlyphType)} but will also send data to the client if anything changed
     */
    void removeGlyph(EnumGlyphType glyph, EntityPlayerMP player);
}
