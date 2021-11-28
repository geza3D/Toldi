package me.geza3d.toldi.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.network.Packet;
import net.minecraft.util.ActionResult;

public interface PacketCallback{

	public static final Event<PacketCallback> IN = EventFactory.createArrayBacked(PacketCallback.class, 
			listeners -> packet -> {
				for(PacketCallback listener : listeners) {
					ActionResult result = listener.packet(packet);
					if(result == ActionResult.FAIL) return result;
				}
				return ActionResult.SUCCESS;
			});
	
	public static final Event<PacketCallback> OUT = EventFactory.createArrayBacked(PacketCallback.class, 
			listeners -> packet -> {
				for(PacketCallback listener : listeners) {
					ActionResult result = listener.packet(packet);
					if(result == ActionResult.FAIL) return result;
				}
				return ActionResult.SUCCESS;
			});
	
	ActionResult packet(Packet<?> packet);
}
