package me.geza3d.toldi;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import me.geza3d.toldi.init.Modules;
import net.fabricmc.api.ModInitializer;

public class Toldi implements ModInitializer {

	public static final String MODID = "toldi";
	public static final String NAME = "Toldi";
	public static final Logger LOGGER = LogManager.getLogger(NAME);
	
	
	@Override
	public void onInitialize() {
		Modules.registerModules();
	}

}