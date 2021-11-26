package me.geza3d.toldi.gui.panels;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

public class MainPanel extends Panel {
	
	boolean scrollable;
	int scrollOffset;
	
	List<ButtonPanel> buttons = new ArrayList<>();
	
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
		DrawableHelper.fill(matrices, x, y, x+width, y+height, new Color(0x360094, false).getRGB());
		for(ButtonPanel button : buttons) {
			button.render(matrices, mouseX, mouseY, delta);
		}
	}
	
	public void handleScroll(int mouseX, int mouseY, int amount) {
		if(scrollable) {
			if(isMouseOver(mouseX, mouseY)) {
				scrollOffset += amount;
			}
		}
	}
	
	@Override
	protected void onClick(int mouseX, int mouseY) {
		for(ButtonPanel button : buttons) {
			button.click(mouseX, mouseY);
		}
	}
	
	public void addButton(ButtonPanel button) {
		buttons.add(button);
	}

}
