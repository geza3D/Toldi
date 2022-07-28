package me.geza3d.toldi.module.modules.render;

import java.awt.Color;

import me.geza3d.toldi.events.ChamsOverrideCallback;
import me.geza3d.toldi.events.RenderCallback;
import me.geza3d.toldi.init.Modules;
import me.geza3d.toldi.module.EnumModuleType;
import me.geza3d.toldi.module.ToldiModule;
import me.geza3d.toldi.module.ToldiModule.Type;
import me.geza3d.toldi.module.settings.Setting.ColorSetting;
import me.geza3d.toldi.module.settings.Setting.ModeSetting;
import me.geza3d.toldi.render.ToldiShaders;
import me.geza3d.toldi.util.RenderUtil;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.RenderLayer.MultiPhaseParameters;
import net.minecraft.client.render.RenderLayer.MultiPhaseParameters.Builder;
import net.minecraft.client.render.RenderPhase.Cull;
import net.minecraft.client.render.RenderPhase.DepthTest;
import net.minecraft.client.render.RenderPhase.Transparency;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;

@Type(EnumModuleType.RENDER)
public class Chams extends ToldiModule{

	ColorSetting hidden = new ColorSetting(this, "hidden", Color.CYAN.getRGB());
	ColorSetting visible = new ColorSetting(this, "visible", Color.CYAN.getRGB());
	ModeSetting mode = new ModeSetting(this, "mode", 0, "hidden","visible","both");
	
	Frustum frustum;
	Builder builderLequal = MultiPhaseParameters.builder().shader(ToldiShaders.OUTLINE_ALPHA).depthTest(DepthTest.LEQUAL_DEPTH_TEST).cull(Cull.DISABLE_CULLING).transparency(Transparency.TRANSLUCENT_TRANSPARENCY);
	Builder builderAlways = MultiPhaseParameters.builder().shader(ToldiShaders.OUTLINE_ALPHA).depthTest(DepthTest.ALWAYS_DEPTH_TEST).cull(Cull.DISABLE_CULLING).transparency(Transparency.TRANSLUCENT_TRANSPARENCY);
	
	private static boolean doChamsOverride = false;
	
	@Listener
	public void onRenderLast() {
		RenderCallback.LAST.register((matrices, tickDelta, camera) -> {
			if(getStatus()) {
				for(Entity entity : getWorld().getEntities()) {		
					EntityRenderDispatcher dispatcher = getMC().getEntityRenderDispatcher();
					EntityRenderer<? super Entity> renderer = dispatcher.getRenderer(entity);
					WorldRenderer wr = getMC().worldRenderer;
			        Immediate immediate = wr.bufferBuilders.getEntityVertexConsumers();
					if(entity == getPlayer() || entity == Modules.FREECAM.cam) continue;
					
			        RenderPhase.Texture texture = new RenderPhase.Texture(renderer.getTexture(entity), false, false);
					double x = MathHelper.lerp(tickDelta, entity.prevX, entity.getX()) - camera.getPos().x;
					double y = MathHelper.lerp(tickDelta, entity.prevY, entity.getY()) - camera.getPos().y;
					double z = MathHelper.lerp(tickDelta, entity.prevZ, entity.getZ()) - camera.getPos().z;
					float yaw = MathHelper.lerp(tickDelta, entity.prevYaw, entity.getYaw());
					matrices.push();
					matrices.translate(x, y, z);
					//doChamsOverride = true;
					//Hidden
					if(mode.getValue() != 1) {
						RenderLayer entityesp = RenderLayer.of("entityesp_always", VertexFormats.POSITION_COLOR_TEXTURE, VertexFormat.DrawMode.QUADS, 256, builderAlways.texture(texture).build(false));
						renderCham(hidden, entityesp, renderer, entity, tickDelta, matrices, immediate);
						immediate.draw();
						renderer.render(entity, yaw, tickDelta, matrices, immediate, 1);
						immediate.draw();
					}
					//Visible
					if(mode.getValue() != 0) {
						RenderLayer entityesp = RenderLayer.of("entityesp_lequal", VertexFormats.POSITION_COLOR_TEXTURE, VertexFormat.DrawMode.QUADS, 256, builderLequal.texture(texture).build(false));
						renderCham(visible, entityesp, renderer, entity, tickDelta, matrices, immediate);
						immediate.draw();
					}
					//doChamsOverride = false;
					matrices.pop();
				}
			}
			return ActionResult.SUCCESS;
		});
	}
	
	@Listener
	public void onChamsOverride() {
		ChamsOverrideCallback.EVENT.register(() -> {
			if(getStatus() && doChamsOverride) {
				return ActionResult.FAIL;
			}
			return ActionResult.SUCCESS;
		});
	}
	
	private static void renderCham(ColorSetting color, RenderLayer layer, EntityRenderer<? super Entity> renderer, Entity entity, float tickDelta, MatrixStack matrices, Immediate immediate) {
		RenderUtil.setRenderColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
		RenderUtil.entityCutoutNoCullOverride = layer;
		RenderUtil.itemEntityTranslucentCull = layer;
		RenderUtil.outlineOverride = layer;
		RenderUtil.entityTranslucentOverride = layer;
		renderer.render(entity, MathHelper.lerp(tickDelta, entity.prevYaw, entity.getYaw()), tickDelta, matrices, immediate, 1);
		RenderUtil.entityTranslucentOverride = null;
		RenderUtil.outlineOverride = null;
		RenderUtil.itemEntityTranslucentCull = null;
		RenderUtil.entityCutoutNoCullOverride = null;
		RenderUtil.clearRenderColor();
	}
	
	public void setupFrustum(MatrixStack matrices, Vec3d pos, Matrix4f projectionMatrix) {
        Matrix4f matrix4f = matrices.peek().getPositionMatrix();
        double d = pos.getX();
        double e = pos.getY();
        double f = pos.getZ();
        this.frustum = new Frustum(matrix4f, projectionMatrix);
        this.frustum.setPosition(d, e, f);
    }
}
