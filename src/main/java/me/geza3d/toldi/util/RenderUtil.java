package me.geza3d.toldi.util;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.systems.RenderSystem;

import me.geza3d.toldi.Toldi;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Matrix4f;

public class RenderUtil extends DrawableHelper{

	public static void glScissors(int x, int y, int width, int height) {
		Window window = Toldi.CLIENT.getWindow();
		int scale = Toldi.CLIENT.options.guiScale;
		GL11.glScissor(x * scale, (window.getScaledHeight()-y-height) * scale, width * scale, (height-1) * scale);
	}
	
	public static void drawPolygon(MatrixStack matrices, double startDegree, double endDegree, int corners, int radius, int x, int y, int color) {
        drawPolygon(matrices.peek().getPositionMatrix(), startDegree, endDegree, corners, radius, x, y, color);
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
    
    public static void drawColorCircle(MatrixStack matrix, int radius, int x, int y) {
    	drawColorCircle(matrix.peek().getPositionMatrix(), radius, x, y);
    }
    
    private static void drawColorCircle(Matrix4f matrix, int radius, int x, int y) {
    	x += radius;
    	y += radius;
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        bufferBuilder.begin(VertexFormat.DrawMode.TRIANGLE_FAN, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(x, y, 0f).color(1f, 1f, 1f, 1f).next();
        Color c = new Color(255,0,0,255);
        for(double i = 360; i > -1; i -= 1) {
        	int color = c.getRGB();
        	float[] hsb = {0,0,0};
        	hsb = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), hsb);
        	hsb[0] = (float) i / 360f;
        	c = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
        	float a = (float)(color >> 24 & 0xFF) / 255.0f;
            float r = (float)(color >> 16 & 0xFF) / 255.0f;
            float g = (float)(color >> 8 & 0xFF) / 255.0f;
            float b = (float)(color & 0xFF) / 255.0f;
        	bufferBuilder.vertex(x-Math.cos(Math.toRadians(i))*radius, y-Math.sin(Math.toRadians(i))*radius, 0.0D).color(r, g, b, a).next();
        }
        bufferBuilder.end();
        BufferRenderer.draw(bufferBuilder);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }
    
    public static void fillHGradient(MatrixStack matrix, int x, int y, int w, int h, int c1, int c2) {
    	fillHGradient(matrix.peek().getPositionMatrix(), x, y, w, h, c1, c2);
    }
    
    private static void fillHGradient(Matrix4f matrix, int x, int y, int w, int h, int c1, int c2) {
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        float a1 = (float)(c1 >> 24 & 0xFF) / 255.0f;
        float r1 = (float)(c1 >> 16 & 0xFF) / 255.0f;
        float g1 = (float)(c1 >> 8 & 0xFF) / 255.0f;
        float b1 = (float)(c1 & 0xFF) / 255.0f;
        float a2 = (float)(c2 >> 24 & 0xFF) / 255.0f;
        float r2 = (float)(c2 >> 16 & 0xFF) / 255.0f;
        float g2 = (float)(c2 >> 8 & 0xFF) / 255.0f;
        float b2 = (float)(c2 & 0xFF) / 255.0f;
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(x+w, y, 0f).color(r2, g2, b2, a2).next();
        bufferBuilder.vertex(x, y, 0f).color(r1, g1, b1, a1).next();
        bufferBuilder.vertex(x, y+h, 0f).color(r1, g1, b1, a1).next();
        bufferBuilder.vertex(x+w, y+h, 0f).color(r2, g2, b2, a2).next();
        bufferBuilder.end();
        BufferRenderer.draw(bufferBuilder);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }
    
    public static void drawRowBasedCheckerBoard(MatrixStack matrix, int rows, int x, int y, int w, int h, int c1, int c2) {
    	int cellsize = h / rows;
    	if(cellsize < 1) {
    		cellsize = 1;
    	}
    	boolean s1 = true;
    	boolean s2 = true;
    	int color = c1;
    	for(int i = 0; i+cellsize < h; i += cellsize) {
    		for(int j = 0; j+cellsize < w; j += cellsize) {
    			fill(matrix, x+j, y+i, x+j+cellsize, y+i+cellsize, color);
    			if(s1) {
    				color = c2;
    			} else {
    				color = c1;
    			}
    			s1 = !s1;
    		}
       		if(s2) {
       			color = c2;
       			s1 = false;
       		} else {
       			color = c1;
       			s1 = true;
       		}
       		s2 = !s2;
    	}
    }
    
    public static void drawBoxOutline(Matrix4f matrix, BlockPos pos, Camera cam, int rgb, float a) {
    	drawBoxOutline(matrix, pos.getX() - cam.getPos().x, pos.getY() - cam.getPos().y, pos.getZ() - cam.getPos().z, pos.getX()+1 - cam.getPos().x, pos.getY()+1 - cam.getPos().y, pos.getZ()+1 - cam.getPos().z, rgb, a);
    }
    
