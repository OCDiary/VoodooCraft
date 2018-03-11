package mdc.voodoocraft.tile.render;

import mdc.voodoocraft.tile.TileDollPedestal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;

public class TileDollPedestalRender extends TileEntitySpecialRenderer<TileDollPedestal>{

	@Override
	public void render(TileDollPedestal te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
	{
		ItemStack dollStack = te.getDollStack();
		if(dollStack == null) return;
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);
		GlStateManager.translate(0.5, 0.95, 0.5);
		float angle = (((float)te.getWorld().getTotalWorldTime() + partialTicks) / 20.0F) * (180F / (float)Math.PI);
		GlStateManager.rotate(angle, 0, 1, 0);
		Minecraft.getMinecraft().getRenderItem().renderItem(dollStack, TransformType.GROUND);
		GlStateManager.popMatrix();
	}
}