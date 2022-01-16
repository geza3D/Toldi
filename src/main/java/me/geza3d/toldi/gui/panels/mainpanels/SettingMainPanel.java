package me.geza3d.toldi.gui.panels.mainpanels;

import java.awt.Color;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

import me.geza3d.toldi.Toldi;
import me.geza3d.toldi.gui.panels.MainPanel;
import me.geza3d.toldi.gui.panels.Panel;
import me.geza3d.toldi.gui.panels.buttons.BooleanSettingButton;
import me.geza3d.toldi.gui.panels.buttons.ColorButton.ColorSettingButton;
import me.geza3d.toldi.gui.windows.GuiValues;
import me.geza3d.toldi.gui.panels.buttons.ModeSettingButton;
import me.geza3d.toldi.gui.panels.buttons.NumberSettingButton;
import me.geza3d.toldi.module.ToldiModule;
import me.geza3d.toldi.module.settings.Setting;
import me.geza3d.toldi.module.settings.Setting.BooleanSetting;
import me.geza3d.toldi.module.settings.Setting.ColorSetting;
import me.geza3d.toldi.module.settings.Setting.ModeSetting;
import me.geza3d.toldi.module.settings.NumberSetting;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;

public class SettingMainPanel extends MainPanel {

	ToldiModule prevModule;
	
	public SettingMainPanel(int x, int y, int width, int height) {
		super(x, y, width, height, true);
	}
	
	@Override
	protected void onRender(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		DrawableHelper.fill(matrices, x, unOffsettedY, x+width, unOffsettedY+height, GuiValues.c1);
		if(prevModule != GuiValues.selectedModule) {
			prevModule = GuiValues.selectedModule;
			scrollOffset = 0;
			desiredScrollOffset = 0;
			buttons.clear();
			if(prevModule != null) {
				int height = 0;
				for(int i = 0; i < prevModule.settings.size(); i++) {
					Setting<?> setting = prevModule.settings.get(i);
					if(setting instanceof NumberSetting<?>) {
						addButton(new NumberSettingButton((NumberSetting<?>) setting, this, x+2, y+16+height, width-4, 18));
						height += 19;
					} else if(setting instanceof BooleanSetting) {
						addButton(new BooleanSettingButton((BooleanSetting) setting, this, x+2, y+16+height, width-4, 16));
						height += 17;
					} else if(setting instanceof ModeSetting) {
						addButton(new ModeSettingButton((ModeSetting) setting, this, x+2, y+16+height, width-4, 16));
						height += 17;
					} else if(setting instanceof ColorSetting) {
						addButton(new ColorSettingButton((ColorSetting) setting, this, x+2, y+16+height, width-4, 74));
						height += 75;
					}
				}
			}
		}
		for(Panel button : buttons) {
			button.render(matrices, mouseX, mouseY, delta);
		}
		fill(matrices, x, y, x+width, y+14, GuiValues.c2);
		try {
			drawCenteredText(matrices, Toldi.TEXTRENDERER, GuiValues.selectedModule != null ? GuiValues.selectedModule.getName() : new TranslatableText("module."+Toldi.MODID+".null.name").parse(null, null, 0).asString(), x + width/2, unOffsettedY+2, Color.WHITE.getRGB());
		} catch (CommandSyntaxException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isMouseOver(int mouseX, int mouseY) {
		return x <= mouseX && x + width > mouseX && y + 14 <= mouseY && y + height > mouseY;
	}
}
