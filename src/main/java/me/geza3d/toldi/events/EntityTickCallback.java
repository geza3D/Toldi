package me.geza3d.toldi.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.util.ActionResult;

public interface EntityTickCallback {

	public static final Event<EntityTickCallback> TICK = EventFactory.createArrayBacked(EntityTickCallback.class, 
			listeners -> entity -> {
				for(EntityTickCallback listener : listeners) {
					ActionResult result = listener.tick(entity);
					if(result == ActionResult.FAIL) return result;
				}
				return ActionResult.SUCCESS;
			});
	
	ActionResult tick(Entity entity);
}
