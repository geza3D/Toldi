package me.geza3d.toldi.module.modules.combat;

import me.geza3d.toldi.module.EnumModuleType;
import me.geza3d.toldi.module.ToldiRotatingModule;
import me.geza3d.toldi.module.settings.NumberSetting.DoubleSetting;
import me.geza3d.toldi.module.settings.Setting.BooleanSetting;
import me.geza3d.toldi.module.settings.Setting.ModeSetting;
import me.geza3d.toldi.util.CombatUtil;
import me.geza3d.toldi.util.MathUtil;
import me.geza3d.toldi.util.RotationUtil;
import me.geza3d.toldi.util.CombatUtil.TargetMode;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.AmbientEntity;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.util.Hand;

@ToldiRotatingModule.Type(EnumModuleType.COMBAT)
public class KillAura extends ToldiRotatingModule {
	
	BooleanSetting rotate = new BooleanSetting(this, "rotate", true);
	BooleanSetting raytrace = new BooleanSetting(this, "raytrace", true);
	DoubleSetting distance = new DoubleSetting(this, "distance", 4d, 1d, 6d);
	ModeSetting priority = new ModeSetting(this, "priority", 0, "distance", "health");
	ModeSetting aim = new ModeSetting(this, "aim", 1, "leg", "center", "head");
	BooleanSetting attackPassive = new BooleanSetting(this, "attackpassive", true);
	BooleanSetting attackAggressive = new BooleanSetting(this, "attackaggressive", true);
	BooleanSetting attackNeutral = new BooleanSetting(this, "attackneutral", true);
	BooleanSetting attackInanimate = new BooleanSetting(this, "attackananimate", true);
	BooleanSetting attackPlayer = new BooleanSetting(this, "attackplayer", true);
	
	@Listener
	public void onTick() {
		ClientTickEvents.START_CLIENT_TICK.register(client -> {
			if(getStatus()) {
				Entity target = null;
				float best = Float.MAX_VALUE;
				float[] rotation = {0,0};
				for(Entity entity : getWorld().getEntities()) {
					if(entity == getPlayer()) continue;
					if(CombatUtil.getDistanceBetweenEntities(entity, getPlayer()) > distance.getValue()) continue;
					if(entity instanceof LivingEntity) {
						if(((LivingEntity) entity).getHealth() <= 0 || ((LivingEntity) entity).isDead()) {
							continue;
						}
					}
					boolean canBeAttacked = false;
					if(attackPassive.getValue()) {
						if(entity instanceof PassiveEntity || entity instanceof AmbientEntity || entity instanceof WaterCreatureEntity) {
							canBeAttacked = true;
						}
					}
					if(attackAggressive.getValue()) {
						if(entity instanceof HostileEntity) {
							canBeAttacked = true;
						}
					}
					if(attackInanimate.getValue()) {
						if(entity instanceof BoatEntity ||
								entity instanceof AbstractMinecartEntity) {
							canBeAttacked = true;
						}
					}
					if(attackPlayer.getValue()) {
						if(entity instanceof PlayerEntity) {
							canBeAttacked = true;
						}
					}

					if(!attackNeutral.getValue()) {
						if(entity instanceof PiglinEntity ||
								entity instanceof IronGolemEntity ||
								entity instanceof EndermanEntity) {
							canBeAttacked = false;
						}
					}
					switch(aim.getValue()) {
					case 0:
						rotation = RotationUtil.getRotationForVector(entity.getPos(), getPlayer());
						break;
					case 1:
						rotation = RotationUtil.getRotationForVector(entity.getPos().add(0, entity.getHeight()/2, 0), getPlayer());
						break;
					default:
						rotation = RotationUtil.getRotationForVector(entity.getPos().add(0, entity.getHeight(), 0), getPlayer());
						break;
					}
					if(raytrace.getValue()) {
						if(!MathUtil.hasNoBlockInAngle(getPlayer(), CombatUtil.getDistanceBetweenEntities(entity, getPlayer()), rotation[0], rotation[1])) {
							canBeAttacked = false;
						}
					}
					if(canBeAttacked) {
						float value = CombatUtil.getValueOfEntity(entity, getPlayer(), TargetMode.values()[priority.getValue()]);
						if(value <= best) {
							target = entity;
							best = value;
						}
					}
				}
				if(target != null) {
					if(rotate.getValue()) {
						super.priority = 2;
						if(isOnTurn()) {
							RotationUtil.rotate(rotation[0], rotation[1]);
							attackTarget(target);
						}
						return;
					} else {
						attackTarget(target);
					}
				}
				super.priority = -1;
			}
		});
	}
	
	private void attackTarget(Entity target) {
		if(getPlayer().getAttackCooldownProgress(0f) >= 1) {
			getWorld().sendPacket(PlayerInteractEntityC2SPacket.attack(target, getPlayer().isSneaking()));
			getPlayer().swingHand(Hand.MAIN_HAND);
			getPlayer().resetLastAttackedTicks();
		}
	}

}
