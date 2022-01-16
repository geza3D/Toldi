package me.geza3d.toldi.gui.panels.buttons;

import me.geza3d.toldi.Toldi;
import me.geza3d.toldi.gui.panels.ButtonPanel;
import me.geza3d.toldi.gui.panels.MainPanel;
import me.geza3d.toldi.gui.windows.GuiValues;
import me.geza3d.toldi.module.settings.NumberSetting;
import me.geza3d.toldi.module.settings.NumberSetting.DoubleSetting;
import me.geza3d.toldi.module.settings.NumberSetting.IntegerSetting;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.MathHelper;

public class NumberSettingButton extends ButtonPanel {

	NumberSetting<?> setting;
	TextFieldWidget textField;
	boolean selected;
	
	public NumberSettingButton(NumberSetting<?> setting, MainPanel main, int x, int y, int width, int height) {
		super(main, x, y, width, height, setting.getName());
		this.setting = setting;
		textField = new TextFieldWidget(Toldi.TEXTRENDERER, x+width-38, y+height/2-3, 38, 9, new LiteralText(String.valueOf(setting.getValue())));
		textField.setEditable(true);
		textField.setDrawsBackground(false);
		textField.setMaxLength(5);
	}

	@Override
	protected void onRender(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		fill(matrices, x, y, x + width, y + height, GuiValues.c5);
		textField.y = y+height/2-4;
		double max = 1;
		//double min = 1; TODO
		double value = 1;
		int width = this.width-40;
		if(setting instanceof IntegerSetting) {
			max = (double)(int) setting.getMax();
			//min = (double)(int) setting.getMin();
			value = (double)(int) setting.getValue();
		} else {
			max = (double) setting.getMax();
			//min = (double) setting.getMin();
			value = (double) setting.getValue();
		}
		int sliderWidth = (int)(width * MathHelper.clamp(value / max,0,1));
		drawTextWithShadow(matrices, Toldi.TEXTRENDERER, new LiteralText(text), x, y, 0xffffffff);
		textField.render(matrices, mouseX, mouseY, delta);
		fill(matrices, x, y+10, x + width, y + height-1, GuiValues.c6);
		fill(matrices, x, y+10, x + sliderWidth, y + height-1, GuiValues.c7);
		fill(matrices, x + sliderWidth, y+9, x + sliderWidth + 2, y + height, GuiValues.c7);
		if(selected) {
			if(setting instanceof IntegerSetting) {
				((IntegerSetting)setting).setValue(Integer.valueOf((int) (MathHelper.clamp((mouseX-x) / (double) width, 0, 1) * max)));
			} else {
				((DoubleSetting)setting).setValue(Double.valueOf(MathHelper.clamp((mouseX-x) / (double) width, 0, 1) * max));
			}
		}
		if(!textField.isFocused()) {
			if(setting instanceof IntegerSetting) {
				textField.setText(String.valueOf((int)setting.getValue()));
			} else {
				textField.setText(String.valueOf(((int)((double)setting.getValue()*100d))/100d));
			}
		} else if(!textField.getText().isEmpty()){
			if(setting instanceof IntegerSetting) {
				((IntegerSetting)setting).setValue((int) Double.parseDouble(textField.getText()));
			} else {
				((DoubleSetting)setting).setValue(Double.parseDouble(textField.getText()));
			}
		}
	}

	@Override
	protected void onClick(int mouseX, int mouseY) {
		if(mouseY - y > 9) selected = true;
	}
	
	@Override
	public boolean click(int mouseX, int mouseY) {
		textField.mouseClicked(mouseX, mouseY, 0);
		return super.click(mouseX, mouseY);
	}
	
	@Override
	protected void onReleaseMouse(int mouseX, int mouseY) {
		selected = false;
	}
	
	@Override
	protected void onKeyPressed(int keyCode, int scanCode, int modifiers) {
		textField.keyPressed(keyCode, scanCode, modifiers);
	}
	
	@Override
	protected void onKeyReleased(int keyCode, int scanCode, int modifiers) {
		textField.keyReleased(keyCode, scanCode, modifiers);
	}
	
	@Override
	protected void onCharTyped(char chr, int modifiers) {
		if((chr >= '0' && chr <= '9') || chr == '.') {
			textField.charTyped(chr, modifiers);
		}
	}
	
	@Override
	public String getDescription() {
		return setting.getDesc();
	}

}
