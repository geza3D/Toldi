package me.geza3d.toldi.module.modules.movement;

import me.geza3d.toldi.events.EntityCallback;
import me.geza3d.toldi.events.PacketCallback;
import me.geza3d.toldi.init.Modules;
import me.geza3d.toldi.module.EnumModuleType;
import me.geza3d.toldi.module.ToldiModule;
import me.geza3d.toldi.module.ToldiModule.Type;
import me.geza3d.toldi.module.settings.NumberSetting.DoubleSetting;
import me.geza3d.toldi.module.settings.Setting.BooleanSetting;
import me.geza3d.toldi.util.MathUtil;
import me.geza3d.toldi.util.Stopper;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.network.packet.c2s.play.PlayerInputC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.s2c.play.VehicleMoveS2CPacket;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

@Type(EnumModuleType.MOVEMENT)
public class BoatFly extends ToldiModule {

	DoubleSetting hspeed = new DoubleSetting(this, "hspeed", 3d, 0d, 5d);
	DoubleSetting vspeed = new DoubleSetting(this, "vspeed", 3d, 0d, 5d);
	BooleanSetting bypass = new BooleanSetting(this, "bypass", false);
	BooleanSetting noclip = new BooleanSetting(this, "noclip", false);
	
	BoatEntity boat;
	double yLevel;
	Stopper kickTimer = new Stopper();
	
	@Override
	public void disable() {
		boat.setNoGravity(false);
		boat.noClip = false;
		boat = null;
		kickTimer.resetStopper();
		super.disable();
	}
	
	@Listener
	public void onPlayerTick() {
		EntityCallback.TICK.register(entity -> {
			if(getStatus() && entity == getPlayer()) {
				if(entity.getVehicle() instanceof BoatEntity && entity.getVehicle().getFirstPassenger() == getPlayer() && (boat == null || boat != entity.getVehicle())) {
					boat = (BoatEntity) entity.getVehicle();
					boat.setNoGravity(true);
					yLevel = boat.getY();
				} else if((!(entity.getVehicle() instanceof BoatEntity) || entity.getVehicle().getFirstPassenger() != getPlayer()) && boat != null) {
					boat.setNoGravity(false);
					boat.noClip = false;
					boat = null;
				}
			}
			return ActionResult.SUCCESS;
		});
	}
	
	@Listener
	public void onBoatTick() {
		EntityCallback.TICK.register(entity -> {
			if(getStatus() && entity == boat) {
				entity.setYaw(getPlayer().getHeadYaw());
				entity.noClip = noclip.getValue();
				if(getMC().options.keyJump.isPressed() &&
						!Modules.FREECAM.getStatus()) {
					kickTimer.startStopper();
					if(kickTimer.checkStopper(3000l, false)) {
						entity.getVelocity().y = -0.04;
						kickTimer.checkStopper(3500l, true);
					} else {
						entity.getVelocity().y = vspeed.getValue();
					}
					
					yLevel = entity.getY();
				
					
				} else if(getMC().options.keySprint.isPressed() &&
						!Modules.FREECAM.getStatus()){
					
					kickTimer.resetStopper();
					entity.getVelocity().y = -vspeed.getValue();
					yLevel = entity.getY();
				
				} else {
					
					kickTimer.resetStopper();
					
					if(getPlayer().age % 4 == 0) {
						entity.getVelocity().y = 0;
						entity.setPosition(entity.getX(), yLevel, entity.getZ());
					} else {
						entity.getVelocity().y =  -0.1;
					}
					

				}
					
				if((getMC().options.keyForward.isPressed() ||
						getMC().options.keyBack.isPressed() ||
						getMC().options.keyRight.isPressed() ||
						getMC().options.keyLeft.isPressed()) &&
						!Modules.FREECAM.getStatus()){
					entity.getVelocity().x = MathUtil.calculateX(getPlayer().getYaw(), hspeed.getValue());
					entity.getVelocity().z = MathUtil.calculateZ(getPlayer().getYaw(), hspeed.getValue());
				} else {
					entity.getVelocity().x = 0;
					entity.getVelocity().z = 0;
				}
			}
			return ActionResult.SUCCESS;
		});
	}
	
	@Listener
	public void onPacketIn() {
		PacketCallback.IN.register(packet -> {
			if(getStatus() && bypass.getValue() && boat != null) {
				if(packet instanceof VehicleMoveS2CPacket) {
					getWorld().sendPacket(PlayerInteractEntityC2SPacket.interact(boat, false, Hand.MAIN_HAND));
				}
			}
			return ActionResult.SUCCESS;
		});
	}
	
	@Listener
	public void onPacketOut() {
		PacketCallback.IN.register(packet -> {
			if(getStatus() && boat != null) {
				if(packet instanceof PlayerInputC2SPacket) {
					PlayerInputC2SPacket p = (PlayerInputC2SPacket) packet;
					p.sneaking = false;
					
				}
			}
			return ActionResult.SUCCESS;
		});
	}
}
