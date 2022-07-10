package me.geza3d.toldi.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.ActionResult;

public interface ChamsOverrideCallback {

	/**
	 * If its cancelled it will help the rendering of Chams by disabling some render features of the game.
	 */
	public static final Event<ChamsOverrideCallback> EVENT = EventFactory.createArrayBacked(ChamsOverrideCallback.class,
			listeners -> () -> {
				for(ChamsOverrideCallback listener : listeners) {
					ActionResult result = listener.override();
					if(result == ActionResult.FAIL) return result;
				}
				return ActionResult.SUCCESS;
			});

	ActionResult override();
}
