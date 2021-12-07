package me.geza3d.toldi.gui.panels.mainpanels;

import me.geza3d.toldi.gui.clickgui.ClickGui;
import me.geza3d.toldi.gui.panels.ButtonPanel;
import me.geza3d.toldi.gui.panels.MainPanel;
import me.geza3d.toldi.gui.panels.buttons.KeyBindButton;
import me.geza3d.toldi.gui.panels.buttons.KeyBindModeButton;
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
		DrawableHelper.fill(matrices, x, unOffsettedY, x+width, unOffsettedY+height, ClickGui.c2);
		if(prevModule != ClickGui.selectedModule) {
			prevModule = ClickGui.selectedModule;
			scrollOffset = 0;
			desiredScrollOffset = 0;
			buttons.clear();
			if(prevModule != null) {
				addButton(new KeyBindButton(prevModule, this, x + 5, y + height - 18, 120, 16));
				addButton(new KeyBindModeButton(prevModule, this, x + 130, y + height - 18, width - 135, 16));
			}
		}
		for(ButtonPanel button : buttons) {
			button.render(matrices, mouseX, mouseY, delta);
		}	
	}
}
