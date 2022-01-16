package me.geza3d.toldi.gui.panels;

import me.geza3d.toldi.gui.clickgui.ClickGui;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

public class Panel extends DrawableHelper implements Drawable{

	protected int x;
	protected int unOffsettedY;
	protected int y;
	protected int width;
	protected int height;
	public boolean visible = true;
	
	public Panel(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.unOffsettedY = y;
		this.width = width;
		this.height = height;
	}
	
	protected void onRender(MatrixStack matrices, int mouseX, int mouseY, float delta) {
	}
	
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		if(visible) {
			onRender(matrices, mouseX, mouseY, delta);
			if(getDescription() != null && isMouseOver(mouseX, mouseY)) {
				ClickGui.hoveredPanel = this;
			}
		}
	}
	
	public String getDescription() {
		return null;
	}
	
	protected void onClick(int mouseX, int mouseY) {	
	}
	
	public void click(int mouseX, int mouseY) {
		if(isMouseOver(mouseX, mouseY) && visible) {
			onClick(mouseX, mouseY);
		}
	}
	
	protected void onRightClick(int mouseX, int mouseY) {	
	}
	
	public void rightClick(int mouseX, int mouseY) {
		if(isMouseOver(mouseX, mouseY) && visible) {
			onRightClick(mouseX, mouseY);
		}
	}
	
	protected void onReleaseMouse(int mouseX, int mouseY) {	
	}
	
	public void releaseMouse(int mouseX, int mouseY) {
		onReleaseMouse(mouseX, mouseY);
	}
	
	protected void onKeyPressed(int keyCode, int scanCode, int modifiers) {
		
	}
	
	public void keyPressed(int keyCode, int scanCode, int modifiers) {
		onKeyPressed(keyCode, scanCode, modifiers);
	}
	
	protected void onKeyReleased(int keyCode, int scanCode, int modifiers) {
		
	}
	
	public void keyReleased(int keyCode, int scanCode, int modifiers) {
		onKeyReleased(keyCode, scanCode, modifiers);
	}
	
	protected void onCharTyped(char chr, int modifiers) {
		
	}
	
	public void charTyped(char chr, int modifiers) {
		onCharTyped(chr, modifiers);
	}
	
	public boolean isMouseOver(int mouseX, int mouseY) {
		return x <= mouseX && x + width > mouseX && y <= mouseY && y + height > mouseY;
	}
}
