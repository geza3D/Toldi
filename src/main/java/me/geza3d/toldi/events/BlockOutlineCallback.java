package me.geza3d.toldi.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.ActionResult;

public interface BlockOutlineCallback {

	/**
	 * If its cancelled it will make the BlockOutline render regardless if the player is the camera or not.
	 */
	public static final Event<BlockOutlineCallback> EVENT = EventFactory.createArrayBacked(BlockOutlineCallback.class,
			listeners -> () -> {
				for(BlockOutlineCallback listener : listeners) {
					ActionResult result = listener.spoof();
					if(result == ActionResult.FAIL) return result;
				}
				return ActionResult.SUCCESS;
			});
	
	ActionResult spoof();
}
