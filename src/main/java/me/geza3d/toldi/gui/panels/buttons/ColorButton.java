package me.geza3d.toldi.gui.panels.buttons;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import me.geza3d.toldi.Toldi;
import me.geza3d.toldi.gui.panels.ButtonPanel;
import me.geza3d.toldi.gui.panels.MainPanel;
import me.geza3d.toldi.gui.windows.GuiValues;
import me.geza3d.toldi.module.settings.Setting.ColorSetting;
import me.geza3d.toldi.util.RenderUtil;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.MathHelper;

public class ColorButton extends ButtonPanel {

	ColorSetting setting;
	
	public ColorButton(ColorSetting setting, MainPanel main, int x, int y, int width, int height) {
		super(main, x, y, width, height, setting.getName());
		this.setting = setting;
	}
	
	protected void setColorFromInput() {
		
	}
	
	public static class ColorSettingButton extends ColorButton {

		protected List<ButtonPanel> buttons = new ArrayList<>();
		public boolean forceSphere = true;
		
		public ColorSettingButton(ColorSetting setting, MainPanel main, int x, int y, int width, int height) {
			super(setting, main, x, y, width, height);
			buttons.add(new HueSphereButton(this, setting, main, x, y+14, height-14, height-14));
			buttons.add(new PreviewButton(this, setting, main, x + height - 10, y + 14, width - height + 8, (height-14)/3));
			buttons.add(new BrightnessSliderButton(setting, main, x + height - 10, y + 14 + (height-14)/3, width - height + 8, (height-14)/3));
			buttons.add(new AlphaSliderButton(setting, main, x + height - 10, y + 14 + 2 * (height-14)/3, width - height + 8, (height-14)/3));
		}
		
		@Override
		protected void onRender(MatrixStack matrices, int mouseX, int mouseY, float delta) {
			fill(matrices, x, y, x+width, y+height, GuiValues.c5);
			drawStringWithShadow(matrices, Toldi.TEXTRENDERER, setting.getName(), x, y+3, 0xFFFFFFFF);
			for(ButtonPanel button : buttons) {
				button.render(matrices, mouseX, mouseY, delta);
			}
			super.onRender(matrices, mouseX, mouseY, delta);
		}
		
		@Override
		protected void onClick(int mouseX, int mouseY) {
			for(ButtonPanel button : buttons) {
				button.click(mouseX, mouseY);
			}
			super.onClick(mouseX, mouseY);
		}
		
		@Override
		protected void onReleaseMouse(int mouseX, int mouseY) {
			for(ButtonPanel button : buttons) {
				button.releaseMouse(mouseX, mouseY);
			}
			super.onReleaseMouse(mouseX, mouseY);
		}
		
		@Override
		protected void onKeyPressed(int keyCode, int scanCode, int modifiers) {
			for(ButtonPanel button : buttons) {
				button.keyPressed(keyCode, scanCode, modifiers);
			}
			super.onKeyPressed(keyCode, scanCode, modifiers);
		}
		
		@Override
		protected void onKeyReleased(int keyCode, int scanCode, int modifiers) {
			for(ButtonPanel button : buttons) {
				button.keyReleased(keyCode, scanCode, modifiers);
			}
			super.onKeyReleased(keyCode, scanCode, modifiers);
		}
		
		@Override
		protected void onCharTyped(char chr, int modifiers) {
			for(ButtonPanel button : buttons) {
				button.charTyped(chr, modifiers);
			}
			super.onCharTyped(chr, modifiers);
		}
		
	}

	private class HueSphereButton extends ColorButton {
		
		public HueSphereButton(ColorSettingButton button, ColorSetting setting, MainPanel main, int x, int y, int width, int height) {
			super(setting, main, x, y, width, height);
			this.button = button;
			float[] hsb = {0,0,0};
			hsb = Color.RGBtoHSB(setting.getRed(), setting.getGreen(), setting.getBlue(), hsb);
			pointX = (int) (radius - Math.cos(Math.toRadians(hsb[0]*360f)) * hsb[1] * radius);
			pointY = (int) (radius - Math.sin(Math.toRadians(hsb[0]*360f)) * hsb[1] * radius);
		}
		
		ColorSettingButton button;
		
		boolean clicked = false;
		
		int pointX = 0;
		int pointY = 0;
		int radius = width / 2;
		
