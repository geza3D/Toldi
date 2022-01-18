package me.geza3d.toldi.module.modules.player;

import me.geza3d.toldi.events.PacketCallback;
import me.geza3d.toldi.module.EnumModuleType;
import me.geza3d.toldi.module.ToldiModule;
import me.geza3d.toldi.module.ToldiModule.Type;
import me.geza3d.toldi.module.settings.NumberSetting.IntegerSetting;
import me.geza3d.toldi.module.settings.Setting.BooleanSetting;
import net.minecraft.item.Items;
import net.minecraft.item.ThrowablePotionItem;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket.Mode;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.ActionResult;

@Type(EnumModuleType.PLAYER)
public class FarThrow extends ToldiModule {

	IntegerSetting strength = new IntegerSetting(this, "strength", 30, 1, 300);
	BooleanSetting arrow = new BooleanSetting(this, "arrow", true);
	BooleanSetting potion = new BooleanSetting(this, "potion", true);
	BooleanSetting pearl = new BooleanSetting(this, "pearl", true);
	BooleanSetting trident = new BooleanSetting(this, "trident", true);
	BooleanSetting other = new BooleanSetting(this, "other", true);
	
	@Listener
	public void onPacketOut() {
		PacketCallback.OUT.register(packet -> {
			if(getStatus()) {
				if(packet instanceof PlayerActionC2SPacket) {
					Action action = ((PlayerActionC2SPacket) packet).getAction();
					if(action == Action.RELEASE_USE_ITEM) {
						if(arrow.getValue() && getPlayer().getMainHandStack().getItem() == Items.BOW
								|| trident.getValue() && getPlayer().getMainHandStack().getItem() == Items.TRIDENT) {
							doExploit();
						}
					}
				}
				if(packet instanceof PlayerInteractItemC2SPacket) {
					if(potion.getValue() && (getPlayer().getMainHandStack().getItem() == Items.EXPERIENCE_BOTTLE || getPlayer().getMainHandStack().getItem() instanceof ThrowablePotionItem)
							|| pearl.getValue() && getPlayer().getMainHandStack().getItem() == Items.ENDER_PEARL
							|| other.getValue() && (getPlayer().getMainHandStack().getItem() == Items.SNOWBALL || getPlayer().getMainHandStack().getItem() == Items.EGG)) {
						doExploit();
					}
				}
			}
			return ActionResult.SUCCESS;
		});
	}
	
	private void doExploit() {
		getWorld().sendPacket(new ClientCommandC2SPacket(getPlayer(), Mode.START_SPRINTING));
		for(int i = 0; i < strength.getValue(); i++) {
			getWorld().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(getPlayer().getPos().x, getPlayer().getPos().y + 1e-10, getPlayer().getPos().z, false));
			getWorld().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(getPlayer().getPos().x, getPlayer().getPos().y, getPlayer().getPos().z, true));
		}
	}
}
