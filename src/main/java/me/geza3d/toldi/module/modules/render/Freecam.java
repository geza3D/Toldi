package me.geza3d.toldi.module.modules.render;

import me.geza3d.toldi.events.BlockOutlineCallback;
import me.geza3d.toldi.events.EntityCallback;
import me.geza3d.toldi.events.EntityChangeLookDirectionCallback;
import me.geza3d.toldi.events.HudCallback;
import me.geza3d.toldi.events.JoinLeaveCallback;
import me.geza3d.toldi.events.PacketCallback;
import me.geza3d.toldi.events.RenderCallback;
import me.geza3d.toldi.module.EnumModuleType;
import me.geza3d.toldi.module.ToldiModule;
import me.geza3d.toldi.module.settings.NumberSetting.DoubleSetting;
import me.geza3d.toldi.util.MathUtil;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.entity.Entity.RemovalReason;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Vec3d;

@ToldiModule.Type(EnumModuleType.RENDER)
public class Freecam extends ToldiModule{

	DoubleSetting speed = new DoubleSetting(this, "speed", 3d, 0d, 5d);
	
	//Don't ask why, I was lazy...
	VillagerEntity cam;
	
	@Override
	public void enable() {
		cam = new VillagerEntity(EntityType.VILLAGER ,getWorld());
		cam.noClip = true;
		cam.setNoGravity(true);
		cam.setInvisible(true);
		cam.setPosition(getPlayer().getPos());
		cam.setYaw(getPlayer().getHeadYaw());
		cam.setHeadYaw(getPlayer().getHeadYaw());
		cam.setPitch(getPlayer().getPitch());
		cam.setId(-1337);
		getWorld().addEntity(-1337, cam);
		getMC().setCameraEntity(cam);
		getMC().chunkCullingEnabled = false;
		super.enable();
	}
	
	@Override
	public void disable() {
		super.disable();
		getMC().setCameraEntity(getPlayer());
		getWorld().removeEntity(cam.getId(), RemovalReason.DISCARDED);
		getMC().chunkCullingEnabled = true;
	} 
	
	@Listener
	public void onDisconnect() {
		JoinLeaveCallback.LEAVE.register(()-> {
			if(getRawStatus()) disable();
			return ActionResult.SUCCESS;
		});
	}
	
	@Listener
	public void onEntityTick() {
		EntityCallback.TICK.register(entity -> {
			if(getStatus() && entity == cam) {
				if(getMC().options.keyJump.isPressed()) {
					
					cam.getVelocity().y = speed.getValue();
					
				} else if(getMC().options.keySneak.isPressed()){
					
					cam.getVelocity().y = -speed.getValue();
				
				} else {
					
					cam.getVelocity().y = 0;
					
				}
					
				if(getMC().options.keyForward.isPressed() ||
						getMC().options.keyBack.isPressed() ||
						getMC().options.keyRight.isPressed() ||
						getMC().options.keyLeft.isPressed()){
					
					cam.getVelocity().x = MathUtil.calculateX(cam.getYaw(), speed.getValue());
					
					cam.getVelocity().z = MathUtil.calculateZ(cam.getYaw(), speed.getValue());
					
				} else {
					
					cam.getVelocity().x = 0;
					
					cam.getVelocity().z = 0;
					
				}
				
				cam.move(MovementType.SELF, cam.getVelocity());
			}

			return ActionResult.SUCCESS;
		});
	}
	
	@Listener
	public void onStopTicking() {
		EntityCallback.STOP_TICKING.register((entity)-> {
			if(getStatus() && entity == cam) {
				return ActionResult.FAIL;
			}
			return ActionResult.SUCCESS;
		});
	}
	
	@Listener
	public void onChangeLookDirection() {
		EntityChangeLookDirectionCallback.EVENT.register((entity,x,y) -> {
			if(getStatus() && entity == getPlayer()) {
				cam.changeLookDirection(x, y);
				cam.setHeadYaw(cam.getYaw());
				return ActionResult.FAIL;
			}
			return ActionResult.SUCCESS;
		});
	}
	
	@Listener
	public void onRenderLast() {
		RenderCallback.ENTITIES.register((matrices, tickDelta, camera) -> {
			if(getStatus()) {
				Vec3d vec3d = camera.getPos();
				double x = vec3d.getX();
				double y = vec3d.getY();
				double z = vec3d.getZ();
				Immediate immediate = getMC().worldRenderer.bufferBuilders.getEntityVertexConsumers();
				getMC().worldRenderer.renderEntity(getPlayer(), x, y, z, tickDelta, matrices, immediate);
			}
			return ActionResult.SUCCESS;
		});
	}
	
	@Listener
	public void onGetCameraPlayer() {
		HudCallback.CAMERAPLAYER.register(()-> {
			if(getStatus()) {
				return ActionResult.FAIL;
			}
			return ActionResult.SUCCESS;
		});
	}
	
	@Listener
	public void onBlockOutline() {
		BlockOutlineCallback.EVENT.register(() -> {
			if(getStatus()) {
				return ActionResult.FAIL;
			}
			return ActionResult.SUCCESS;
		});
	}
	
	@Listener
	public void onPacketOut() {
		PacketCallback.OUT.register(packet -> {
			if(getStatus()) {
				if(packet instanceof PlayerInteractEntityC2SPacket) {
					PlayerInteractEntityC2SPacket p = (PlayerInteractEntityC2SPacket) packet;
					if(p.entityId == getPlayer().getId()) {
						return ActionResult.FAIL;
					}
				}
			}
			return ActionResult.SUCCESS;
		});
	}
}
