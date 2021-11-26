package me.geza3d.toldi.gui.panels;

import net.minecraft.client.util.math.MatrixStack;

public abstract class ButtonPanel extends Panel {

	MainPanel main;
	protected String text;
	
	public ButtonPanel(MainPanel main, int x, int y, int width, int height, String text) {
		super(x,y,width,height);
		this.main = main;
		this.text = text;
	}
	
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		offsettedY = y + main.scrollOffset;
		super.render(matrices, mouseX, mouseY, delta);
	}
}