		@Override
		protected void onRender(MatrixStack matrices, int mouseX, int mouseY, float delta) {
			RenderUtil.drawColorCircle(matrices, radius, x, y);
			float[] hsb = {0,0,0};
			hsb = Color.RGBtoHSB(setting.getRed(), setting.getGreen(), setting.getBlue(), hsb);
			if(clicked) {
				float difX = mouseX - x - radius;
				float difY = mouseY - y - radius;
				double l = Math.sqrt(difX*difX + difY*difY);
				if(l > radius) {
					difX /= l;
					difY /= l;
					pointX = (int) (radius + difX * radius);
					pointY = (int) (radius + difY * radius);
				} else {
					pointX = mouseX - x;
					pointY = mouseY - y;
				}
			} else if(!button.forceSphere){
				pointX = (int) (radius - Math.cos(Math.toRadians(hsb[0]*360f)) * hsb[1] * radius);
				pointY = (int) (radius - Math.sin(Math.toRadians(hsb[0]*360f)) * hsb[1] * radius);
			}
			if(button.forceSphere) {
				setColorFromInput();
			}
			drawHorizontalLine(matrices, x+pointX-2, x+pointX+2, y+pointY, 0xFF000000);
			drawVerticalLine(matrices, x+pointX, y+pointY-3, y+pointY+3, 0xFF000000);
			super.onRender(matrices, mouseX, mouseY, delta);
		}
		
		@Override
		protected void onClick(int mouseX, int mouseY) {
			if(!clicked) {
				clicked = true;
			}
			super.onClick(mouseX, mouseY);
		}
		
		@Override
		protected void onReleaseMouse(int mouseX, int mouseY) {
			if(clicked) {
				clicked = false;
				setColorFromInput();
			}
			super.onReleaseMouse(mouseX, mouseY);
		}
		
		@Override
		protected void setColorFromInput() {
			float cX = pointX - radius;
			float cY = pointY - radius;
			double l = Math.sqrt(cX*cX + cY*cY);
			cX /= l;
			cY /= l;
			float[] hsb = {0, 0, 0};
			hsb = Color.RGBtoHSB(setting.getRed(), setting.getGreen(), setting.getBlue(), hsb);
			double twopi = (2 * Math.PI);
			hsb[0] = (float) ((Math.atan2(cY, cX) + Math.PI) / twopi);
			hsb[1] = MathHelper.clamp((float) l / (float) radius, 0, 1);
			Color color = new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
			setting.setRed(color.getRed());
			setting.setBlue(color.getBlue());
			setting.setGreen(color.getGreen());
		}
	}
	
	private class BrightnessSliderButton extends ColorButton {

		public BrightnessSliderButton(ColorSetting setting, MainPanel main, int x, int y, int width, int height) {
			super(setting, main, x, y, width, height);
		}
		
		int sliderX = 0;
		boolean clicked = false;
		
		@Override
		protected void onRender(MatrixStack matrices, int mouseX, int mouseY, float delta) {
			float[] hsb = {0,0,0};
			hsb = Color.RGBtoHSB(setting.getRed(), setting.getGreen(), setting.getBlue(), hsb);
			RenderUtil.fillHGradient(matrices, x, y+2, width, height-4, 0xFF000000, Color.HSBtoRGB(hsb[0], hsb[1], 1f));
			if(clicked) {
				sliderX = MathHelper.clamp(mouseX-x, 0, width);
				setColorFromInput();
			} else {
				sliderX = (int) (width * hsb[2]);
			}
			fill(matrices, x+sliderX, y, x+sliderX+2, y+height, GuiValues.c7);
			super.onRender(matrices, mouseX, mouseY, delta);
		}
		
		@Override
		protected void onClick(int mouseX, int mouseY) {
			if(!clicked) {
				clicked = true;
			}
			super.onClick(mouseX, mouseY);
		}
		
		@Override
		protected void onReleaseMouse(int mouseX, int mouseY) {
			if(clicked) {
				clicked = false;
				setColorFromInput();
			}
			super.onReleaseMouse(mouseX, mouseY);
		}
		
		@Override
		protected void setColorFromInput() {
			float[] hsb = {0, 0, 0};
			hsb = Color.RGBtoHSB(setting.getRed(), setting.getGreen(), setting.getBlue(), hsb);
			Color color = new Color(Color.HSBtoRGB(hsb[0], hsb[1], (float) sliderX / (float) width));
			setting.setRed(color.getRed());
			setting.setBlue(color.getBlue());
			setting.setGreen(color.getGreen());
		}
	}
	
	private class AlphaSliderButton extends ColorButton {

