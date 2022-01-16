package me.geza3d.toldi.gui.windows;

import me.geza3d.toldi.gui.panels.Panel;
import me.geza3d.toldi.gui.panels.hud.HudPanel;
import me.geza3d.toldi.gui.panels.mainpanels.DescriptionMainPanel;
import me.geza3d.toldi.gui.panels.mainpanels.KeyBindMainPanel;
import me.geza3d.toldi.gui.panels.mainpanels.ModulesMainPanel;
import me.geza3d.toldi.gui.panels.mainpanels.SettingMainPanel;
import me.geza3d.toldi.init.Modules;
import me.geza3d.toldi.module.EnumModuleType;
import me.geza3d.toldi.module.ToldiHudModule;
import me.geza3d.toldi.module.ToldiModule;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

public class HudEditor extends Window {

	protected HudEditor() {
		super(new LiteralText("hudeditor"));
	}

	@Override
	protected void init() {
		panels.clear();
		GuiValues.movingHudPanel = false;
		GuiValues.selectedType = EnumModuleType.HUD;
		GuiValues.selectedModule = null;
		
		int w = this.width/2;
		int h = this.height/2;
		
		for(ToldiModule module : Modules.HUD) {
			if(module instanceof ToldiHudModule) {
				((ToldiHudModule) module).getPanel().resetLocation();
				((ToldiHudModule) module).getPanel().drawBackground = true;
				panels.add(((ToldiHudModule) module).getPanel());
			}
		}
		
		ModulesMainPanel modules = new ModulesMainPanel(w-170, h-120, 168, 210);
		
		panels.add(modules);
		
		SettingMainPanel settings = new SettingMainPanel(w + 2, h - 120, 198, 191);
		
		panels.add(settings);
		
		KeyBindMainPanel keybind = new KeyBindMainPanel(w + 2, h + 70, 198, 20);
		
		panels.add(keybind);
		
		DescriptionMainPanel description = new DescriptionMainPanel(w - 170, h + 92, 370, 16);
		
		panels.add(description);
	}
	
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		for(Panel panel : panels) {
			if(panel instanceof HudPanel || !GuiValues.movingHudPanel) panel.render(matrices, mouseX, mouseY, delta);
		}
		GuiValues.hoveredPanel = null;
	}
	
	@Override
	public void onClose() {
		for(ToldiModule module : Modules.HUD) {
			((ToldiHudModule) module).getPanel().drawBackground = false;
		}
		super.onClose();
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		for(Panel panel : panels) {
			if(button == 0) {
				if(panel.click((int) mouseX, (int) mouseY)) break;
			} else if (button == 1) {
				panel.rightClick((int) mouseX, (int) mouseY);
			}
		}
		return false;
	}
}
