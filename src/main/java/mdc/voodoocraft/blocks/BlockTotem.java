package mdc.voodoocraft.blocks;

import mdc.voodoocraft.VoodooConfig;
import mdc.voodoocraft.init.VCItems;
import mdc.voodoocraft.tile.TileTotem;
import mdc.voodoocraft.util.EnumGlyphType;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class BlockTotem extends VCBlockContainer implements ITileEntityProvider
{
    public BlockTotem()
    {
        super("totem");
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileTotem();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if(world.isRemote) return false;
        ItemStack heldItem = player.getHeldItem(hand);
        if(!heldItem.isEmpty())
        {
            TileTotem te = (TileTotem) world.getTileEntity(pos);
            if(te == null) return false;

            if(heldItem.getItem() == VCItems.TOOL1)
            {
                //Clear glyph on side
                te.setSide(side, null);
                return true;
            }
            else if(heldItem.getItem() == VCItems.TOOL2)
            {
                //Cycle to next glyph on side
                EnumGlyphType glyph = te.getSide(side);
                glyph = glyph == null ? EnumGlyphType.BASIC : player.capabilities.isCreativeMode ? glyph.next() : glyph.nextKnown(player);
                te.setSide(side, glyph);
                //Send chat message to the player
                if(VoodooConfig.advancedOutput)
                    player.sendMessage(new TextComponentTranslation("voodoo.glyphset.info", glyph.getLocalName()));
                return true;
            }
        }
        return false;
    }
}
