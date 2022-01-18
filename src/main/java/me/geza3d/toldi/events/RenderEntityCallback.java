package me.geza3d.toldi.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.ActionResult;

public interface RenderEntityCallback {

	public static final Event<RenderEntityCallback> PRE = EventFactory.createArrayBacked(RenderEntityCallback.class, 
			listeners -> (dispatcher, entity, x, y, z, delta, matrices, consumer, light) -> {
				for(RenderEntityCallback listener : listeners) {
					ActionResult result = listener.render(dispatcher, entity, x, y, z, delta, matrices, consumer, light);
					if(result == ActionResult.FAIL) return result;
				}
				return ActionResult.SUCCESS;
			});
	
	public static final Event<RenderEntityCallback> POST = EventFactory.createArrayBacked(RenderEntityCallback.class, 
			listeners -> (dispatcher, entity, x, y, z, delta, matrices, consumer, light) -> {
				for(RenderEntityCallback listener : listeners) {
					listener.render(dispatcher, entity, x, y, z, delta, matrices, consumer, light);
				}
				return ActionResult.SUCCESS;
			});
	
	ActionResult render(EntityRenderDispatcher dispatcher, Entity entity, double x, double y, double z, float delta, MatrixStack matrices, VertexConsumerProvider consumer, int light);
}
