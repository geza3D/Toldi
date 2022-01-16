package me.geza3d.toldi.module.modules.render;

import static com.mojang.blaze3d.systems.RenderSystem.*;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import me.geza3d.toldi.events.RenderCallback;
import me.geza3d.toldi.module.EnumModuleType;
import me.geza3d.toldi.module.ToldiModule;
import me.geza3d.toldi.module.settings.Setting.BooleanSetting;
import me.geza3d.toldi.module.settings.Setting.ColorSetting;
import me.geza3d.toldi.util.RenderUtil;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.world.chunk.WorldChunk;

@ToldiModule.Type(EnumModuleType.RENDER)
public class StorageESP extends ToldiModule {

	BooleanSetting dynamicColor = new BooleanSetting(this, "dynamiccolor", true);
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
							int color;
							if(dynamicColor.getValue()) {
								Color c = new Color(getWorld().getBlockState(pos).getMapColor(getWorld(), pos).color);
								color = new Color(c.getRed(), c.getGreen(), c.getBlue(), 255).getRGB();
							} else {
								color = this.color.getValue();
							}
				            Matrix4f matrix = matrices.peek().getPositionMatrix();
				            enableBlend();
							blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
							depthFunc(GL11.GL_ALWAYS);
							RenderUtil.drawFilledBox(matrix, pos, camera, color, 0.5f);
							RenderUtil.drawBoxOutline(matrix, pos, camera, color);
							depthFunc(GL11.GL_LEQUAL);
							disableBlend();
						}
					});
				}
			}
			return ActionResult.SUCCESS;
		});
	}
}
