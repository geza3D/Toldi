package me.geza3d.toldi.gui.panels.hud;

import me.geza3d.toldi.Toldi;
import me.geza3d.toldi.init.Modules;
import me.geza3d.toldi.module.ToldiHudModule;
import me.geza3d.toldi.module.ToldiModule;
import net.minecraft.client.util.math.MatrixStack;
import static me.geza3d.toldi.util.RenderUtil.*;

public class ActiveModulesHudPanel extends HudPanel{

	public ActiveModulesHudPanel(ToldiHudModule module, float xp, float yp, int width, int height) {
		super(module, xp, yp, width, height);
	}
	
	int orientation = 0;

	@Override
	protected void onRender(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		if(drawBackground) fill(matrices, x, y, x + width, y + height, 0x77000000);
		if(clicked) {
			x = mouseX - mDifX;
			y = mouseY - mDifY;
		}
		int mx = getWindowWidth() / 2;
		int my = getWindowHeight() / 2;
		int pmx = x + width / 2;
		int pmy = y + height / 2;
		if(mx >= pmx && my > pmy) {
			orientation = 0;
		} else if(mx < pmx && my >= pmy) {
			orientation = 1;
		} else if(mx < pmx && my < pmy) {
			orientation = 2;
		} else {
			orientation = 3;
		}
		
		int h = 2;
		Modules.ACTIVE.sort((o1, o2) -> Toldi.TEXTRENDERER.getWidth(o2.getRawName())-Toldi.TEXTRENDERER.getWidth(o1.getRawName()));
		for(ToldiModule module : Modules.ACTIVE) {
			if(!(module instanceof ToldiHudModule)) {
				if(orientation < 2) {
					switch(orientation) {
						case 0:
							drawStringWithShadow(matrices, Toldi.TEXTRENDERER, module.getName(), x, y+h, this.module.color.getValue());
							break;
						case 1:
							drawStringWithShadow(matrices, Toldi.TEXTRENDERER, module.getName(), x+width-Toldi.TEXTRENDERER.getWidth(module.getName()), y+h, this.module.color.getValue());
					}
				} else {
					switch(orientation) {
						case 2:
							drawStringWithShadow(matrices, Toldi.TEXTRENDERER, module.getName(), x+width-Toldi.TEXTRENDERER.getWidth(module.getName()), y+height-h-10, this.module.color.getValue());
							break;
						case 3:
							drawStringWithShadow(matrices, Toldi.TEXTRENDERER, module.getName(), x, y+height-h-10, this.module.color.getValue());
					}
				}
				h+=10;
			}
		}
		if(Modules.ACTIVE.isEmpty()) {
			height = 200;
		} else {
			int prevHeight = height;
			height = h;
			if(orientation > 1) {
				y = y+prevHeight - height;
				resetYPercentage();
			}	
		}
		if(x == -999 || y == -999) {
			resetLocation();
		}
	}

}
