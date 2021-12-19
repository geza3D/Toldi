package me.geza3d.toldi.module.modules.render;

import static com.mojang.blaze3d.systems.RenderSystem.*;

import org.lwjgl.opengl.GL11;

import me.geza3d.toldi.events.RenderCallback;
import me.geza3d.toldi.module.EnumModuleType;
import me.geza3d.toldi.module.ToldiModule;
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
							buffer.begin(DrawMode.DEBUG_LINE_STRIP, VertexFormats.POSITION_COLOR);
							Matrix4f matrix = matrices.peek().getPositionMatrix();
							buffer.vertex(matrix, x1, y1, z1).color(1f, 1f, 1f, 1f).next();
						    buffer.vertex(matrix, x2, y1, z1).color(1f, 1f, 1f, 1f).next();
						    buffer.vertex(matrix, x2, y2, z1).color(1f, 1f, 1f, 1f).next();
						    buffer.vertex(matrix, x2, y2, z2).color(1f, 1f, 1f, 1f).next();
						    buffer.vertex(matrix, x2, y1, z2).color(1f, 1f, 1f, 1f).next();
						    buffer.vertex(matrix, x1, y1, z2).color(1f, 1f, 1f, 1f).next();
						    buffer.vertex(matrix, x1, y2, z2).color(1f, 1f, 1f, 1f).next();
						    buffer.vertex(matrix, x1, y2, z1).color(1f, 1f, 1f, 1f).next();
						    buffer.vertex(matrix, x1, y1, z1).color(1f, 1f, 1f, 1f).next();
						    buffer.vertex(matrix, x1, y1, z2).color(1f, 1f, 1f, 1f).next();
						    buffer.vertex(matrix, x1, y2, z2).color(1f, 1f, 1f, 1f).next();
						    buffer.vertex(matrix, x2, y2, z2).color(1f, 1f, 1f, 1f).next();
						    buffer.vertex(matrix, x2, y1, z2).color(1f, 1f, 1f, 1f).next();
						    buffer.vertex(matrix, x2, y1, z1).color(1f, 1f, 1f, 1f).next();
						    buffer.vertex(matrix, x2, y2, z1).color(1f, 1f, 1f, 1f).next();
						    buffer.vertex(matrix, x1, y2, z1).color(1f, 1f, 1f, 1f).next();
						    buffer.vertex(matrix, x1, y1, z1).color(1f, 1f, 1f, 1f).next();
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
