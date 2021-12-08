package me.geza3d.toldi.module.modules.render;

import me.geza3d.toldi.events.BrightnessCallback;
import me.geza3d.toldi.module.EnumModuleType;
import me.geza3d.toldi.module.ToldiModule;
import me.geza3d.toldi.module.ToldiModule.Type;
import net.minecraft.util.ActionResult;

@Type(EnumModuleType.RENDER)
public class Fullbright extends ToldiModule {

	@Listener
	public void onGamma() {
		BrightnessCallback.EVENT.register(()->{
			if(getStatus()) {
				return ActionResult.FAIL;
			}
			return ActionResult.SUCCESS;
		});
	}
	
}
