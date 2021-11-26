package me.geza3d.toldi.init;

import me.geza3d.toldi.module.modules.player.Debug;

public class Modules {

	public static Debug DEBUG;
	
	public static void registerModules() {
		DEBUG = new Debug();
	}
}
