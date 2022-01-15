package me.geza3d.toldi.module.modules.render;

import static com.mojang.blaze3d.systems.RenderSystem.*;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import me.geza3d.toldi.events.RenderCallback;
import me.geza3d.toldi.module.EnumModuleType;
import me.geza3d.toldi.module.ToldiModule;
import me.geza3d.toldi.module.settings.Setting.ColorSetting;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.world.chunk.WorldChunk;

@ToldiModule.Type(EnumModuleType.RENDER)
public class StorageESP extends ToldiModule {

	ColorSetting color = new ColorSetting(this, "color", Color.CYAN.getRGB());
	
	@Listener
	public void onRender() {
		RenderCallback.LAST.register((matrices, tickDelta, camera)->{
			if(getStatus()) {
				for(int i = 0; i < getMC().world.getChunkManager().chunks.chunks.length(); i++) {
					WorldChunk chunk = getMC().world.getChunkManager().chunks.chunks.getPlain(i);
					if(chunk == null) continue;
					chunk.blockEntities.forEach((pos,entity)->{
						if(entity instanceof LockableContainerBlockEntity) {
							depthFunc(GL11.GL_ALWAYS);
							BufferBuilder buffer = Tessellator.getInstance().getBuffer();
							float x1 = (float) (pos.getX() - camera.getPos().x);
							float y1 = (float) (pos.getY() - camera.getPos().y);
							float z1 = (float) (pos.getZ() - camera.getPos().z);
							float x2 = x1 + 1;
							float y2 = y1 + 1;
							float z2 = z1 + 1;
							int color = this.color.getValue();
							float a = (float)(color >> 24 & 0xFF) / 255.0f;
				            float r = (float)(color >> 16 & 0xFF) / 255.0f;
				            float g = (float)(color >> 8 & 0xFF) / 255.0f;
				            float b = (float)(color & 0xFF) / 255.0f;
							buffer.begin(DrawMode.DEBUG_LINE_STRIP, VertexFormats.POSITION_COLOR);
							Matrix4f matrix = matrices.peek().getPositionMatrix();
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
							depthFunc(GL11.GL_LEQUAL);
						}
					});
				}
			}
			return ActionResult.SUCCESS;
		});
	}
}
