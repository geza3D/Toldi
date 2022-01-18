package me.geza3d.toldi.module;

import me.geza3d.toldi.events.RotatingModuleCallback;
import me.geza3d.toldi.util.RotationUtil;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class ToldiRotatingModule extends ToldiModule {

	protected int priority;
	
	public ToldiRotatingModule() {
		ClientTickEvents.START_CLIENT_TICK.register(client -> {
			if(getRawStatus()) {
				updatePriority();
			}
		});
	}
	
	public void updatePriority() {
		if(priority == -1) {
			info = "";
		} else {
			if(RotationUtil.currentPriority <= priority) {
				RotationUtil.currentPriority = priority;
				info = "[ON]";
			} else {
				info = "[HOLD]";
			}
		}
	}
	
	@Override
	public void enable() {
		updatePriority();
		super.enable();
	}
	
	@Override
	public void disable() {
		super.disable();
		RotationUtil.currentPriority = -1;
		RotatingModuleCallback.DISABLE.invoker().handle(this);
	}
	
	public boolean isOnTurn() {
		return RotationUtil.currentPriority == priority;
	}
}
