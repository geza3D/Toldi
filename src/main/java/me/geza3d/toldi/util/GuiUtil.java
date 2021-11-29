package me.geza3d.toldi.util;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.systems.RenderSystem;

import me.geza3d.toldi.Toldi;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;

public class GuiUtil {

	public static void glScissors(int x, int y, int width, int height) {
		Window window = Toldi.CLIENT.getWindow();
		GL11.glScissor(x * 2, (window.getScaledHeight()-y-height) * 2, width * 2, (height-1) * 2);
	}
	
	public static void drawPolygon(MatrixStack matrices, double startDegree, double endDegree, int corners, int radius, int x, int y, int color) {
        drawPolygon(matrices.peek().getModel(), startDegree, endDegree, corners, radius, x, y, color);
    }

    private static void drawPolygon(Matrix4f matrix, double startDegree, double endDegree, int corners, int radius, int x, int y, int color) {
    	double increment = 360 / (double) corners;
    	x += radius;
    	y += radius;
        float a = (float)(color >> 24 & 0xFF) / 255.0f;
        float r = (float)(color >> 16 & 0xFF) / 255.0f;
        float g = (float)(color >> 8 & 0xFF) / 255.0f;
        float b = (float)(color & 0xFF) / 255.0f;
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        bufferBuilder.begin(VertexFormat.DrawMode.TRIANGLE_FAN, VertexFormats.POSITION_COLOR);
        for(double i = endDegree; i > startDegree; i-=increment) {
        	bufferBuilder.vertex(x-Math.cos(Math.toRadians(i))*radius, y-Math.sin(Math.toRadians(i))*radius, 0.0D).color(r, g, b, a).next();
        }
        bufferBuilder.end();
        BufferRenderer.draw(bufferBuilder);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }
    /*
    public static void drawPolygonOutline(double startDegree, double endDegree, int corners, int x, int y, int radius, float width, int color) {
    	double increment = 360 / (double) corners;
    	x += radius;
    	y += radius;
    	GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
        GlStateManager.disableTexture2D();
        GlStateManager.enableAlpha();
        GlStateManager.depthMask(false);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
        GL11.glLineWidth(width);

        float a = (float)(color >> 24 & 255) / 255.0F;
        float r = (float)(color >> 16 & 255) / 255.0F;
        float g = (float)(color >> 8 & 255) / 255.0F;
        float b = (float)(color & 255) / 255.0F;

        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);
        for(double i = startDegree; i <= endDegree; i+=increment) {
        	bufferbuilder.pos(x-Math.cos(Math.toRadians(i))*radius, y-Math.sin(Math.toRadians(i))*radius, 0.0D).color(r, g, b, a).endVertex();
        }
        bufferbuilder.pos(x-Math.cos(Math.toRadians(endDegree))*radius, y-Math.sin(Math.toRadians(endDegree))*radius, 0.0D).color(r, g, b, a).endVertex();
        tessellator.draw();
        GL11.glDisable(GL_LINE_SMOOTH);
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }*/
}
