package me.geza3d.toldi.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.ActionResult;

public interface CameraSpoofCallback {

	/**
	 * If its cancelled it will make the BlockOutline render regardless if the player is the camera or not.
	 */
	public static final Event<CameraSpoofCallback> EVENT = EventFactory.createArrayBacked(CameraSpoofCallback.class,
			listeners -> () -> {
				for(CameraSpoofCallback listener : listeners) {
					ActionResult result = listener.spoof();
					if(result == ActionResult.FAIL) return result;
				}
				return ActionResult.SUCCESS;
			});
	
	/**
	 * Cancelling this will spoof the CameraPlayer to be the player and not the camera entity if the camera entity is a player.
	 */
	Event<CameraSpoofCallback> CAMERAPLAYER = EventFactory.createArrayBacked(CameraSpoofCallback.class,
			listeners -> () -> {
				for(CameraSpoofCallback listener : listeners) {
					ActionResult result = listener.spoof();
					if(result == ActionResult.FAIL) return result;
				}
				return ActionResult.SUCCESS;
			});

	ActionResult spoof();
}
