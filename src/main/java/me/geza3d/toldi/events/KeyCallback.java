package me.geza3d.toldi.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.ActionResult;

public interface KeyCallback {

	public static final Event<KeyCallback> EVENT = EventFactory.createArrayBacked(KeyCallback.class, 
			listeners -> (key,action) -> {
				for(KeyCallback listener : listeners) {
					ActionResult result = listener.press(key,action);
					if(result == ActionResult.FAIL) return result;
				}
				return ActionResult.SUCCESS;
			});
	ActionResult press(int key, int action);
}
