package me.geza3d.toldi.gui.panels.buttons;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

import me.geza3d.toldi.Toldi;
import me.geza3d.toldi.gui.panels.ButtonPanel;
import me.geza3d.toldi.gui.panels.MainPanel;
import me.geza3d.toldi.gui.windows.GuiValues;
import me.geza3d.toldi.module.ToldiModule;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;

public class KeyBindButton extends ButtonPanel {

	ToldiModule module;
	boolean clicked;
	
	public KeyBindButton(ToldiModule module, MainPanel main, int x, int y, int width, int height) {
		super(main, x, y, width, height, getKeyName(module.keybindSetting.getValue()));
		this.module = module;
	}

	@Override
	protected void onRender(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		fill(matrices, x, unOffsettedY, x+width, unOffsettedY+height, GuiValues.c1);
		String text = "";
		try {
			text = new TranslatableText("setting.keybind.key").parse(null, null, 0).getString() + ": " + this.text;
		} catch (CommandSyntaxException e) {
			e.printStackTrace();
		}
		drawCenteredText(matrices, Toldi.TEXTRENDERER, text, x + width / 2, unOffsettedY + height / 2 - 4, GuiValues.c7);
	}
	
	@Override
	protected void onClick(int mouseX, int mouseY) {
		clicked = true;
		text = "...";
	}
	
	@Override
	protected void onRightClick(int mouseX, int mouseY) {
		module.keybindSetting.setValue(-1);
		text = getKeyName(module.keybindSetting.getValue());
	}
	
	@Override
	protected void onKeyPressed(int keyCode, int scanCode, int modifiers) {
		if(clicked) {
			if(keyCode == InputUtil.GLFW_KEY_ESCAPE) {
				module.keybindSetting.setValue(-1);
				text = getKeyName(module.keybindSetting.getValue());
			} else {
				module.keybindSetting.setValue(keyCode);
				text = getKeyName(module.keybindSetting.getValue());
			}
			clicked = false;
		}
	}
	
	private static String getKeyName(int key) {
		if(key == -1) {
			return "";
		}
		return InputUtil.fromKeyCode(key, 0).getLocalizedText().getString();
	}
}
