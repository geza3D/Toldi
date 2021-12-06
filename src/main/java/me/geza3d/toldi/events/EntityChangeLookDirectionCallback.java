package me.geza3d.toldi.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.util.ActionResult;

public interface EntityChangeLookDirectionCallback {

	public static final Event<EntityChangeLookDirectionCallback> EVENT = EventFactory.createArrayBacked(EntityChangeLookDirectionCallback.class, 
			listeners -> (entity,x,y) -> {
				for(EntityChangeLookDirectionCallback listener : listeners) {
					ActionResult result = listener.tick(entity,x,y);
					if(result == ActionResult.FAIL) return result;
				}
				return ActionResult.SUCCESS;
			});
	
	ActionResult tick(Entity entity, double cursorDeltaX, double cursorDeltaY);
}
