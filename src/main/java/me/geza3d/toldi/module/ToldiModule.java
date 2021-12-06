package me.geza3d.toldi.module;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

import me.geza3d.toldi.Toldi;
import me.geza3d.toldi.init.Modules;
import me.geza3d.toldi.module.settings.SettingHolder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.text.TranslatableText;

public class ToldiModule extends SettingHolder {

	protected EnumModuleType type;
	protected String name;
	public String info = "";
	protected String desc;
	protected boolean status = false;
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public static @interface Type {
		EnumModuleType value();
	}
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public static @interface Listener {
	}
	
	public void initListeners() {
		for(Method method : this.getClass().getMethods()) {
			if(method.getAnnotation(Listener.class) != null) {
				try {
					method.invoke(this);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@SuppressWarnings("incomplete-switch")
	public ToldiModule() {
		Type type = this.getClass().getAnnotation(Type.class);
		if(type == null) {
			throw new RuntimeException("Unregistered module! Register " + this.getClass().toString() + " using the @ModuleReg annotiation");
		}
		name = this.getClass().getSimpleName().toLowerCase();
		desc = this.getClass().getSimpleName().toLowerCase();
		this.type = type.value();
		switch(this.type) {
		case RENDER:
			Modules.RENDER.add(this);
			break;
		case EXPLOIT:
			Modules.EXPLOIT.add(this);
			break;
		case MOVEMENT:
			Modules.MOVEMENT.add(this);
			break;
		case COMBAT:
			Modules.COMBAT.add(this);
			break;
		case WORLD:
			Modules.WORLD.add(this);
			break;
		case PLAYER:
			Modules.PLAYER.add(this);
			break;
		}
		Modules.ALL.add(this);
		Modules.MODULESBYNAME.put(name, this);
		initListeners();
	}
	
	public void enable() {
		if(!status) {
			status = true;
		}
	}
	
	public void disable() {
		if(status) {
			status = false;
		}
	}
	
	public void toggle() {
		if(status) {
			disable();
		} else {
			enable();
		}
	}
	
	public boolean getStatus() {
		return status && getPlayer() != null;
	}
	
	public boolean getRawStatus() {
		return status;
	}
	
	public String getRawName() {
		try {
			return new TranslatableText("module." + Toldi.MODID + "." + name + ".name").parse(null, null, 0).getString();
		} catch (CommandSyntaxException e) {
			e.printStackTrace();
		}
		return name;
	}
	
	public String getName() {
		return getRawName() + (info.isEmpty() ? "" : " " + info);
	}
	
	public EnumModuleType getType() {
		return type;
	}
	
	@Override
	public String getHolderName() {
		return name;
	}
	
	public String getUntranslatedName() {
		return name;
	}
	
	//Utils
	protected MinecraftClient getMC() {
		return MinecraftClient.getInstance();
	}
	
	protected ClientPlayerEntity getPlayer() {
		return getMC().player;
	}
	
	protected ClientWorld getWorld() {
		return getMC().world;
	}
}
