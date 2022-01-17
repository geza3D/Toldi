package me.geza3d.toldi.module.modules.combat;

import me.geza3d.toldi.handlers.RotationHandler;
import me.geza3d.toldi.module.EnumModuleType;
import me.geza3d.toldi.module.ToldiRotatingModule;
import me.geza3d.toldi.module.settings.NumberSetting.DoubleSetting;
import me.geza3d.toldi.module.settings.Setting.BooleanSetting;
import me.geza3d.toldi.module.settings.Setting.ModeSetting;
import me.geza3d.toldi.util.CombatUtil;
import me.geza3d.toldi.util.RotationUtil;
import me.geza3d.toldi.util.CombatUtil.TargetMode;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.util.Hand;

@ToldiRotatingModule.Type(EnumModuleType.COMBAT)
public class KillAura extends ToldiRotatingModule {
	
	DoubleSetting distance = new DoubleSetting(this, "distance", 4d, 1d, 10d);
	ModeSetting mode = new ModeSetting(this, "mode", 0, "distance", "health");
	BooleanSetting attackPassive = new BooleanSetting(this, "attackpassive", true);
	BooleanSetting attackAggressive = new BooleanSetting(this, "attackaggressive", true);
	BooleanSetting attackNeutral = new BooleanSetting(this, "attackneutral", true);
	BooleanSetting attackInanimate = new BooleanSetting(this, "attackananimate", true);
	BooleanSetting attackPlayer = new BooleanSetting(this, "attackplayer", true);
	
	public KillAura() {
		super(2);
	}
	
	@Listener
	public void onTick() {
		ClientTickEvents.START_CLIENT_TICK.register(client -> {
			if(getStatus()) {
				CombatUtil.attackPassive = attackPassive.getValue();
				CombatUtil.attackAggressive = attackAggressive.getValue();
				CombatUtil.attackNeutral = attackNeutral.getValue();
				CombatUtil.attackInanimate = attackInanimate.getValue();
				CombatUtil.attackPlayer = attackPlayer.getValue();
				Entity target = CombatUtil.getTarget(getWorld(), getPlayer(), distance.getValue(), TargetMode.values()[mode.getValue()]);
				if(target != null) {
					float[] rotation = RotationUtil.getRotationForVector(target.getPos().add(0, target.getHeight()/2, 0), getPlayer());
					RotationHandler.rotate(rotation[0], rotation[1]);
					if(getPlayer().getAttackCooldownProgress(0f) >= 1) {
						getWorld().sendPacket(PlayerInteractEntityC2SPacket.attack(target, getPlayer().isSneaking()));
						getPlayer().swingHand(Hand.MAIN_HAND);
						getPlayer().resetLastAttackedTicks();
					}
				}
			}
		});
	}

}
