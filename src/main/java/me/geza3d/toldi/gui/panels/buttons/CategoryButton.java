package me.geza3d.toldi.gui.panels.buttons;

import java.util.Random;

import me.geza3d.toldi.Toldi;
import me.geza3d.toldi.gui.clickgui.ClickGui;
import me.geza3d.toldi.gui.panels.ButtonPanel;
import me.geza3d.toldi.gui.panels.MainPanel;
import me.geza3d.toldi.module.EnumModuleType;
import me.geza3d.toldi.util.RenderUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CategoryButton extends ButtonPanel {

	EnumModuleType type;
	Item item;
	int r;
	
	public CategoryButton(EnumModuleType type, Item item, MainPanel main, int x, int y) {
		super(main, x, y, 30, 30, "");
		this.type = type;
		this.item = item;
		this.r = new Random().nextInt(360);
	}

	@Override
	protected void onRender(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		int rgb = ClickGui.c3;
		if(main.isMouseOver(mouseX, mouseY) && isMouseOver(mouseX, mouseY) || ClickGui.selectedType == type) {
			rgb = ClickGui.c4;
			r++;
			r%=360;
		}
		RenderUtil.drawPolygon(matrices, 0+r, 360+r, 5, 15, x, y, ClickGui.c2);
		RenderUtil.drawPolygon(matrices, 0+r, 360+r, 5, 13, x+2, y+2, rgb);
		Toldi.CLIENT.getItemRenderer().renderGuiItemIcon(new ItemStack(item), x+7, y+7);
	}

	@Override
	protected void onClick(int mouseX, int mouseY) {
		ClickGui.selectedType = type;
		ClickGui.selectedModule = null;
	}

}
