package me.geza3d.toldi.module.settings;

public class NumberSetting<T extends Number> extends Setting<T> {

	T min;
	T max;
	
	public NumberSetting(SettingHolder holder, String name, T defaultValue, T min, T max) {
		super(holder, name, defaultValue);
		this.min = min;
		this.max = max;
	}

	public T getMin() {
		return min;
	}

	public T getMax() {
		return max;
	}

}
