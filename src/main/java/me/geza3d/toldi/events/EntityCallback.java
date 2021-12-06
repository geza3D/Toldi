package me.geza3d.toldi.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.util.ActionResult;

public interface EntityCallback {

	public static final Event<EntityCallback> TICK = EventFactory.createArrayBacked(EntityCallback.class, 
			listeners -> entity -> {
				for(EntityCallback listener : listeners) {
					ActionResult result = listener.tick(entity);
					if(result == ActionResult.FAIL) return result;
				}
				return ActionResult.SUCCESS;
			});
	
	public static final Event<EntityCallback> STOP_TICKING = EventFactory.createArrayBacked(EntityCallback.class, 
			listeners -> entity -> {
				for(EntityCallback listener : listeners) {
					ActionResult result = listener.tick(entity);
					if(result == ActionResult.FAIL) return result;
				}
				return ActionResult.SUCCESS;
			});
	
	ActionResult tick(Entity entity);
}
