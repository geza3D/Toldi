package me.geza3d.toldi.module.modules.hud;

import me.geza3d.toldi.gui.panels.hud.ActiveModulesHudPanel;
import me.geza3d.toldi.gui.panels.hud.HudPanel;
import me.geza3d.toldi.module.EnumModuleType;
import me.geza3d.toldi.module.ToldiHudModule;

@ToldiHudModule.Type(EnumModuleType.HUD)
public class ActiveModules extends ToldiHudModule{

	@Override
	protected HudPanel createPanel() {
		return new ActiveModulesHudPanel(this, 0f, 0f, 100, 200);
	}


}
