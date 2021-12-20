package me.geza3d.toldi.module.modules.world;

import me.geza3d.toldi.module.EnumModuleType;
import me.geza3d.toldi.module.ToldiModule;
import me.geza3d.toldi.module.settings.NumberSetting.DoubleSetting;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

@ToldiModule.Type(EnumModuleType.WORLD)
public class Timer extends ToldiModule{

	DoubleSetting speed = new DoubleSetting(this, "speed", 1d, 0d, 5d);
	
	@Override
	public void disable() {
		getMC().renderTickCounter.tickTime = 50f;
		super.disable();
	}
	
	@Listener
	public void onTick() {
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if(getStatus()) {
				client.renderTickCounter.tickTime = (float) (50f / speed.getValue());
			}
		});
	}
}
