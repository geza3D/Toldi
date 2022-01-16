package me.geza3d.toldi.module;

import java.awt.Color;

import me.geza3d.toldi.gui.panels.hud.HudPanel;
import me.geza3d.toldi.module.settings.Setting.ColorSetting;

public abstract class ToldiHudModule extends ToldiModule {

	protected HudPanel panel;
	
	public ColorSetting color = new ColorSetting(this, "color", Color.CYAN.getRGB());
	
	public ToldiHudModule() {
		this.panel = createPanel();
	}

	protected abstract HudPanel createPanel();

	public HudPanel getPanel() {
		return panel;
	}

}
