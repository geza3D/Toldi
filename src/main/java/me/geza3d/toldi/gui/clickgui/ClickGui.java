package me.geza3d.toldi.gui.clickgui;

import java.util.ArrayList;
import java.util.List;

import me.geza3d.toldi.Toldi;
import me.geza3d.toldi.gui.panels.MainPanel;
import me.geza3d.toldi.gui.panels.Panel;
import me.geza3d.toldi.gui.panels.buttons.ModuleButton;
import me.geza3d.toldi.init.Modules;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class ClickGui extends Screen {
	
	List<Panel> panels = new ArrayList<>();
	
	public ClickGui() {
		super(Text.of("clickgui"));
	}
	
	@Override
	protected void init() {
		panels.clear();
		MainPanel modules = new MainPanel(Toldi.CLIENT.getWindow().getScaledWidth()/2-100, Toldi.CLIENT.getWindow().getScaledHeight()/2-100, 200, 200, true);
		modules.addButton(new ModuleButton(Modules.DEBUG, modules, Toldi.CLIENT.getWindow().getScaledWidth()/2-50, Toldi.CLIENT.getWindow().getScaledHeight()/2-5, 100, 10));
		panels.add(modules);
	}
	
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		for(Panel panel : panels) {
			panel.render(matrices, mouseX, mouseY, delta);
		}
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		for(Panel panel : panels) {
			panel.click((int) mouseX, (int) mouseY);
		}
		return false;
	}
	
	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
		for(Panel panel : panels) {
			if(panel instanceof MainPanel) {
				((MainPanel) panel).handleScroll((int) mouseX, (int) mouseY, (int) amount);
			}
		}
		return false;
	}
}
