package me.geza3d.toldi.gui.clickgui;

import java.util.ArrayList;
import java.util.List;

import me.geza3d.toldi.gui.panels.MainPanel;
import me.geza3d.toldi.gui.panels.Panel;
import me.geza3d.toldi.gui.panels.buttons.CategoryButton;
import me.geza3d.toldi.gui.panels.mainpanels.ModulesMainPanel;
import me.geza3d.toldi.gui.panels.mainpanels.SettingMainPanel;
import me.geza3d.toldi.module.EnumModuleType;
import me.geza3d.toldi.module.ToldiModule;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;

public class ClickGui extends Screen {
	
	List<Panel> panels = new ArrayList<>();
	public static EnumModuleType selectedType = EnumModuleType.RENDER;
	public static ToldiModule selectedModule = null;
	public static int c1 = 0xff384266; //Main
	public static int c2 = 0xff4a5785; //Secondary
	public static int c3 = 0xff2c3659; //Button
	public static int c4 = 0xff6882de; //Button Hovered
	public static int c5 = 0xff45507a; //Setting Main
	public static int c6 = 0xff272d45; //Setting Secondary
	public static int c7 = 0xff33a0ff; //Setting elements
	
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
		
		SettingMainPanel settings = new SettingMainPanel(width + 2, height - 120, 198, 210);
		
		panels.add(settings);
	}
	
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		renderBackground(matrices);
		for(Panel panel : panels) {
			panel.render(matrices, mouseX, mouseY, delta);
		}
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
