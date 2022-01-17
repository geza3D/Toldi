package me.geza3d.toldi.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.ActionResult;

public interface SendMovementPacketsCallback {

	public static final Event<SendMovementPacketsCallback> PRE = EventFactory.createArrayBacked(SendMovementPacketsCallback.class, 
			listeners -> player -> {
				for(SendMovementPacketsCallback listener : listeners) {
					ActionResult result = listener.sendPackets(player);
					if(result == ActionResult.FAIL) return result;
				}
				return ActionResult.SUCCESS;
			});
	
	public static final Event<SendMovementPacketsCallback> POST = EventFactory.createArrayBacked(SendMovementPacketsCallback.class, 
			listeners -> player -> {
				for(SendMovementPacketsCallback listener : listeners) {
					listener.sendPackets(player);
				}
				return ActionResult.SUCCESS;
			});
	
	ActionResult sendPackets(ClientPlayerEntity player);
}
