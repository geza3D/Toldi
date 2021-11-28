package me.geza3d.toldi.gui.panels.mainpanels;

import java.awt.Color;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

import me.geza3d.toldi.Toldi;
import me.geza3d.toldi.gui.clickgui.ClickGui;
import me.geza3d.toldi.gui.panels.ButtonPanel;
import me.geza3d.toldi.gui.panels.MainPanel;
import me.geza3d.toldi.gui.panels.buttons.BooleanSettingButton;
import me.geza3d.toldi.gui.panels.buttons.ModeSettingButton;
import me.geza3d.toldi.gui.panels.buttons.NumberSettingButton;
import me.geza3d.toldi.module.ToldiModule;
import me.geza3d.toldi.module.settings.Setting;
import me.geza3d.toldi.module.settings.Setting.BooleanSetting;
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
		DrawableHelper.fill(matrices, x, unOffsettedY, x+width, unOffsettedY+height, new Color(0x360094, false).getRGB());
		if(prevModule != ClickGui.selectedModule) {
			prevModule = ClickGui.selectedModule;
			scrollOffset = 0;
			desiredScrollOffset = 0;
			buttons.clear();
			if(prevModule != null) {
				for(int i = 0; i < prevModule.settings.size(); i++) {
					Setting<?> setting = prevModule.settings.get(i);
					if(setting instanceof NumberSetting<?>) {
						addButton(new NumberSettingButton((NumberSetting<?>) setting, this, x+2, y+16+18*i, width-4, 16));
					} else if(setting instanceof BooleanSetting) {
						addButton(new BooleanSettingButton((BooleanSetting) setting, this, x+2, y+16+18*i, width-4, 16));
					} else if(setting instanceof ModeSetting) {
						addButton(new ModeSettingButton((ModeSetting) setting, this, x+2, y+16+18*i, width-4, 16));
					}
				}
			}
		}
		for(ButtonPanel button : buttons) {
			button.render(matrices, mouseX, mouseY, delta);
		}
		fill(matrices, x, y, x+width, y+14, new Color(0x23005e, false).getRGB());
		try {
			drawCenteredText(matrices, Toldi.TEXTRENDERER, ClickGui.selectedModule != null ? ClickGui.selectedModule.getName() : new TranslatableText("module."+Toldi.MODID+".null.name").parse(null, null, 0).asString(), x + width/2, unOffsettedY+2, Color.WHITE.getRGB());
		} catch (CommandSyntaxException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isMouseOver(int mouseX, int mouseY) {
		return x <= mouseX && x + width > mouseX && y + 14 <= mouseY && y + height > mouseY;
	}
}
