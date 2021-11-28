package me.geza3d.toldi.gui.panels.buttons;

import me.geza3d.toldi.Toldi;
import me.geza3d.toldi.gui.panels.ButtonPanel;
import me.geza3d.toldi.gui.panels.MainPanel;
import me.geza3d.toldi.module.settings.Setting.BooleanSetting;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

public class BooleanSettingButton extends ButtonPanel {

	BooleanSetting setting;
	int switchX;
	int switchY;
	int leverX;
	int desiredLeverX;
	
	public BooleanSettingButton(BooleanSetting setting, MainPanel main, int x, int y, int width, int height) {
		super(main, x, y, width, height, setting.getName());
		this.setting = setting;
		this.switchX = x + width - 32;
		this.leverX = switchX + (setting.getValue() ? 15 : 0);
		this.desiredLeverX = leverX;
		this.switchY = y + height / 2 - 5;
	}

	@Override
	protected void onRender(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		switchY = y + height / 2 - 5;
		fill(matrices, x, y, x + width, y + height, 0xff2b0075);
		drawTextWithShadow(matrices, Toldi.TEXTRENDERER, new LiteralText(text), x, y + height / 2 - 4, 0xffffffff);
		fill(matrices, switchX, switchY+2, switchX + 25, switchY + 8, 0xff331070);
		if(leverX < desiredLeverX) {
			leverX++;
		} else if(leverX > desiredLeverX) {
			leverX--;
		}
		fill(matrices, leverX, switchY, leverX + 10, switchY + 10, 0xffb133ff);
	}
	
	@Override
	protected void onClick(int mouseX, int mouseY) {
		setting.setValue(!setting.getValue());
		if(setting.getValue()) {
			desiredLeverX = switchX + 15;
		} else {
			desiredLeverX = switchX;
		}
	}
}
