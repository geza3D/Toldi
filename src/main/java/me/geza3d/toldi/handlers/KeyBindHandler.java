package me.geza3d.toldi.handlers;

import org.lwjgl.glfw.GLFW;
import me.geza3d.toldi.Toldi;
import me.geza3d.toldi.events.KeyCallback;
import me.geza3d.toldi.gui.clickgui.ClickGui;
import me.geza3d.toldi.keybind.ModuleKeyBind;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.ActionResult;

public class KeyBindHandler {

	public static KeyBinding clickGuiKey;
	
	public static void registerKeyBinds() {
		clickGuiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("key." + Toldi.MODID + ".clickgui", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_CONTROL, Toldi.NAME));
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while(clickGuiKey.wasPressed()) {
				Toldi.CLIENT.setScreen(new ClickGui());
			}
			ModuleKeyBind.keyByModuleMap.forEach((module, bind) -> {
				switch(bind.getMode()) {
				case 0:
					if(bind.isPressed()) bind.getModule().toggle();
					break;
				case 1:
					if(bind.isPushed() && !module.getStatus()) {
						module.enable();
					} else if(!bind.isPushed() && module.getStatus()) {
						module.disable();
					}
				}
			});
		});
		KeyCallback.EVENT.register((key, action) -> {
			if(Toldi.CLIENT.currentScreen == null) {
				if(action == 1) {
					ModuleKeyBind.pushKeybinds(key);
				} else if(action == 0){
					ModuleKeyBind.releaseKeybinds(key);
				}
			}
			return ActionResult.SUCCESS;
		});
	}
}
