package me.geza3d.toldi;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import me.geza3d.toldi.gui.clickgui.ClickGui;
import me.geza3d.toldi.handlers.ConfigHandler;
import me.geza3d.toldi.init.Modules;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;

public class Toldi implements ModInitializer {

	public static final String MODID = "toldi";
	public static final String NAME = "Toldi";
	public static final Logger LOGGER = LogManager.getLogger(NAME);
	public static MinecraftClient CLIENT;
	public static TextRenderer TEXTRENDERER;
	public static KeyBinding clickGuiKey;

	@Override
	public void onInitialize() {
		CLIENT = MinecraftClient.getInstance();
		TEXTRENDERER = new TextRenderer(id -> {
			return CLIENT.textRenderer.getFontStorage(new Identifier(MODID, "code_new_roman"));
		});
		Modules.registerModules();
		ConfigHandler.initConfigHandler();
		
		clickGuiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("key." + MODID + ".clickgui", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_CONTROL, NAME));
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while(clickGuiKey.wasPressed()) {
				CLIENT.setScreen(new ClickGui());
			}
		});
	}

}