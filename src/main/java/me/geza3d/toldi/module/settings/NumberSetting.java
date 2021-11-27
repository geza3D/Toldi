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
	
	public static class IntegerSetting extends NumberSetting<Integer> {
		public IntegerSetting(SettingHolder holder, String name, Integer defaultValue, Integer min, Integer max) {
			super(holder, name, defaultValue, min, max);
		}
	}
	
	public static class DoubleSetting extends NumberSetting<Double> {
		public DoubleSetting(SettingHolder holder, String name, Double defaultValue, Double min, Double max) {
			super(holder, name, defaultValue, min, max);
		}
	}

}
