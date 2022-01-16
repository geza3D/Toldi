package me.geza3d.toldi.gui.panels.mainpanels;

import me.geza3d.toldi.gui.panels.MainPanel;
import me.geza3d.toldi.gui.panels.Panel;
import me.geza3d.toldi.gui.panels.buttons.KeyBindButton;
import me.geza3d.toldi.gui.panels.buttons.KeyBindModeButton;
import me.geza3d.toldi.gui.windows.GuiValues;
import me.geza3d.toldi.module.ToldiModule;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

public class KeyBindMainPanel extends MainPanel {

	ToldiModule prevModule;
	
	public KeyBindMainPanel(int x, int y, int width, int height) {
		super(x, y, width, height, false);
	}
	
	@Override
	protected void onRender(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		DrawableHelper.fill(matrices, x, unOffsettedY, x+width, unOffsettedY+height, GuiValues.c2);
		if(prevModule != GuiValues.selectedModule) {
			prevModule = GuiValues.selectedModule;
			scrollOffset = 0;
			desiredScrollOffset = 0;
			buttons.clear();
			if(prevModule != null) {
				addButton(new KeyBindButton(prevModule, this, x + 5, y + height - 18, 120, 16));
				addButton(new KeyBindModeButton(prevModule, this, x + 130, y + height - 18, width - 135, 16));
			}
		}
		for(Panel button : buttons) {
			button.render(matrices, mouseX, mouseY, delta);
		}	
	}
}
