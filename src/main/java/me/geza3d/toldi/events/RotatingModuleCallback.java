package me.geza3d.toldi.events;

import me.geza3d.toldi.module.ToldiRotatingModule;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface RotatingModuleCallback {

	public static final Event<RotatingModuleCallback> DISABLE = EventFactory.createArrayBacked(RotatingModuleCallback.class, 
			listeners -> module -> {
				for(RotatingModuleCallback listener : listeners) {
					listener.handle(module);
				}
			});
	
	void handle(ToldiRotatingModule module);
}
