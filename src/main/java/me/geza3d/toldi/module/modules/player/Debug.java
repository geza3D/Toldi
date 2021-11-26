package me.geza3d.toldi.module.modules.player;

import me.geza3d.toldi.Toldi;
import me.geza3d.toldi.events.PacketCallback;
import me.geza3d.toldi.module.EnumModuleType;
import me.geza3d.toldi.module.Module;
import net.minecraft.util.ActionResult;

@Module.Type(type = EnumModuleType.PLAYER)
public class Debug extends Module {

	@Listener
	public void onPacket() {
		PacketCallback.IN.register(packet -> {
			if(getStatus()) Toldi.LOGGER.info(packet.getClass());
			return ActionResult.SUCCESS;
		});
	}
}
