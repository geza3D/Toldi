package me.geza3d.toldi.module;

public enum EnumModuleType implements IModuleType{
	RENDER {
		@Override
		public String getTypeName() {
			return "Render";
		}
	},
	EXPLOIT {
		@Override
		public String getTypeName() {
			return "Exploit";
		}
	},
	MOVEMENT {
		@Override
		public String getTypeName() {
			return "Movement";
		}
	},
	COMBAT {
		@Override
		public String getTypeName() {
			return "Combat";
		}
	},
	WORLD {
		@Override
		public String getTypeName() {
			return "World";
		}
	},
	PLAYER {
		@Override
		public String getTypeName() {
			return "Player";
		}
	}
}
