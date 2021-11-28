package me.geza3d.toldi.handlers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import me.geza3d.toldi.Toldi;
import me.geza3d.toldi.events.JoinLeaveCallback;
import me.geza3d.toldi.init.Modules;
import me.geza3d.toldi.module.ToldiModule;
import me.geza3d.toldi.module.settings.Setting;
import me.geza3d.toldi.module.settings.Setting.BooleanSetting;
import me.geza3d.toldi.module.settings.Setting.ModeSetting;
import me.geza3d.toldi.module.settings.NumberSetting.DoubleSetting;
import me.geza3d.toldi.module.settings.NumberSetting.IntegerSetting;
import net.minecraft.util.ActionResult;

public class ConfigHandler {

	public static final String DIR = Toldi.CLIENT.runDirectory.getAbsolutePath().substring(0, Toldi.CLIENT.runDirectory.getAbsolutePath().length()-1) + "toldi\\";
	public static String currentProfile = "default";
	
	public static void initConfigHandler(){
		loadModuleStatus();
		loadModules();
		JoinLeaveCallback.LEAVE.register(()->{
			saveModuleStatus();
			saveModules();
			return ActionResult.SUCCESS;
		});
	}
	
	private static void loadModuleStatus() {
		File f = new File(DIR + currentProfile + "\\modulestatus.json");
		String json = readFileIfExists(f);
		if(json == null) return;
		Gson gson = new Gson();
		String[] modules = gson.fromJson(json, String[].class);
		for(String name : modules) {
			ToldiModule module = Modules.getModuleByName(name);
			if(module != null) module.enable();
		}
	}
	
	private static void saveModuleStatus() {
		List<String> activeModules = new ArrayList<>();
		for(ToldiModule module : Modules.ALL) {
			if(module.getRawStatus()) {
				activeModules.add(module.getUntranslatedName());
			}
		}
		Gson gson = new Gson();
		String moduleStatus = gson.toJson(activeModules.toArray());
		writeIntoFile(DIR+currentProfile, "modulestatus.json", moduleStatus);
	}
	
	private static void loadModules() {
		Gson gson = new Gson();
		for(ToldiModule module : Modules.ALL) {
			File f = new File(DIR + currentProfile + "\\" + module.getType().getTypeName() + "\\" + module.getUntranslatedName() + ".json");
			String json = readFileIfExists(f);
			if(json == null) continue;
			Map<String, Object> map = gson.fromJson(json, new TypeToken<Map<String,Object>>() {}.getType());
			for(Setting<?> setting : module.settings) {
				Object o = map.get(setting.getUntranslatedName());
				if(setting instanceof IntegerSetting) {
					((IntegerSetting)setting).setValue((int)(double)(Double)o);
				} else if(setting instanceof DoubleSetting) {
					((DoubleSetting)setting).setValue((Double)o);
				} else if(setting instanceof BooleanSetting) {
					((BooleanSetting)setting).setValue((Boolean)o);
				} else if(setting instanceof ModeSetting) {
					((ModeSetting)setting).setValue((int)(double)(Double)o);
				}
			}
		}
	}
	
	private static void saveModules() {
		Map<String, Object> map = new HashMap<>();
		Gson gson = new Gson();
		for(ToldiModule module : Modules.ALL) {
			for(Setting<?> setting : module.settings) {
				map.put(setting.getUntranslatedName(), setting.getValue());
			}
			String settings = gson.toJson(map);
			writeIntoFile(DIR+currentProfile+"\\"+module.getType().getTypeName(), module.getUntranslatedName() + ".json", settings);
		}
	}
	
	private static String readFileIfExists(File file) {
		if(file.exists()) {
			try {
				return new String(Files.readAllBytes(Paths.get(file.getPath())), StandardCharsets.UTF_8);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	private static void writeIntoFile(String dir, String fileName, String data) {
		File file = new File(dir+"\\"+fileName);
		file.delete();
		Path path = Paths.get(dir);
		if(!Files.exists(path)) {
			try {
				Files.createDirectories(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(file.exists()) {
			try {
				FileWriter w = new FileWriter(file);
				w.write(data);
				w.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