    public static void drawBoxOutline(Matrix4f matrix, BlockPos pos, Camera cam, int rgba) {
    	drawBoxOutline(matrix, pos.getX() - cam.getPos().x, pos.getY() - cam.getPos().y, pos.getZ() - cam.getPos().z, pos.getX()+1 - cam.getPos().x, pos.getY()+1 - cam.getPos().y, pos.getZ()+1 - cam.getPos().z, rgba);
    }
    
    public static void drawBoxOutline(Matrix4f matrix, BlockPos pos, Camera cam, float r, float g, float b, float a) {
    	drawBoxOutline(matrix, pos.getX() - cam.getPos().x, pos.getY() - cam.getPos().y, pos.getZ() - cam.getPos().z, pos.getX()+1 - cam.getPos().x, pos.getY()+1 - cam.getPos().y, pos.getZ()+1 - cam.getPos().z, r, g, b, a);
    }
    
    public static void drawBoxOutline(Matrix4f matrix, double x1, double y1, double z1, double x2, double y2, double z2, int rgba) {
    	float a = (float)(rgba >> 24 & 0xFF) / 255.0f;
    	drawBoxOutline(matrix, x1, y1, z1, x2, y2, z2, rgba, a);
    }
    
    public static void drawBoxOutline(Matrix4f matrix, double x1, double y1, double z1, double x2, double y2, double z2, int rgb, float a) {
    	float r = (float)(rgb >> 16 & 0xFF) / 255.0f;
        float g = (float)(rgb >> 8 & 0xFF) / 255.0f;
        float b = (float)(rgb & 0xFF) / 255.0f;
    	drawBoxOutline(matrix, x1, y1, z1, x2, y2, z2, r, g, b, a);
    }
    
    public static void drawBoxOutline(Matrix4f matrix, double x1, double y1, double z1, double x2, double y2, double z2, float r, float g, float b, float a) {
    	drawBoxOutline(matrix, (float) x1, (float) y1, (float) z1, (float) x2, (float) y2, (float) z2, r, g, b, a);
    }
    
    public static void drawBoxOutline(Matrix4f matrix, float x1, float y1, float z1, float x2, float y2, float z2, float r, float g, float b, float a) {
    	BufferBuilder buffer = Tessellator.getInstance().getBuffer();
    	buffer.begin(DrawMode.DEBUG_LINE_STRIP, VertexFormats.POSITION_COLOR);
		buffer.vertex(matrix, x1, y1, z1).color(r, g, b, a).next();
	    buffer.vertex(matrix, x2, y1, z1).color(r, g, b, a).next();
	    buffer.vertex(matrix, x2, y2, z1).color(r, g, b, a).next();
	    buffer.vertex(matrix, x2, y2, z2).color(r, g, b, a).next();
	    buffer.vertex(matrix, x2, y1, z2).color(r, g, b, a).next();
	    buffer.vertex(matrix, x1, y1, z2).color(r, g, b, a).next();
	    buffer.vertex(matrix, x1, y2, z2).color(r, g, b, a).next();
	    buffer.vertex(matrix, x1, y2, z1).color(r, g, b, a).next();
	    buffer.vertex(matrix, x1, y1, z1).color(r, g, b, a).next();
	    buffer.vertex(matrix, x1, y1, z2).color(r, g, b, a).next();
	    buffer.vertex(matrix, x1, y2, z2).color(r, g, b, a).next();
	    buffer.vertex(matrix, x2, y2, z2).color(r, g, b, a).next();
	    buffer.vertex(matrix, x2, y1, z2).color(r, g, b, a).next();
	    buffer.vertex(matrix, x2, y1, z1).color(r, g, b, a).next();
	    buffer.vertex(matrix, x2, y2, z1).color(r, g, b, a).next();
	    buffer.vertex(matrix, x1, y2, z1).color(r, g, b, a).next();
	    buffer.vertex(matrix, x1, y1, z1).color(r, g, b, a).next();
		buffer.end();
		BufferRenderer.draw(buffer);
    }
    
    public static void drawFilledBox(Matrix4f matrix, BlockPos pos, Camera cam, int rgb, float a) {
    	drawFilledBox(matrix, pos.getX() - cam.getPos().x, pos.getY() - cam.getPos().y, pos.getZ() - cam.getPos().z, pos.getX()+1 - cam.getPos().x, pos.getY()+1 - cam.getPos().y, pos.getZ()+1 - cam.getPos().z, rgb, a);
    }
    
    public static void drawFilledBox(Matrix4f matrix, BlockPos pos, Camera cam, int rgba) {
    	drawFilledBox(matrix, pos.getX() - cam.getPos().x, pos.getY() - cam.getPos().y, pos.getZ() - cam.getPos().z, pos.getX()+1 - cam.getPos().x, pos.getY()+1 - cam.getPos().y, pos.getZ()+1 - cam.getPos().z, rgba);
    }
    
