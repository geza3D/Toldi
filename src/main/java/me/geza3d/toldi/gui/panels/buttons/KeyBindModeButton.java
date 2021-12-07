package me.geza3d.toldi.gui.panels.buttons;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

import me.geza3d.toldi.Toldi;
import me.geza3d.toldi.gui.clickgui.ClickGui;
import me.geza3d.toldi.gui.panels.ButtonPanel;
import me.geza3d.toldi.gui.panels.MainPanel;
import me.geza3d.toldi.module.ToldiModule;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;

public class KeyBindModeButton extends ButtonPanel {

	ToldiModule module;
	
	public KeyBindModeButton(ToldiModule module, MainPanel main, int x, int y, int width, int height) {
		super(main, x, y, width, height, "");
		this.module = module;
	}
	
	@Override
	protected void onRender(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		fill(matrices, x, unOffsettedY, x+width, unOffsettedY+height, ClickGui.c1);
		String text = "";
		System.out.println(module.keybindSetting.getMode());
		if(module.keybindSetting.getMode() == 0) {
			text = "press";
		} else {
			text = "hold";
		}
		try {
			text = new TranslatableText("setting.keybind.mode." + text).parse(null, null, 0).getString();
		} catch (CommandSyntaxException e) {
			e.printStackTrace();
		}
		fill(matrices, x, y, x+width, y+height, ClickGui.c1);
		fill(matrices, x + width/2 - 5 - Toldi.TEXTRENDERER.getWidth(text)/2 - 3, y + height / 2 - 5, x + width/2 - 5 + Toldi.TEXTRENDERER.getWidth(text) / 2 + 3, y + height / 2 + 6, ClickGui.c6);
		drawCenteredText(matrices, Toldi.TEXTRENDERER, text, x + width / 2 - 5, y + height / 2 - 4, ClickGui.c7);
	}
	
	@Override
	protected void onClick(int mouseX, int mouseY) {
		module.keybindSetting.incrementMode();
	}

	@Override
	protected void onRightClick(int mouseX, int mouseY) {
		module.keybindSetting.incrementMode();
	}
}
