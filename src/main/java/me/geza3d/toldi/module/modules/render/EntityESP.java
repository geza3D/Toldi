package me.geza3d.toldi.module.modules.render;

import static com.mojang.blaze3d.systems.RenderSystem.blendFunc;
import static com.mojang.blaze3d.systems.RenderSystem.depthFunc;
import static com.mojang.blaze3d.systems.RenderSystem.disableBlend;
import static com.mojang.blaze3d.systems.RenderSystem.enableBlend;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.systems.RenderSystem;

import me.geza3d.toldi.events.RenderCallback;
import me.geza3d.toldi.init.Modules;
import me.geza3d.toldi.module.EnumModuleType;
import me.geza3d.toldi.module.ToldiModule;
import me.geza3d.toldi.module.ToldiModule.Type;
import me.geza3d.toldi.module.settings.Setting.BooleanSetting;
import me.geza3d.toldi.module.settings.Setting.ColorSetting;
import me.geza3d.toldi.module.settings.Setting.ModeSetting;
import me.geza3d.toldi.util.RenderUtil;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;

@Type(EnumModuleType.RENDER)
public class EntityESP extends ToldiModule{

	ColorSetting color = new ColorSetting(this, "color", Color.CYAN.getRGB());
	ModeSetting mode = new ModeSetting(this, "mode", 0, "box","line","cross");
	BooleanSetting mobs = new BooleanSetting(this, "mobs", true);
	BooleanSetting players = new BooleanSetting(this, "players", true);
	BooleanSetting inanimate = new BooleanSetting(this, "inanimate", true);
	
	//I'm going to learn OpenGL and get back to this later... I won't give up on making a custom shader ESP that easily... TODO
	@Listener
	public void onRender() {
		RenderCallback.LAST.register((matrices, tickDelta, camera) -> {
			if(getStatus()) {
		        RenderSystem.setShader(GameRenderer::getPositionColorShader);
		        enableBlend();
				blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				depthFunc(GL11.GL_ALWAYS);
				for(Entity entity : getWorld().getEntities()) {
					if(entity == getPlayer() || entity == Modules.FREECAM.cam) continue;
					if(entity instanceof LivingEntity && mobs.getValue() && !(entity instanceof PlayerEntity)
							|| entity instanceof PlayerEntity && players.getValue()
							|| inanimate.getValue() && !(entity instanceof LivingEntity) && !(entity instanceof PlayerEntity)) {
						double x = MathHelper.lerp(tickDelta, entity.prevX, entity.getX()) - camera.getPos().x;
						double y = MathHelper.lerp(tickDelta, entity.prevY, entity.getY()) - camera.getPos().y;
						double z = MathHelper.lerp(tickDelta, entity.prevZ, entity.getZ()) - camera.getPos().z;
						matrices.push();
						switch(mode.getValue()) {
						case 0:
							matrices.translate(x, y, z);
							if(entity instanceof LivingEntity) {
								matrices.multiply(new Quaternion(0, -MathHelper.lerp(tickDelta, ((LivingEntity) entity).prevBodyYaw, ((LivingEntity) entity).bodyYaw), 0, true));
							} else {
								matrices.multiply(new Quaternion(0, -MathHelper.lerp(tickDelta, entity.prevYaw, entity.getYaw()), 0, true));
							}
				        	RenderUtil.drawBoxOutline(matrices.peek().getPositionMatrix(), -entity.getWidth()/2, 0, -entity.getWidth()/2, entity.getWidth()/2, entity.getHeight(), entity.getWidth()/2, color.getValue());    
				        	break;
						case 1:
							matrices.translate(x, y, z);
							RenderUtil.drawBoxOutline(matrices.peek().getPositionMatrix(), 0, 0, 0, 0, entity.getHeight(), 0, color.getValue());
							break;
						case 2:
							matrices.translate(x, y, z);
							if(entity instanceof LivingEntity) {
								matrices.multiply(new Quaternion(0, -MathHelper.lerp(tickDelta, ((LivingEntity) entity).prevBodyYaw, ((LivingEntity) entity).bodyYaw), 0, true));
							} else {
								matrices.multiply(new Quaternion(0, -MathHelper.lerp(tickDelta, entity.prevYaw, entity.getYaw()), 0, true));
							}
							RenderUtil.drawBoxOutline(matrices.peek().getPositionMatrix(), 0, 0, 0, 0, entity.getHeight(), 0, color.getValue());
							RenderUtil.drawBoxOutline(matrices.peek().getPositionMatrix(), -entity.getWidth()/2, entity.getHeight() * 0.7, 0, entity.getWidth()/2, entity.getHeight() * 0.7, 0, color.getValue());
							break;
						}
				       	matrices.pop();
					}
				}
				depthFunc(GL11.GL_LEQUAL);
				disableBlend();
			}
			return ActionResult.SUCCESS;
		});
	}
}
