package me.geza3d.toldi.util;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;

public class RotationUtil {

	public static int currentPriority = -1;
	
	public static float[] getRotationForVector(Vec3d vector, ClientPlayerEntity player) {
		Vec3d eyesPos = new Vec3d(player.getPos().x + player.getVelocity().x, 
				player.getEyeY() + player.getVelocity().y + 0.0784000015258789, 
				player.getPos().z + player.getVelocity().z);
		 
		double diffX = vector.x - eyesPos.x;
		double diffY = vector.y - eyesPos.y;
		double diffZ = vector.z - eyesPos.z;
		
		if(diffX == 0 && diffZ == 0) {
			diffX = 0.1; diffZ = 0.1;
		}
		 
		double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
		 
		float yaw = wrapYaw((float) -Math.toDegrees(Math.atan2(diffX, diffZ)));
		float pitch = wrapPitch((float) -(Math.toDegrees(Math.atan2(diffY, diffXZ))));

		
		float[] returned = {yaw, pitch};
		return returned;
	}
	
	public static float wrapYaw(float yaw) {
		if(yaw >= 180) {
			return -180 + (yaw % 180); 
		} else if(yaw < -180) {
			return 180 + (yaw % 180);
		}
		
		return yaw;
	}
	
	public static float wrapPitch(float pitch) {
		if(pitch >= 90) {	
			return 90 - (pitch % 90);
		} else if(pitch < -90){
			return -90 - (pitch % 90);	
		}
		 
		return pitch;
	}
}
