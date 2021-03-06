package me.geza3d.toldi.gui.panels.mainpanels;

import java.awt.Color;
import java.util.List;

import me.geza3d.toldi.Toldi;
import me.geza3d.toldi.gui.panels.MainPanel;
import me.geza3d.toldi.gui.panels.Panel;
import me.geza3d.toldi.gui.panels.buttons.ModuleButton;
import me.geza3d.toldi.gui.windows.GuiValues;
import me.geza3d.toldi.init.Modules;
import me.geza3d.toldi.module.EnumModuleType;
import me.geza3d.toldi.module.ToldiModule;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

public class ModulesMainPanel extends MainPanel {

	EnumModuleType prevType;
	
	public ModulesMainPanel(int x, int y, int width, int height) {
		super(x, y, width, height, true);
	}
	
	@Override
	protected void onRender(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		DrawableHelper.fill(matrices, x, unOffsettedY, x+width, unOffsettedY+height, GuiValues.c1);
		if(prevType != GuiValues.selectedType) {
			prevType = GuiValues.selectedType;
			scrollOffset = 0;
			desiredScrollOffset = 0;
			buttons.clear();
			List<ToldiModule> modules = Modules.MODULESBYTYPE.get(GuiValues.selectedType);
			for(int i = 0; i < modules.size(); i++) {
				addButton(new ModuleButton(modules.get(i), this, x+2, y+16+16*i, width-4, 14));
			}
		}
		for(Panel button : buttons) {
			button.render(matrices, mouseX, mouseY, delta);
		}
		fill(matrices, x, y, x+width, y+14, GuiValues.c2);
		drawCenteredText(matrices, Toldi.TEXTRENDERER, GuiValues.selectedType.getName(), x + width/2, unOffsettedY+2, Color.WHITE.getRGB());
	}

	@Override
	public boolean isMouseOver(int mouseX, int mouseY) {
		return x <= mouseX && x + width > mouseX && y + 14 <= mouseY && y + height > mouseY;
	}
}
