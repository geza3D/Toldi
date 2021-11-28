package me.geza3d.toldi.init;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.geza3d.toldi.module.EnumModuleType;
import me.geza3d.toldi.module.ToldiModule;
import me.geza3d.toldi.module.modules.player.Debug;

public class Modules {

	public static final List<ToldiModule> RENDER = new ArrayList<>();
	public static final List<ToldiModule> EXPLOIT = new ArrayList<>();
	public static final List<ToldiModule> MOVEMENT = new ArrayList<>();
	public static final List<ToldiModule> COMBAT = new ArrayList<>();
	public static final List<ToldiModule> WORLD = new ArrayList<>();
	public static final List<ToldiModule> PLAYER = new ArrayList<>();
	
	public static final List<ToldiModule> ALL = new ArrayList<>();
	
	public static final Map<EnumModuleType, List<ToldiModule>> MODULESBYTYPE = new HashMap<>();
	
	public static final Map<String, ToldiModule> MODULESBYNAME = new HashMap<>();
	
	public static Debug DEBUG;
	
	public static void registerModules() {
		MODULESBYTYPE.put(EnumModuleType.RENDER, RENDER);
		MODULESBYTYPE.put(EnumModuleType.EXPLOIT, EXPLOIT);
		MODULESBYTYPE.put(EnumModuleType.MOVEMENT, MOVEMENT);
		MODULESBYTYPE.put(EnumModuleType.COMBAT, COMBAT);
		MODULESBYTYPE.put(EnumModuleType.WORLD, WORLD);
		MODULESBYTYPE.put(EnumModuleType.PLAYER, PLAYER);
		MODULESBYTYPE.put(EnumModuleType.ALL, ALL);
		
		DEBUG = new Debug();
	}
	
	public static ToldiModule getModuleByName(String name) {
		return MODULESBYNAME.get(name);
	}
}
