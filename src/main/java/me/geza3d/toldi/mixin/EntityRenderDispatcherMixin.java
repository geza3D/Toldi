package me.geza3d.toldi.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.geza3d.toldi.events.RenderEntityCallback;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.ActionResult;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {

	@Inject(method = "render", at = @At("HEAD"), cancellable = true)
	public void onRenderPre(Entity entity, double x, double y, double z, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo info) {
		ActionResult result = RenderEntityCallback.PRE.invoker().render((EntityRenderDispatcher) (Object) this, entity, x, y, z, tickDelta, matrices, vertexConsumers, light);
		if(result == ActionResult.FAIL) info.cancel();
	}
	
	@Inject(method = "render", at = @At("RETURN"))
	public void onRenderPost(Entity entity, double x, double y, double z, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo info) {
		RenderEntityCallback.POST.invoker().render((EntityRenderDispatcher) (Object) this, entity, x, y, z, tickDelta, matrices, vertexConsumers, light);
	}
}
