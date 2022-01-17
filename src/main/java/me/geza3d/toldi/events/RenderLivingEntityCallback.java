package me.geza3d.toldi.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.ActionResult;

public interface RenderLivingEntityCallback {

	public static final Event<RenderLivingEntityCallback> PRE = EventFactory.createArrayBacked(RenderLivingEntityCallback.class, 
			listeners -> (matrices, entity) -> {
				for(RenderLivingEntityCallback listener : listeners) {
					ActionResult result = listener.render(matrices, entity);
					if(result == ActionResult.FAIL) return result;
				}
				return ActionResult.SUCCESS;
			});
	
	public static final Event<RenderLivingEntityCallback> POST = EventFactory.createArrayBacked(RenderLivingEntityCallback.class, 
			listeners -> (matrices, entity) -> {
				for(RenderLivingEntityCallback listener : listeners) {
					listener.render(matrices, entity);
				}
				return ActionResult.SUCCESS;
			});
	
	ActionResult render(MatrixStack matrices, Entity entity);
}
