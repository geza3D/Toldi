package me.geza3d.toldi.module.modules.player;

import me.geza3d.toldi.Toldi;
import me.geza3d.toldi.events.PacketCallback;
import me.geza3d.toldi.module.EnumModuleType;
import me.geza3d.toldi.module.ToldiModule;
import me.geza3d.toldi.module.settings.NumberSetting.DoubleSetting;
import me.geza3d.toldi.module.settings.NumberSetting.IntegerSetting;
import me.geza3d.toldi.module.settings.Setting.BooleanSetting;
import me.geza3d.toldi.module.settings.Setting.ModeSetting;
import net.minecraft.util.ActionResult;

@ToldiModule.Type(type = EnumModuleType.PLAYER)
public class Debug extends ToldiModule {

	IntegerSetting test1 = new IntegerSetting(this, "test1", 5, 0, 10);
	DoubleSetting test2 = new DoubleSetting(this, "test2", 5d, 0d, 10d);
	BooleanSetting test3 = new BooleanSetting(this, "test3", false);
	ModeSetting test4 = new ModeSetting(this, "test3", 0, "A", "B", "C");
	
	@Listener
	public void onPacket() {
		PacketCallback.IN.register(packet -> {
			if(getStatus()) Toldi.LOGGER.info(packet.getClass());
			return ActionResult.SUCCESS;
		});
	}
}
