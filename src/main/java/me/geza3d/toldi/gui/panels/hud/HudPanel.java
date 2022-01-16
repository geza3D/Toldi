package me.geza3d.toldi.gui.panels.hud;

import me.geza3d.toldi.gui.panels.Panel;
import me.geza3d.toldi.gui.windows.GuiValues;
import me.geza3d.toldi.module.ToldiHudModule;
import me.geza3d.toldi.util.RenderUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class HudPanel extends Panel{

	ToldiHudModule module;
	boolean clicked = false;
	int mDifX = 0;
	int mDifY = 0;
	float xp;
	float yp;
	public boolean drawBackground = false;
	
	public HudPanel(ToldiHudModule module, float xp, float yp, int width, int height) {
		super(-999, -999, width, height);
		this.module = module;
		this.xp = xp;
		this.yp = yp;
	}
	
	@Override
	protected void onRender(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		if(drawBackground) fill(matrices, x, y, x + width, y + height, 0x77000000);
		//This might seem dumb to not load during the game initializes, however, there's no window then...
		if(x == -999 || y == -999) {
			resetLocation();
		}
		if(clicked) {
			x = mouseX - mDifX;
			y = mouseY - mDifY;
		}
	}
	
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		visible = module.getRawStatus();
		super.render(matrices, mouseX, mouseY, delta);
	}
	
	@Override
	protected void onClick(int mouseX, int mouseY) {
		clicked = true;
		mDifX = mouseX - x;
		mDifY = mouseY - y;
		GuiValues.movingHudPanel = true;
	}
	
	@Override
	protected void onReleaseMouse(int mouseX, int mouseY) {
		clicked = false;
		resetXPercentage();
		resetYPercentage();
		resetLocation();
		GuiValues.movingHudPanel = false;
	}
	
	public void setXPercentage(float x) {
		this.xp = MathHelper.clamp(x, 0f, 1f);
	}
	
	public void setYPercentage(float y) {
		this.yp = MathHelper.clamp(y, 0f, 1f);
	}
	
	public float getXPercentage() {
		return x / (float) (RenderUtil.getWindowWidth()-width);
	}
	
	public float getYPercentage() {
		return y / (float) (RenderUtil.getWindowHeight()-height);
	}
	
	public void resetXPercentage() {
		setXPercentage(getXPercentage());
	}
	
	public void resetYPercentage() {
		setYPercentage(getYPercentage());
	}
	
	//In case of the window size changing...
	public void resetLocation() {
		x = (int)((RenderUtil.getWindowWidth()-width) * xp);
		y = (int) ((RenderUtil.getWindowHeight()-height) * yp);
	}
	
	@Override
	public String getDescription() {
		return module.getDescription();
	}
	
}
