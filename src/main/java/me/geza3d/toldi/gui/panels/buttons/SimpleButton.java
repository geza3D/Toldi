package me.geza3d.toldi.gui.panels.buttons;

import java.awt.Color;

import me.geza3d.toldi.Toldi;
import me.geza3d.toldi.gui.panels.ButtonPanel;
import me.geza3d.toldi.gui.panels.MainPanel;
import me.geza3d.toldi.gui.windows.GuiValues;
import net.minecraft.client.util.math.MatrixStack;

public class SimpleButton extends ButtonPanel {

	Runnable run;
	
	public SimpleButton(MainPanel main, int x, int y, int width, int height, String text, Runnable run) {
		super(main, x, y, width, height, text);
		this.run = run;
	}
	
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		int rgb = GuiValues.c3;
		if(main.isMouseOver(mouseX, mouseY) && isMouseOver(mouseX, mouseY)) 
			rgb = GuiValues.c4;
		fill(matrices, x, y, x+width, y+height, rgb);
		drawCenteredText(matrices, Toldi.TEXTRENDERER, text, x + width/2, y + (height - 10) / 2, Color.WHITE.getRGB());
	}
	
	@Override
	protected void onClick(int mouseX, int mouseY) {
		run.run();
	}

}
