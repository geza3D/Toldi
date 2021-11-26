package me.geza3d.toldi.util;

import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.util.math.MatrixStack;

public class GuiUtil {

	public static void drawRect(MatrixStack matrices, int x, int y, int width, int height, int color) {
		float a = (float)(color >> 24 & 0xFF) / 255.0f;
        float r = (float)(color >> 16 & 0xFF) / 255.0f;
        float g = (float)(color >> 8 & 0xFF) / 255.0f;
        float b = (float)(color & 0xFF) / 255.0f;
		BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
	}
}
