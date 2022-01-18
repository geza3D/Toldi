package me.geza3d.toldi.handlers;

import me.geza3d.toldi.events.RenderEntityCallback;
import me.geza3d.toldi.events.SendMovementPacketsCallback;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.ActionResult;

public class RotationHandler {
	
	public static void initRotationHandler() {
		initPacketSpoofer();
		initRenderSpoofer();
	}
	
	private static Float rotationYaw = null;
	private static Float rotationPitch = null;
	private static Float realYaw = null;
	private static Float realPitch = null;
	private static int ticks = 0;
	
	public static void rotate(float yaw, float pitch, int ticks) {
		RotationHandler.rotationYaw = yaw;
		RotationHandler.rotationPitch = pitch;
		RotationHandler.ticks = ticks;
	}
	
	public static void rotate(float yaw, float pitch) {
		rotate(yaw, pitch, 1);
	}
	
	public static void resetTicks() {
		ticks = 0;
	}
	
	private static void initPacketSpoofer() {
		SendMovementPacketsCallback.PRE.register(player -> {
			if(rotationYaw != null && rotationPitch != null) {
				realYaw = player.getYaw();
				realPitch = player.getPitch();
				player.setYaw(rotationYaw);
				player.setPitch(rotationPitch);
			}
			return ActionResult.SUCCESS;
		});
		SendMovementPacketsCallback.POST.register(player -> {
			if(realYaw != null && realPitch != null) {
				player.setYaw(realYaw);
				player.setPitch(realPitch);
				if(ticks == 0) {
					rotationYaw = null;
					rotationPitch = null;
					realYaw = null;
					realPitch = null;
				} else {
					ticks--;
				}
			}
			return ActionResult.SUCCESS;
		});
	}
	
	private static Float yaw = null;
	private static Float pitch = null;
	private static Float prevYaw = null;
	private static Float prevPitch = null;
	
	private static void initRenderSpoofer() {
		RenderEntityCallback.PRE.register((dispatcher, entity, x, y, z, delta, matrices, consumer, light) ->{
			if(entity instanceof ClientPlayerEntity && rotationYaw != null && rotationPitch != null) {
				ClientPlayerEntity player = (ClientPlayerEntity) entity;
				yaw = player.getYaw();
				pitch = player.getPitch();
				prevYaw = player.prevHeadYaw;
				prevPitch = player.prevPitch;
				player.headYaw = rotationYaw;
				player.setYaw(rotationYaw);
				player.setPitch(rotationPitch);
				player.bodyYaw = rotationYaw;
				player.prevHeadYaw = rotationYaw;
				player.prevPitch = rotationPitch;
				player.prevBodyYaw = rotationYaw;
			}
			return ActionResult.SUCCESS;
		});
		RenderEntityCallback.POST.register((dispatcher, entity, x, y, z, delta, matrices, consumer, light) ->{
			if(entity instanceof ClientPlayerEntity && yaw != null && pitch != null && prevYaw != null && prevPitch != null) {
				ClientPlayerEntity player = (ClientPlayerEntity) entity;
				player.headYaw = yaw;
				player.setYaw(yaw);
				player.setPitch(pitch);
				player.bodyYaw = yaw;
				player.prevHeadYaw = prevYaw;
				player.prevPitch = prevPitch;
				player.prevBodyYaw = prevYaw;
				yaw = null;
				pitch = null;
				prevYaw = null;
				prevPitch = null;
			}
			return ActionResult.SUCCESS;
		});
	}
}