		public AlphaSliderButton(ColorSetting setting, MainPanel main, int x, int y, int width, int height) {
			super(setting, main, x, y, width, height);
		}
		
		int sliderX = 0;
		boolean clicked = false;
		
		@Override
		protected void onRender(MatrixStack matrices, int mouseX, int mouseY, float delta) {
			RenderUtil.drawRowBasedCheckerBoard(matrices, 5, x, y+2, width, height-4, 0xFFFFFFFF, 0xFFBFBFBF);
			RenderUtil.fillHGradient(matrices, x, y+2, width, height-4, 
					new Color(setting.getRed(), setting.getGreen(), setting.getBlue(), 0).getRGB(), 
					new Color(setting.getRed(), setting.getGreen(), setting.getBlue(), 255).getRGB());
			if(clicked) {
				sliderX = MathHelper.clamp(mouseX-x, 0, width);
				setColorFromInput();
			} else {
				sliderX = (int) (width * setting.getAlpha() / 255f);
			}
			fill(matrices, x+sliderX, y, x+sliderX+2, y+height, GuiValues.c7);
			super.onRender(matrices, mouseX, mouseY, delta);
		}
		
		@Override
		protected void onClick(int mouseX, int mouseY) {
			if(!clicked) {
				clicked = true;
			}
			super.onClick(mouseX, mouseY);
		}
		
		@Override
		protected void onReleaseMouse(int mouseX, int mouseY) {
			if(clicked) {
				clicked = false;
				setColorFromInput();
			}
			super.onReleaseMouse(mouseX, mouseY);
		}
		
		@Override
		protected void setColorFromInput() {
			setting.setAlpha((int)(sliderX / (float) width * 255));
		}
	}
	
	private class PreviewButton extends ColorButton {
		
		public PreviewButton(ColorSettingButton button, ColorSetting setting, MainPanel main, int x, int y, int width, int height) {
			super(setting, main, x, y, width, height);
			this.button = button;
			textField = new TextFieldWidget(Toldi.TEXTRENDERER, x + 2, y + height / 2 - 5, width, 9, new LiteralText(getHexColor()));
			textField.setEditable(true);
			textField.setDrawsBackground(false);
		}
		
		ColorSettingButton button;
		TextFieldWidget textField;
		
		@Override
		protected void onRender(MatrixStack matrices, int mouseX, int mouseY, float delta) {
			fill(matrices, x, y+2, x + width, y + height - 2, setting.getValue());
			textField.y = y + height / 2 - 5;
			textField.render(matrices, mouseX, mouseY, delta);
			if(textField.isFocused()) {
				setting.setValue(parseHexColor(textField.getText()));
			} else {
				textField.setText(getHexColor());
			}
			super.onRender(matrices, mouseX, mouseY, delta);
		}
		
		@Override
		public boolean click(int mouseX, int mouseY) {
			textField.mouseClicked(mouseX, mouseY, 0);
			if(textField.isFocused()) {
				button.forceSphere = false;
			} else {
				button.forceSphere = true;
			}
			return super.click(mouseX, mouseY);
		}
		
		@Override
		protected void onKeyPressed(int keyCode, int scanCode, int modifiers) {
			textField.keyPressed(keyCode, scanCode, modifiers);
			super.onKeyPressed(keyCode, scanCode, modifiers);
		}
		
		@Override
		protected void onKeyReleased(int keyCode, int scanCode, int modifiers) {
			textField.keyReleased(keyCode, scanCode, modifiers);
			super.onKeyReleased(keyCode, scanCode, modifiers);
		}
		
		@Override
		protected void onCharTyped(char chr, int modifiers) {
			textField.charTyped(chr, modifiers);
			super.onCharTyped(chr, modifiers);
		}
		
		private String getHexColor() {
			String string = Integer.toHexString(new Color(setting.getRed(), setting.getGreen(), setting.getBlue()).getRGB());
			string = string.substring(2);
			return "#" + string;
		}
		
		private int parseHexColor(String color) {;
			color = color.replaceFirst("#", "");
			int c;
			try {
				c = Integer.parseInt(color, 16);
			} catch(NumberFormatException e) {
				return new Color(0,0,0,setting.getAlpha()).getRGB();
			}
			c = MathHelper.clamp(c, 0, 0xFFFFFF);
			Color rgb = new Color(c);
			return new Color(rgb.getRed(), rgb.getGreen(), rgb.getBlue(), setting.getAlpha()).getRGB();
		}
		
	}
	
	@Override
	public String getDescription() {
		return setting.getDesc();
	}
}
