package me.geza3d.toldi.gui.windows;

import java.util.ArrayList;
import java.util.List;

import me.geza3d.toldi.gui.panels.MainPanel;
import me.geza3d.toldi.gui.panels.Panel;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class Window extends Screen{

	protected Window(Text title) {
		super(title);
	}

	List<Panel> panels = new ArrayList<>();
	
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		for(Panel panel : panels) {
			panel.render(matrices, mouseX, mouseY, delta);
		}
		GuiValues.hoveredPanel = null;
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		for(Panel panel : panels) {
			if(button == 0) {
				panel.click((int) mouseX, (int) mouseY);
			} else if (button == 1) {
				panel.rightClick((int) mouseX, (int) mouseY);
			}
		}
		return false;
	}
	
	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		for(Panel panel : panels) {
			panel.releaseMouse((int) mouseX, (int) mouseY);
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
	
	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		for(Panel panel : panels) {
			panel.keyPressed(keyCode, scanCode, modifiers);
		}
		return super.keyPressed(keyCode, scanCode, modifiers);
	}
	
	@Override
	public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
		for(Panel panel : panels) {
			panel.keyReleased(keyCode, scanCode, modifiers);
		}
		return false;
	}
	
	@Override
	public boolean charTyped(char chr, int modifiers) {
		for(Panel panel : panels) {
			panel.charTyped(chr, modifiers);
		}
		return false;
	}
	
	

}
