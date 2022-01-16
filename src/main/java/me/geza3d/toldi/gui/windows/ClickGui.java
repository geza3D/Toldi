package me.geza3d.toldi.gui.windows;

import me.geza3d.toldi.Toldi;
import me.geza3d.toldi.gui.panels.MainPanel;
import me.geza3d.toldi.gui.panels.buttons.CategoryButton;
import me.geza3d.toldi.gui.panels.buttons.SimpleButton;
import me.geza3d.toldi.gui.panels.mainpanels.DescriptionMainPanel;
import me.geza3d.toldi.gui.panels.mainpanels.KeyBindMainPanel;
import me.geza3d.toldi.gui.panels.mainpanels.ModulesMainPanel;
import me.geza3d.toldi.gui.panels.mainpanels.SettingMainPanel;
import me.geza3d.toldi.module.EnumModuleType;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Items;
import net.minecraft.text.LiteralText;

public class ClickGui extends Window {

	public ClickGui() {
		super(new LiteralText("clickgui"));
	}
	
	@Override
	protected void init() {
		panels.clear();
		if(GuiValues.selectedType.ordinal() > 6)
			GuiValues.selectedType = EnumModuleType.RENDER;
		if(GuiValues.selectedModule != null && GuiValues.selectedModule.getType().ordinal() > 6)
			GuiValues.selectedModule = null;
		
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
		
		MainPanel windowPanel = new MainPanel(width + 202, height - 120, 50, 226);
		windowPanel.renderBackground = false;
		windowPanel.addButton(new SimpleButton(windowPanel, width + 204, height - 118, 46, 20, "Hud", new Runnable() {
			@Override
			public void run() {
				Toldi.CLIENT.setScreen(new HudEditor());
			}
		}));
		
		panels.add(windowPanel);
	}
	
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		renderBackground(matrices);
		super.render(matrices, mouseX, mouseY, delta);
	}
}
