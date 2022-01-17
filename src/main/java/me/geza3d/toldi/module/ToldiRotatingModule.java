package me.geza3d.toldi.module;

import me.geza3d.toldi.events.RotatingModuleCallback;
import me.geza3d.toldi.util.RotationUtil;

public class ToldiRotatingModule extends ToldiModule {

	public int priority;
	
	public ToldiRotatingModule(int priority) {
		this.priority = priority;
		RotatingModuleCallback.DISABLE.register(module -> {
			if(getRawStatus()) {
				updatePriority();
			}
		});
	}
	
	public void updatePriority() {
		if(RotationUtil.currentPriority < priority) {
			RotationUtil.currentPriority = priority;
			info = "[ON]";
		} else {
			info = "[OFF]";
		}
	}
	
	@Override
	public void enable() {
		
		super.enable();
	}
	
	@Override
	public void disable() {
		RotationUtil.currentPriority = -1;
		RotatingModuleCallback.DISABLE.invoker().handle(this);
		super.disable();
	}
	
	@Override
	public boolean getStatus() {
		return super.getStatus() && RotationUtil.currentPriority == priority;
	}
}
