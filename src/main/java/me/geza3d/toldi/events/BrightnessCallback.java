package me.geza3d.toldi.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.ActionResult;

public interface BrightnessCallback {

	/**
	 * Cancelling this will make the brightness 1000f, without changing its actual value.
	 */
	Event<BrightnessCallback> EVENT = EventFactory.createArrayBacked(BrightnessCallback.class,
			listeners -> () -> {
				for(BrightnessCallback listener : listeners) {
					ActionResult result = listener.spoof();
					if(result == ActionResult.FAIL) return result;
				}
				return ActionResult.SUCCESS;
			});

	ActionResult spoof();
}
