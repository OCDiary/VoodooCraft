package mdc.voodoocraft.blocks;

import java.util.Random;

import mdc.voodoocraft.VoodooCraft;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class VCBlock extends Block
{
	 protected final Random BLOCK_RANDOM = new Random();

    public VCBlock(String name, Material mat)
    {
        super(mat);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(VoodooCraft.VOODOO_TAB);
    }
    
    public VCBlock(String name)
    {
    	this(name, Material.ROCK);
	}
}
