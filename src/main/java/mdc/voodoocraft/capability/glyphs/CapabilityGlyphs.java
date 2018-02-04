package mdc.voodoocraft.capability.glyphs;

import mdc.voodoocraft.Reference;
import mdc.voodoocraft.capability.CapabilityProvider;
import mdc.voodoocraft.handlers.NetworkHandler;
import mdc.voodoocraft.init.VCCapabilities;
import mdc.voodoocraft.messages.MessageCapability;
import mdc.voodoocraft.util.EnumGlyphType;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import java.util.ArrayList;
import java.util.List;

public class CapabilityGlyphs implements ICapGlyphs
{
    private static final ResourceLocation glyphsRL = new ResourceLocation(Reference.MODID, "_Glyphs");

    private List<EnumGlyphType> knownGlyphs = new ArrayList<>();

    public CapabilityGlyphs()
    {
        knownGlyphs.add(EnumGlyphType.BASIC);
    }

    @Override
    public List<EnumGlyphType> getKnownGlyphs()
    {
        return knownGlyphs;
    }

    @Override
    public boolean knowsGlyph(EnumGlyphType glyph)
    {
        return knownGlyphs.contains(glyph);
    }

    @Override
    public boolean addGlyph(EnumGlyphType glyph)
    {
        return !knowsGlyph(glyph) && knownGlyphs.add(glyph);
    }

    @Override
    public boolean addGlyph(EnumGlyphType glyph, EntityPlayerMP player)
    {
        boolean result = addGlyph(glyph);
        if(result) dataChanged(player);
        return result;
    }

    @Override
    public void removeGlyph(EnumGlyphType glyph)
    {
        knownGlyphs.remove(glyph);
    }

    @Override
    public void removeGlyph(EnumGlyphType glyph, EntityPlayerMP player)
    {
        int oldSize = knownGlyphs.size();
        removeGlyph(glyph);
        if(knownGlyphs.size() != oldSize) dataChanged(player);
    }

    @Override
    public ResourceLocation getKey()
    {
        return glyphsRL;
    }

    @Override
    public ICapabilityProvider getProvider()
    {
        return new CapabilityProvider<>(VCCapabilities.GLYPHS);
    }

    @Override
    public void dataChanged(EntityPlayerMP player)
    {
        //Send all data to the client side player
        NetworkHandler.INSTANCE.sendTo(new MessageCapability(VCCapabilities.GLYPHS, serializeNBT()), player);
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound nbt = new NBTTagCompound();
        int[] ids = new int[knownGlyphs.size()];
        for(int i = 0; i < knownGlyphs.size(); i++)
            ids[i] = knownGlyphs.get(i).ordinal();
        NBTTagIntArray glyphIDs = new NBTTagIntArray(ids);
        nbt.setTag("glyphs", glyphIDs);
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
        if(nbt.hasKey("glyphs"))
        {
            knownGlyphs.clear();
            int[] glyphIDs = nbt.getIntArray("glyphs");
            for(int id : glyphIDs)
                addGlyph(EnumGlyphType.byIndex(id));
        }
    }
}
