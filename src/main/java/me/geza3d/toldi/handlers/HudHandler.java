package me.geza3d.toldi.handlers;

import me.geza3d.toldi.Toldi;
import me.geza3d.toldi.gui.windows.HudEditor;
import me.geza3d.toldi.init.Modules;
import me.geza3d.toldi.module.ToldiHudModule;
import me.geza3d.toldi.module.ToldiModule;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class HudHandler {

	public static void initHudHandler() {
		HudRenderCallback.EVENT.register((matrices, delta) -> {
			if(!(Toldi.CLIENT.currentScreen instanceof HudEditor)) {
				for(ToldiModule module : Modules.HUD) {
					if(module instanceof ToldiHudModule) {
						((ToldiHudModule) module).getPanel().render(matrices, -999, -999, delta);
						((ToldiHudModule) module).getPanel().resetLocation();
					}
				}
			}
		});
	}

}
