package me.geza3d.toldi.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.ActionResult;

public interface HudCallback {

	/**
	 * Cancelling this will spoof the CameraPlayer to be the player and not the camera entity if the camera entity is a player.
	 */
	public static final Event<HudCallback> CAMERAPLAYER = EventFactory.createArrayBacked(HudCallback.class,
			listeners -> () -> {
				for(HudCallback listener : listeners) {
					ActionResult result = listener.spoof();
					if(result == ActionResult.FAIL) return result;
				}
				return ActionResult.SUCCESS;
			});
	ActionResult spoof();
}
