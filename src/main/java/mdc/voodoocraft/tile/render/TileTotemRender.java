package mdc.voodoocraft.tile.render;

import mdc.voodoocraft.tile.TileTotem;
import mdc.voodoocraft.util.EnumGlyphType;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import org.lwjgl.opengl.GL11;

public class TileTotemRender extends TileEntitySpecialRenderer<TileTotem>
{
    public void render(TileTotem te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        for(EnumFacing side : EnumFacing.HORIZONTALS)
        {
            //Get the glyph on the given side
            EnumGlyphType glyph = te.getSide(side);
            if(glyph == null) continue;

            GlStateManager.pushMatrix();
            GlStateManager.translate(x, y, z);
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.color(0.25f, 0.1f, 0.1f, 0.4f);

            //Fix the lighting
            int i = te.getWorld().getCombinedLight(te.getPos().offset(side), 0);
            int j = i % 65536;
            int k = i / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);

            //Bind the glyph texture to use to render
            bindTexture(glyph.getTextureLocation());

            Tessellator tes = Tessellator.getInstance();
            BufferBuilder buf = tes.getBuffer();
            buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

            float offset = (1f / 16f) * 0.9f;

            switch(side)
            {
                case NORTH: //-Z
                    buf.pos(1, 0, offset).tex(0, 1).endVertex();
                    buf.pos(0, 0, offset).tex(1, 1).endVertex();
                    buf.pos(0, 1, offset).tex(1, 0).endVertex();
                    buf.pos(1, 1, offset).tex(0, 0).endVertex();
                    break;
                case SOUTH: //+Z
                    buf.pos(0, 0, 1 - offset).tex(0, 1).endVertex();
                    buf.pos(1, 0, 1 - offset).tex(1, 1).endVertex();
                    buf.pos(1, 1, 1 - offset).tex(1, 0).endVertex();
                    buf.pos(0, 1, 1 - offset).tex(0, 0).endVertex();
                    break;
                case WEST: //-X
                    buf.pos(offset, 0, 0).tex(0, 1).endVertex();
                    buf.pos(offset, 0, 1).tex(1, 1).endVertex();
                    buf.pos(offset, 1, 1).tex(1, 0).endVertex();
                    buf.pos(offset, 1, 0).tex(0, 0).endVertex();
                    break;
                case EAST: //+X
                    buf.pos(1 - offset, 0, 0).tex(1, 1).endVertex();
                    buf.pos(1 - offset, 1, 0).tex(1, 0).endVertex();
                    buf.pos(1 - offset, 1, 1).tex(0, 0).endVertex();
                    buf.pos(1 - offset, 0, 1).tex(0, 1).endVertex();
            }

            tes.draw();
            GlStateManager.popMatrix();
        }
    }
}
