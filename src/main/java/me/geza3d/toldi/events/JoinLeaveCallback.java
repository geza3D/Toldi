package me.geza3d.toldi.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.ActionResult;

public interface JoinLeaveCallback {

	public static final Event<JoinLeaveCallback> JOIN = EventFactory.createArrayBacked(JoinLeaveCallback.class,
			listeners -> () -> {
				for(JoinLeaveCallback listener : listeners) {
					ActionResult result = listener.handle();
					if(result == ActionResult.FAIL) return result;
				}
				return ActionResult.SUCCESS;
			});
	
	public static final Event<JoinLeaveCallback> LEAVE = EventFactory.createArrayBacked(JoinLeaveCallback.class,
			listeners -> () -> {
				for(JoinLeaveCallback listener : listeners) {
					ActionResult result = listener.handle();
					if(result == ActionResult.FAIL) return result;
				}
				return ActionResult.SUCCESS;
			});
	
	ActionResult handle();
}
