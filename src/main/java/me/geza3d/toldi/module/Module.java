package me.geza3d.toldi.module;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.TranslatableText;

public class Module {

	protected EnumModuleType type;
	protected String name;
	public String info = "";
	protected String desc;
	protected boolean status = true;
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public static @interface Type {
		EnumModuleType type();
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
	
	public Module() {
		Type type = this.getClass().getAnnotation(Type.class);
		if(type == null) {
			throw new RuntimeException("Unregistered module! Register " + this.getClass().toString() + " using the @ModuleReg annotiation");
		}
		name = "module." + this.getClass().getName().toLowerCase() + ".name";
		desc = "module." + this.getClass().getName().toLowerCase() + ".description";
		this.type = type.type();
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
	
	public String getRawName() {
		return new TranslatableText(name).asString();
	}
	
	public String getName() {
		return getRawName() + (info.isEmpty() ? "" : " " + info);
	}
	
	//Utils
	public MinecraftClient getMC() {
		return MinecraftClient.getInstance();
	}
	
	@SuppressWarnings("resource")
	public ClientPlayerEntity getPlayer() {
		return getMC().player;
	}
}
