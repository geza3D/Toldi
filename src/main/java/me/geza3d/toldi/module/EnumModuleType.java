package me.geza3d.toldi.module;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

import me.geza3d.toldi.Toldi;
import net.minecraft.text.TranslatableText;

public enum EnumModuleType implements IModuleType{
	RENDER {
		@Override
		public String getTypeName() {
			return "render";
		}
	},
	EXPLOIT {
		@Override
		public String getTypeName() {
			return "exploit";
		}
	},
	MOVEMENT {
		@Override
		public String getTypeName() {
			return "movement";
		}
	},
	COMBAT {
		@Override
		public String getTypeName() {
			return "combat";
		}
	},
	WORLD {
		@Override
		public String getTypeName() {
			return "world";
		}
	},
	PLAYER {
		@Override
		public String getTypeName() {
			return "player";
		}
	},
	ALL {
		@Override
		public String getTypeName() {
			return "all";
		}
	},
	HUD {
		@Override
		public String getTypeName() {
			return "hud";
		}
	};
	
	private static final String PREFIX = "type." + Toldi.MODID + ".";
	
	public String getName() {
		try {
			return new TranslatableText(PREFIX+getTypeName()+".name").parse(null, null, 0).getString();
		} catch (CommandSyntaxException e) {
			e.printStackTrace();
		}
		return getTypeName();
	}
	
	public String getDesc() {
		try {
			return new TranslatableText(PREFIX+getTypeName()+".desc").parse(null, null, 0).getString();
		} catch (CommandSyntaxException e) {
			e.printStackTrace();
		}
		return getTypeName();
	}
}
