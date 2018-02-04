package mdc.voodoocraft.blocks;

import mdc.voodoocraft.init.VCItems;
import mdc.voodoocraft.tile.TileDeathGlyph;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BlockDeathGlyph extends VCModelBlock implements ITileEntityProvider
{
    public BlockDeathGlyph()
    {
        super("death_glyph", Material.CIRCUITS);
        setHardness(40f);
        isBlockContainer = true;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileDeathGlyph();
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BlockGlyph.hitbox;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos)
    {
        return NULL_AABB;
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        return null;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if(world.isRemote) return true;
        TileDeathGlyph te = (TileDeathGlyph) world.getTileEntity(pos);
        if(te == null) return false;
        if(heldItem == null)
        {
            //Submit XP if can
            if(te.getXPLevels() > 0 && player.experienceLevel >= te.getXPLevels())
            {
                player.sendMessage(new TextComponentString("Sacrificed " + te.getXPLevels() + " XP levels"));
                player.removeExperienceLevel(te.getXPLevels());
                te.submitXPLevels();
            }
            else
            {
                //Say required items and experience
                int xp = te.getXPLevels();
                String xpText = xp == 0 ? "" : "" + TextFormatting.GREEN + te.getXPLevels() + " XP levels" + TextFormatting.RESET + " and ";
                player.sendMessage(new TextComponentString("To get your items back, you must provide " + xpText + "the following items as sacrifice:"));
                player.sendMessage(new TextComponentString(te.getStackList()));
                if(te.hasUsedCoin())
                    player.sendMessage(new TextComponentString("You have already used a coin as substitute for an item."));
                else
                    player.sendMessage(new TextComponentString("You can substitute 1 item with a " + I18n.format(VCItems.COIN.getUnlocalizedName() + ".name")));
            }
        }
        else
        {
            //Check held item is a valid submission
            if(te.submitStack(heldItem))
            {
                player.sendMessage(new TextComponentString("Sacrificed " + heldItem.getDisplayName()));
                heldItem.stackSize--;
                if(heldItem.stackSize <= 0) heldItem = null;
                player.setHeldItem(hand, heldItem);
            }
            else if(heldItem.getItem() == VCItems.COIN && !te.hasUsedCoin())
            {
                //Submit coin as substitute
                ItemStack stack = te.substitute();
                player.sendMessage(new TextComponentString("Coin submitted as substitute for item " + stack.getDisplayName()));
                heldItem.stackSize--;
                if(heldItem.stackSize <= 0) heldItem = null;
                player.setHeldItem(hand, heldItem);
            }
            else
                player.sendMessage(new TextComponentString("This item is not required"));
        }

        //If submitted everything, then give items back
        if(te.hasSubmittedAll())
        {
            player.sendMessage(new TextComponentString("The sacrifice is complete"));
            te.readPlayerInventory(player);
            world.setBlockToAir(pos);
        }
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand)
    {
        if(rand.nextInt(6) == 0)
            for(int i = 0; i < 2; i++)
                world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + rand.nextDouble(), pos.getY() + 0.1d, pos.getZ() + rand.nextDouble(), 0.001d, 0.002d, 0.001d);

        if(rand.nextInt(2) == 0)
            for(int i = 0; i < 7; i++)
                world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + rand.nextDouble(), pos.getY() + 0.1d, pos.getZ() + rand.nextDouble(), 0.001d, 0.002d, 0.001d);
    }
}
