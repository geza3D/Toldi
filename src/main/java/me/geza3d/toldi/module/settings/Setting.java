package me.geza3d.toldi.module.settings;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

import me.geza3d.toldi.Toldi;
import net.minecraft.text.TranslatableText;

public class Setting<T> {

	T value;
	protected String name;
	protected String desc;
	
	public Setting(SettingHolder holder, String name, T defaultValue) {
		this.name = "setting." + Toldi.MODID + "." + name + ".name";
		this.desc = "setting." + Toldi.MODID + "." + name + ".description";
		this.value = defaultValue;
		holder.settings.add(this);
	}
	
	public T getValue() {
		return value;
	}
	
	public void setValue(T value) {
		this.value = value;
	}
	
	public String getName() {
		try {
			return new TranslatableText(name).parse(null, null, 0).asString();
		} catch (CommandSyntaxException e) {
			e.printStackTrace();
		}
		return name;
	}
	
	public String getDesc() {
		try {
			return new TranslatableText(desc).parse(null, null, 0).asString();
		} catch (CommandSyntaxException e) {
			e.printStackTrace();
		}
		return desc;
	}
	
	public static class BooleanSetting extends Setting<Boolean> {

		public BooleanSetting(SettingHolder holder, String name, Boolean defaultValue) {
			super(holder, name, defaultValue);
		}
	
	}
	
	public static class ModeSetting extends Setting<Integer> {

		String[] modes;
		
		public ModeSetting(SettingHolder holder, String name, Integer defaultValue, String[] modes) {
			super(holder, name, defaultValue);
			for(int i = 0; i < modes.length; i++) {
				modes[i] = "setting." + Toldi.MODID + "." + name + "." + modes[i].toLowerCase();
			}
		}
		
		public String getMode() {
			try {
				return new TranslatableText(modes[value]).parse(null, null, 0).asString();
			} catch (CommandSyntaxException e) {
				e.printStackTrace();
			}
			return modes[value];
		}
		
	}
}
