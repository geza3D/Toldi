package me.geza3d.toldi.module;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

import me.geza3d.toldi.Toldi;
import net.minecraft.text.TranslatableText;

public enum EnumModuleType implements IModuleType{
	RENDER {
		@Override
		public String getTypeName() {
			return PREFIX + "render";
		}
	},
	EXPLOIT {
		@Override
		public String getTypeName() {
			return PREFIX + "exploit";
		}
	},
	MOVEMENT {
		@Override
		public String getTypeName() {
			return PREFIX + "movement";
		}
	},
	COMBAT {
		@Override
		public String getTypeName() {
			return PREFIX + "combat";
		}
	},
	WORLD {
		@Override
		public String getTypeName() {
			return PREFIX + "world";
		}
	},
	PLAYER {
		@Override
		public String getTypeName() {
			return PREFIX + "player";
		}
	},
	ALL {
		@Override
		public String getTypeName() {
			return PREFIX + "all";
		}
	};
	
	private static final String PREFIX = "type." + Toldi.MODID + ".";
	
	public String getName() {
		try {
			return new TranslatableText(getTypeName()).parse(null, null, 0).getString();
		} catch (CommandSyntaxException e) {
			e.printStackTrace();
		}
		return getTypeName();
	}
}
