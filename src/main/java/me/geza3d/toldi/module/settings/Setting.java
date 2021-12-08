package me.geza3d.toldi.module.settings;

import org.lwjgl.glfw.GLFW;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

import me.geza3d.toldi.Toldi;
import me.geza3d.toldi.keybind.ModuleKeyBind;
import me.geza3d.toldi.module.ToldiModule;
import net.minecraft.text.TranslatableText;

public class Setting<T> {

	T value;
	protected String name;
	protected String desc;
	protected SettingHolder holder;
	
	public Setting(SettingHolder holder, String name, T defaultValue) {
		this.name = name;
		this.desc = "setting." + Toldi.MODID + "." + holder.getHolderName() + "." + name + ".description";
		this.value = defaultValue;
		this.holder = holder;
		holder.settings.add(this);
	}
	
	public T getValue() {
		return value;
	}
	
	public void setValue(T value) {
		this.value = value;
	}
	
	@SuppressWarnings("unchecked")
	public T cast(Object o) {
		return (T) o;
	}
	
	public String getUntranslatedName() {
		return name;
	}
	
	public String getName() {
		try {
			return new TranslatableText("setting." + Toldi.MODID + "." + holder.getHolderName() + "." + name + ".name").parse(null, null, 0).getString();
		} catch (CommandSyntaxException e) {
			e.printStackTrace();
		}
		return name;
	}
	
	public String getDesc() {
		try {
			return new TranslatableText(desc).parse(null, null, 0).getString();
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
		
		public ModeSetting(SettingHolder holder, String name, Integer defaultValue, String... modes) {
			super(holder, name, defaultValue);
			for(int i = 0; i < modes.length; i++) {
				modes[i] = "setting." + Toldi.MODID + "." + holder.getHolderName() + "." + name + "." + modes[i].toLowerCase();
			}
			this.modes = modes;
		}
		
		public String getMode() {
			try {
				return new TranslatableText(modes[value]).parse(null, null, 0).getString();
			} catch (CommandSyntaxException e) {
				e.printStackTrace();
			}
			return modes[value];
		}
		
		public void increment() {
			value++;
			value %= modes.length;
		}
		
		public void decrement() {
			value--;
			if(value < 0) value = modes.length - 1;
		}
		
	}
	
	public static class KeyBindSetting extends Setting<Integer> {

		ModuleKeyBind bind;
		
		public KeyBindSetting(ToldiModule module, String name, Integer defaultValue) {
			super(module, name, defaultValue);
			bind = new ModuleKeyBind(module);
			setValue(defaultValue);
		}
		
		public void setMode(int mode) {
			value = getValue() | mode << 12;
			bind.changeMode(mode);
		}
		
		@Override
		public void setValue(Integer value) {
			if(value == -1) value = 0;
			if(this.value == -1) this.value = 0;
			this.value = this.value & 0xF000 | value;
			bind.changeKey(value);
		}
		
		@Override
		public Integer getValue() {
			int n = super.getValue() & 0x0FFF;
			if(n == 0) {
				return GLFW.GLFW_KEY_UNKNOWN;
			}
			return n;
		}
		
		public int getMode() {
			return value >> 12;
		}
		
		public void incrementMode() {
			int mode = getMode();
			mode++;
			mode %= 2;
			setMode(mode);
		}
	}
}
