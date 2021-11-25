package me.geza3d.toldi.module.settings;

public class Setting<T> {

	T value;
	protected String name;
	protected String desc;
	
	public Setting(SettingHolder holder, String name, T defaultValue) {
		this.name = "setting." + name + ".name";
		this.desc = "setting." + name + ".description";
		holder.settings.add(this);
	}
	
	public T getValue() {
		return value;
	}
}
