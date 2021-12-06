package me.geza3d.toldi.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.ActionResult;

public interface ServerPlayerEntityCallback {

	public static final Event<ServerPlayerEntityCallback> TICK = EventFactory.createArrayBacked(ServerPlayerEntityCallback.class, 
			listeners -> () -> {
				for(ServerPlayerEntityCallback listener : listeners) {
					ActionResult result = listener.tick();
					if(result == ActionResult.FAIL) return result;
				}
				return ActionResult.SUCCESS;
			});
	
	ActionResult tick();
}
