package me.geza3d.toldi.keybind;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.glfw.GLFW;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import me.geza3d.toldi.module.ToldiModule;
import net.minecraft.util.math.MathHelper;

public class ModuleKeyBind {

	public static Multimap<Integer, ModuleKeyBind> keyMap = ArrayListMultimap.create();
	public static Map<ToldiModule, ModuleKeyBind> keyByModuleMap = new HashMap<>();
	
	private boolean pressed;
	private boolean pushed;
	
	private ToldiModule module;
	private int currentKey;
	private int mode;
	
	public ModuleKeyBind(ToldiModule module) {
		this.module = module;
		this.currentKey = GLFW.GLFW_KEY_UNKNOWN;
		keyMap.put(currentKey, this);
		keyByModuleMap.put(module, this);
	}
	
	public static void pushKeybinds(int key) {
		Object[] binds;
		if((binds = keyMap.get(key).toArray()) != null) {
			for(Object bind : binds) {
				((ModuleKeyBind) bind).push();
			}
		}
	}
	
	public static void releaseKeybinds(int key) {
		Object[] binds;
		if((binds = keyMap.get(key).toArray()) != null) {
			for(Object bind : binds) {
				((ModuleKeyBind) bind).release();
			}
		}
	}
	
	public void changeKey(int newKey) {
		keyMap.remove(currentKey, this);
		currentKey = newKey;
		keyMap.put(currentKey, this);
	}
	
	public void changeMode(int mode) {
		this.mode = MathHelper.clamp(mode, 0, 1);
	}
	
	public static ModuleKeyBind getKeyByModule(ToldiModule module) {
		return keyByModuleMap.get(module);
	}
	
	public void push() {
		if(!pushed) {
			pressed = true;
			pushed = true;
		}
	}
	
	public void release() {
		pressed = false;
		pushed = false;
	}
	
	public boolean isPressed() {
		if(pressed) {
			pressed = false;
			return true;
		}
		return false;
	}
	
	public boolean isPushed() {
		return pushed;
	}
	
	public ToldiModule getModule() {
		return module;
	}
	
	public int getMode() {
		return mode;
	}
}