    public static void drawFilledBox(Matrix4f matrix, BlockPos pos, Camera cam, float r, float g, float b, float a) {
    	drawFilledBox(matrix, pos.getX() - cam.getPos().x, pos.getY() - cam.getPos().y, pos.getZ() - cam.getPos().z, pos.getX()+1 - cam.getPos().x, pos.getY()+1 - cam.getPos().y, pos.getZ()+1 - cam.getPos().z, r, g, b, a);
    }
    
    public static void drawFilledBox(Matrix4f matrix, double x1, double y1, double z1, double x2, double y2, double z2, int rgba) {
    	float a = (float)(rgba >> 24 & 0xFF) / 255.0f;
    	drawFilledBox(matrix, x1, y1, z1, x2, y2, z2, rgba, a);
    }
    
    public static void drawFilledBox(Matrix4f matrix, double x1, double y1, double z1, double x2, double y2, double z2, int rgb, float a) {
    	float r = (float)(rgb >> 16 & 0xFF) / 255.0f;
        float g = (float)(rgb >> 8 & 0xFF) / 255.0f;
        float b = (float)(rgb & 0xFF) / 255.0f;
        drawFilledBox(matrix, x1, y1, z1, x2, y2, z2, r, g, b, a);
    }
    
    public static void drawFilledBox(Matrix4f matrix, double x1, double y1, double z1, double x2, double y2, double z2, float r, float g, float b, float a) {
    	drawFilledBox(matrix, (float) x1, (float) y1, (float) z1, (float) x2, (float) y2, (float) z2, r, g, b, a);
    }
    
    public static void drawFilledBox(Matrix4f matrix, float x1, float y1, float z1, float x2, float y2, float z2, float r, float g, float b, float a) {
    	BufferBuilder buffer = Tessellator.getInstance().getBuffer();
    	buffer.begin(DrawMode.TRIANGLE_STRIP, VertexFormats.POSITION_COLOR);
        buffer.vertex(matrix, x1, y1, z1).color(r, g, b, a).next();
        buffer.vertex(matrix, x1, y1, z1).color(r, g, b, a).next();
        buffer.vertex(matrix, x1, y1, z1).color(r, g, b, a).next();
        buffer.vertex(matrix, x1, y1, z2).color(r, g, b, a).next();
        buffer.vertex(matrix, x1, y2, z1).color(r, g, b, a).next();
        buffer.vertex(matrix, x1, y2, z2).color(r, g, b, a).next();
        buffer.vertex(matrix, x1, y2, z2).color(r, g, b, a).next();
        buffer.vertex(matrix, x1, y1, z2).color(r, g, b, a).next();
        buffer.vertex(matrix, x2, y2, z2).color(r, g, b, a).next();
        buffer.vertex(matrix, x2, y1, z2).color(r, g, b, a).next();
        buffer.vertex(matrix, x2, y1, z2).color(r, g, b, a).next();
        buffer.vertex(matrix, x2, y1, z1).color(r, g, b, a).next();
        buffer.vertex(matrix, x2, y2, z2).color(r, g, b, a).next();
        buffer.vertex(matrix, x2, y2, z1).color(r, g, b, a).next();
        buffer.vertex(matrix, x2, y2, z1).color(r, g, b, a).next();
        buffer.vertex(matrix, x2, y1, z1).color(r, g, b, a).next();
        buffer.vertex(matrix, x1, y2, z1).color(r, g, b, a).next();
        buffer.vertex(matrix, x1, y1, z1).color(r, g, b, a).next();
        buffer.vertex(matrix, x1, y1, z1).color(r, g, b, a).next();
        buffer.vertex(matrix, x2, y1, z1).color(r, g, b, a).next();
        buffer.vertex(matrix, x1, y1, z2).color(r, g, b, a).next();
        buffer.vertex(matrix, x2, y1, z2).color(r, g, b, a).next();
        buffer.vertex(matrix, x2, y1, z2).color(r, g, b, a).next();
        buffer.vertex(matrix, x1, y2, z1).color(r, g, b, a).next();
        buffer.vertex(matrix, x1, y2, z1).color(r, g, b, a).next();
        buffer.vertex(matrix, x1, y2, z2).color(r, g, b, a).next();
        buffer.vertex(matrix, x2, y2, z1).color(r, g, b, a).next();
        buffer.vertex(matrix, x2, y2, z2).color(r, g, b, a).next();
        buffer.vertex(matrix, x2, y2, z2).color(r, g, b, a).next();
        buffer.vertex(matrix, x2, y2, z2).color(r, g, b, a).next();
        buffer.end();
        BufferRenderer.draw(buffer);
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
