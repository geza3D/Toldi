package me.geza3d.toldi.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.ActionResult;

public interface RenderCallback {

	public static final Event<RenderCallback> FIRST = EventFactory.createArrayBacked(RenderCallback.class, 
			listeners -> (matrices, tickDelta, camera) -> {
				for(RenderCallback listener : listeners) {
					ActionResult result = listener.render(matrices, tickDelta, camera);
					if(result == ActionResult.FAIL) return result;
				}
				return ActionResult.SUCCESS;
			});
	
	public static final Event<RenderCallback> LAST = EventFactory.createArrayBacked(RenderCallback.class, 
			listeners -> (matrices, tickDelta, camera) -> {
				for(RenderCallback listener : listeners) {
					ActionResult result = listener.render(matrices, tickDelta, camera);
					if(result == ActionResult.FAIL) return result;
				}
				return ActionResult.SUCCESS;
			});
	
	public static final Event<RenderCallback> ENTITIES = EventFactory.createArrayBacked(RenderCallback.class, 
			listeners -> (matrices, tickDelta, camera) -> {
				for(RenderCallback listener : listeners) {
					ActionResult result = listener.render(matrices, tickDelta, camera);
					if(result == ActionResult.FAIL) return result;
				}
				return ActionResult.SUCCESS;
			});
	
	ActionResult render(MatrixStack matrices, float tickDelta, Camera camera);
}
