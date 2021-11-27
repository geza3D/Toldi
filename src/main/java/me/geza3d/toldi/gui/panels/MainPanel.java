package me.geza3d.toldi.gui.panels;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import me.geza3d.toldi.util.GuiUtil;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class MainPanel extends Panel {
	
	boolean scrollable;
	public boolean renderBackground = true;
	protected int scrollOffset = 0;
	protected int desiredScrollOffset = 0;
	
	protected List<ButtonPanel> buttons = new ArrayList<>();
	
	public MainPanel(int x, int y, int width, int height, boolean scrollable) {
		super(x, y, width, height);
		this.scrollable = scrollable;
	}
	
	public MainPanel(int x, int y, int width, int height) {
		super(x, y, width, height);
		scrollable = false;
	}

	@Override
	protected void onRender(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		if(renderBackground) DrawableHelper.fill(matrices, x, unOffsettedY, x+width, unOffsettedY+height, new Color(0x360094, false).getRGB());
		for(ButtonPanel button : buttons) {
			button.render(matrices, mouseX, mouseY, delta);
		}
	}
	
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		if(scrollOffset-desiredScrollOffset < -1) {
			scrollOffset += 2;
		} else if(scrollOffset-desiredScrollOffset > 1) {
			scrollOffset -= 2;
		}
		GL11.glEnable(GL11.GL_SCISSOR_TEST);
		GuiUtil.glScissors(x, unOffsettedY, width, height);
		super.render(matrices, mouseX, mouseY, delta);
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
	}
	
	public void handleScroll(int mouseX, int mouseY, int amount) {
		if(scrollable) {
			if(isMouseOver(mouseX, mouseY)) {
				desiredScrollOffset += amount*5;
				desiredScrollOffset = MathHelper.clamp(desiredScrollOffset, Integer.MIN_VALUE, 0);
			}
		}
	}
	
	@Override
	protected void onClick(int mouseX, int mouseY) {
		for(ButtonPanel button : buttons) {
			button.click(mouseX, mouseY);
		}
	}
	
	@Override
	protected void onRightClick(int mouseX, int mouseY) {
		for(ButtonPanel button : buttons) {
			button.rightClick(mouseX, mouseY);
		}
	}
	
	@Override
	protected void onReleaseMouse(int mouseX, int mouseY) {
		for(ButtonPanel button : buttons) {
			button.releaseMouse(mouseX, mouseY);
		}
	}
	
	public void addButton(ButtonPanel button) {
		buttons.add(button);
	}
	
	@Override
	protected void onCharTyped(char chr, int modifiers) {
		for(ButtonPanel button : buttons) {
			button.charTyped(chr, modifiers);
		}
	}
	
	@Override
	protected void onKeyPressed(int keyCode, int scanCode, int modifiers) {
		for(ButtonPanel button : buttons) {
			button.keyPressed(keyCode, scanCode, modifiers);
		}
	}
	
	@Override
	protected void onKeyReleased(int keyCode, int scanCode, int modifiers) {
		for(ButtonPanel button : buttons) {
			button.keyReleased(keyCode, scanCode, modifiers);
		}
	}

}
