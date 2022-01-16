package me.geza3d.toldi.gui.panels.buttons;

import java.awt.Color;

import me.geza3d.toldi.Toldi;
import me.geza3d.toldi.gui.panels.ButtonPanel;
import me.geza3d.toldi.gui.panels.MainPanel;
import me.geza3d.toldi.gui.windows.GuiValues;
import me.geza3d.toldi.module.ToldiModule;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

public class ModuleButton extends ButtonPanel {

	ToldiModule module;
	
	public ModuleButton(ToldiModule module, MainPanel main, int x, int y, int width, int height) {
		super(main, x, y, width, height, module.getRawName());
		this.module = module;
	}

	@Override
	protected void onClick(int mouseX, int mouseY) {
		module.toggle();
	}
	
	@Override
	protected void onRightClick(int mouseX, int mouseY) {
		GuiValues.selectedModule = module;
	}

	@Override
	protected void onRender(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		int rgb = GuiValues.c3;
		if(main.isMouseOver(mouseX, mouseY) && isMouseOver(mouseX, mouseY) || GuiValues.selectedModule == module) 
			rgb = GuiValues.c4;
		DrawableHelper.fill(matrices, x, y, x+width, y+height, rgb);
		drawCenteredText(matrices, Toldi.TEXTRENDERER, text, x + width/2, y + (height - 10) / 2, module.getRawStatus() ? new Color(0x00ffd9, false).getRGB() : Color.WHITE.getRGB());
	}
	
	public ToldiModule getModule() {
		return module;
	}
	
	@Override
	public String getDescription() {
		return module.getDescription();
	}

}
