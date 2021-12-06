package me.geza3d.toldi.module.settings;

import java.util.ArrayList;
import java.util.List;

public abstract class SettingHolder {

	public List<Setting<?>> settings = new ArrayList<>();
	public abstract String getHolderName();
}
