package me.geza3d.toldi.init;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.geza3d.toldi.module.EnumModuleType;
import me.geza3d.toldi.module.ToldiModule;
import me.geza3d.toldi.module.modules.exploit.PortalGodmode;
import me.geza3d.toldi.module.modules.hud.ActiveModules;
import me.geza3d.toldi.module.modules.movement.BoatFly;
import me.geza3d.toldi.module.modules.player.Debug;
import me.geza3d.toldi.module.modules.render.Freecam;
import me.geza3d.toldi.module.modules.render.Fullbright;
import me.geza3d.toldi.module.modules.render.StorageESP;
import me.geza3d.toldi.module.modules.world.Timer;

public class Modules {

	public static final List<ToldiModule> RENDER = new ArrayList<>();
	public static final List<ToldiModule> EXPLOIT = new ArrayList<>();
	public static final List<ToldiModule> MOVEMENT = new ArrayList<>();
	public static final List<ToldiModule> COMBAT = new ArrayList<>();
	public static final List<ToldiModule> WORLD = new ArrayList<>();
	public static final List<ToldiModule> PLAYER = new ArrayList<>();
	public static final List<ToldiModule> HUD = new ArrayList<>();
	
	public static final List<ToldiModule> ALL = new ArrayList<>();
	
	public static final List<ToldiModule> ACTIVE = new ArrayList<>();
	
	public static final Map<EnumModuleType, List<ToldiModule>> MODULESBYTYPE = new HashMap<>();
	
	public static final Map<String, ToldiModule> MODULESBYNAME = new HashMap<>();
	
	//Render
	public static Freecam FREECAM;
	public static Fullbright FULLBRIGHT;
	public static StorageESP STORAGEESP;
	
	//Exploit
	public static PortalGodmode PORTALGODMODE;
	
	//Movement
	public static BoatFly BOATFLY;
	
	//Player
	public static Debug DEBUG;
	
	//World
	public static Timer TIMER;
	
	//Hud
	public static ActiveModules ACTIVEMODULES;
	
	public static void registerModules() {
		MODULESBYTYPE.put(EnumModuleType.RENDER, RENDER);
		MODULESBYTYPE.put(EnumModuleType.EXPLOIT, EXPLOIT);
		MODULESBYTYPE.put(EnumModuleType.MOVEMENT, MOVEMENT);
		MODULESBYTYPE.put(EnumModuleType.COMBAT, COMBAT);
		MODULESBYTYPE.put(EnumModuleType.WORLD, WORLD);
		MODULESBYTYPE.put(EnumModuleType.PLAYER, PLAYER);
		MODULESBYTYPE.put(EnumModuleType.HUD, HUD);
		MODULESBYTYPE.put(EnumModuleType.ALL, ALL);
		
		for(Field field : Modules.class.getFields()) {
			if(ToldiModule.class.isAssignableFrom(field.getType())) {
				try {
					field.set(Modules.class, field.getType().getDeclaredConstructor().newInstance());
				} catch (IllegalArgumentException | IllegalAccessException | InstantiationException
						| InvocationTargetException | NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public static ToldiModule getModuleByName(String name) {
		return MODULESBYNAME.get(name);
	}
}
