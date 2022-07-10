package me.geza3d.toldi.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface InitCallback {

	Event<InitCallback> EVENT = EventFactory.createArrayBacked(InitCallback.class,
			listeners -> () -> {
				for(InitCallback listener : listeners) {
					listener.init();
				}
			});

	void init();
}
