package me.geza3d.toldi.gui.windows;

import java.util.ArrayList;
import java.util.List;

import me.geza3d.toldi.gui.panels.MainPanel;
import me.geza3d.toldi.gui.panels.Panel;
import me.geza3d.toldi.gui.panels.buttons.CategoryButton;
import me.geza3d.toldi.gui.panels.mainpanels.DescriptionMainPanel;
import me.geza3d.toldi.gui.panels.mainpanels.KeyBindMainPanel;
import me.geza3d.toldi.gui.panels.mainpanels.ModulesMainPanel;
import me.geza3d.toldi.gui.panels.mainpanels.SettingMainPanel;
import me.geza3d.toldi.module.EnumModuleType;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;

public class ClickGui extends Screen {
	
	List<Panel> panels = new ArrayList<>();
	public static EnumModuleType selectedType = EnumModuleType.RENDER;
	
	public ClickGui() {
		super(Text.of("clickgui"));
	}
	
	@Override
	protected void init() {
		panels.clear();
		int width = this.width / 2;
		int height = this.height / 2;
		
		int categX = width-200;
		int categY = height-120;
		MainPanel categories = new MainPanel(categX, categY, 30, 210);
		categories.renderBackground = false;
		
		categories.addButton(new CategoryButton(EnumModuleType.RENDER, Items.ENDER_EYE, categories, categX, categY));
		categories.addButton(new CategoryButton(EnumModuleType.EXPLOIT, Items.BEDROCK, categories, categX, categY+30));
		categories.addButton(new CategoryButton(EnumModuleType.MOVEMENT, Items.SADDLE, categories, categX, categY+60));
		categories.addButton(new CategoryButton(EnumModuleType.COMBAT, Items.END_CRYSTAL, categories, categX, categY+90));
		categories.addButton(new CategoryButton(EnumModuleType.WORLD, Items.FILLED_MAP, categories, categX, categY+120));
		categories.addButton(new CategoryButton(EnumModuleType.PLAYER, Items.DIAMOND_HELMET, categories, categX, categY+150));
		categories.addButton(new CategoryButton(EnumModuleType.ALL, Items.EGG, categories, categX, categY+180));
		
		panels.add(categories);
		
		ModulesMainPanel modules = new ModulesMainPanel(width-170, height-120, 168, 210);
		
		panels.add(modules);
		
		SettingMainPanel settings = new SettingMainPanel(width + 2, height - 120, 198, 191);
		
		panels.add(settings);
		
		KeyBindMainPanel keybind = new KeyBindMainPanel(width + 2, height + 70, 198, 20);
		
		panels.add(keybind);
		
		DescriptionMainPanel description = new DescriptionMainPanel(width - 170, height + 92, 370, 16);
		
		panels.add(description);
	}
	
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		renderBackground(matrices);
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
