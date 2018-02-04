package mdc.voodoocraft.tile;

import mdc.voodoocraft.util.EnumGlyphType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

import javax.annotation.Nullable;
import java.util.Arrays;

public class TileTotem extends TileEntity
{
    private EnumGlyphType[] sides = new EnumGlyphType[4];

    public TileTotem()
    {
        //NO-OP
    }

    public void setSide(EnumFacing side, @Nullable EnumGlyphType glyph)
    {
        //We don't set glyphs on the top or bottom sides
        if(Arrays.asList(EnumFacing.HORIZONTALS).contains(side))
        {
            sides[side.getHorizontalIndex()] = glyph;
            markDirty();
        }
    }

    @Nullable
    public EnumGlyphType getSide(EnumFacing side)
    {
        return sides[side.getHorizontalIndex()];
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        sides = new EnumGlyphType[4];
        for(EnumFacing side : EnumFacing.HORIZONTALS)
        {
            if(nbt.hasKey(side.getName())) sides[side.getHorizontalIndex()] = EnumGlyphType.byIndex(nbt.getByte(side.getName()));
        }

        super.readFromNBT(nbt);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        for(EnumFacing side : EnumFacing.HORIZONTALS)
        {
            EnumGlyphType glyph = getSide(side);
            if(glyph != null) nbt.setByte(side.getName(), (byte) glyph.ordinal());
        }

        return super.writeToNBT(nbt);
    }

    //TODO: Figure out how to use the methods below to update the client properly

    @Override
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        return new SPacketUpdateTileEntity(pos, -1, getUpdateTag());
    }

    @Override
    public NBTTagCompound getUpdateTag()
    {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public void onDataPacket(net.minecraft.network.NetworkManager net, SPacketUpdateTileEntity pkt)
    {
        readFromNBT(pkt.getNbtCompound());
    }
}
