package me.geza3d.toldi.gui.panels;

import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

public abstract class Panel extends DrawableHelper implements Drawable{

	protected int x;
	protected int y;
	protected int offsettedY;
	protected int width;
	protected int height;
	public boolean visible = true;
	
	public Panel(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.offsettedY = y;
		this.width = width;
		this.height = height;
	}
	
	protected abstract void onRender(MatrixStack matrices, int mouseX, int mouseY, float delta);
	
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		if(visible) {
			onRender(matrices, mouseX, mouseY, delta);
		}
	}
	
	protected abstract void onClick(int mouseX, int mouseY);
	
	public void click(int mouseX, int mouseY) {
		if(isMouseOver(mouseX, mouseY)) {
			onClick(mouseX, mouseY);
		}
	}
	
	protected boolean isMouseOver(int mouseX, int mouseY) {
		return x <= mouseX && x + width > mouseX && offsettedY <= mouseY && offsettedY + height > mouseY;
	}
}
