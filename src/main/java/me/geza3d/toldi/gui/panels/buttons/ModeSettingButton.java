package me.geza3d.toldi.gui.panels.buttons;

import me.geza3d.toldi.Toldi;
import me.geza3d.toldi.gui.panels.ButtonPanel;
import me.geza3d.toldi.gui.panels.MainPanel;
import me.geza3d.toldi.gui.windows.GuiValues;
import me.geza3d.toldi.module.settings.Setting.ModeSetting;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

public class ModeSettingButton extends ButtonPanel {

	ModeSetting setting;
	
	public ModeSettingButton(ModeSetting setting, MainPanel main, int x, int y, int width, int height) {
		super(main, x, y, width, height, setting.getMode());
		this.setting = setting;
	}
	
	@Override
	protected void onRender(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		fill(matrices, x, y, x + width, y + height, GuiValues.c5);
		fill(matrices, x + width - Toldi.TEXTRENDERER.getWidth("<" + text + ">")-5, y + height / 2 - 5, x + width-5, y + height / 2 + 6, GuiValues.c6);
		drawTextWithShadow(matrices, Toldi.TEXTRENDERER, new LiteralText(setting.getName()), x, y + height / 2 - 4, 0xffffffff);
		drawTextWithShadow(matrices, Toldi.TEXTRENDERER, new LiteralText("<" + text + ">"), x + width - Toldi.TEXTRENDERER.getWidth("<" + text + ">") - 5, y + height / 2 - 4, 0xffffffff);
	}
	
	@Override
	protected void onClick(int mouseX, int mouseY) {
		setting.increment();
		text = setting.getMode();
	}
	
	@Override
	protected void onRightClick(int mouseX, int mouseY) {
		setting.decrement();
		text = setting.getMode();
	}
	
	@Override
	public String getDescription() {
		return setting.getDesc();
	}

}
