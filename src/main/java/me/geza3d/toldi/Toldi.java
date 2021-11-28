package me.geza3d.toldi;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import me.geza3d.toldi.gui.clickgui.ClickGui;
import me.geza3d.toldi.handlers.ConfigHandler;
import me.geza3d.toldi.init.Modules;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.util.Identifier;

public class Toldi implements ModInitializer {

	public static final String MODID = "toldi";
	public static final String NAME = "Toldi";
	public static final Logger LOGGER = LogManager.getLogger(NAME);
	public static MinecraftClient CLIENT;
	public static TextRenderer TEXTRENDERER;
	
	@Override
	public void onInitialize() {
		CLIENT = MinecraftClient.getInstance();
		TEXTRENDERER = new TextRenderer(id -> {
			return CLIENT.textRenderer.getFontStorage(new Identifier(MODID, "code_new_roman"));
		});
		Modules.registerModules();
		ConfigHandler.initConfigHandler();
		
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while(CLIENT.options.keySprint.wasPressed()) {
				CLIENT.setScreen(new ClickGui());
			}
		});
	}

}