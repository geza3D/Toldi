package me.geza3d.toldi.gui.panels.mainpanels;

import me.geza3d.toldi.Toldi;
import me.geza3d.toldi.gui.panels.MainPanel;
import me.geza3d.toldi.gui.windows.GuiValues;
import net.minecraft.client.util.math.MatrixStack;

public class DescriptionMainPanel extends MainPanel {

	public DescriptionMainPanel(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	protected void onRender(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		fill(matrices, x, y, x+width, y+height, GuiValues.c2);
		if(GuiValues.hoveredPanel != null) {
			drawCenteredText(matrices, Toldi.TEXTRENDERER, GuiValues.hoveredPanel.getDescription(), x + width/2, y + height / 2 - 5, 0xFFFFFFFF);
		}	
	}
	

}
