package me.geza3d.toldi.gui.panels.buttons;

import java.awt.Color;

import me.geza3d.toldi.Toldi;
import me.geza3d.toldi.gui.panels.ButtonPanel;
import me.geza3d.toldi.gui.panels.MainPanel;
import me.geza3d.toldi.module.Module;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

public class ModuleButton extends ButtonPanel {

	Module module;
	
	public ModuleButton(Module module, MainPanel main, int x, int y, int width, int height) {
		super(main, x, y, width, height, module.getRawName());
		this.module = module;
	}

	@Override
	protected void onClick(int mouseX, int mouseY) {
		module.toggle();
	}

	@Override
	protected void onRender(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		DrawableHelper.fill(matrices, x, offsettedY, x+width, offsettedY+height, new Color(0x5700ed, false).getRGB());
		drawCenteredText(matrices, Toldi.TEXTRENDERER, text, x + width/2, offsettedY + (width - 8) / 2, Color.WHITE.getRGB());
	}

}
