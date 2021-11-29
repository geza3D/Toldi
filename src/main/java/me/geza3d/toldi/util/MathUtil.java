package me.geza3d.toldi.util;

import me.geza3d.toldi.Toldi;
import net.minecraft.entity.LivingEntity;

public class MathUtil {

	public static double getSideX(double hyp, float yaw) {
		return -Math.sin(Math.toRadians(yaw)) * hyp;
	}
	
	public static double getSideZ(double hyp, float yaw) {
		return Math.cos(Math.toRadians(yaw)) * hyp;	
	}
	
	public static double calculateX(LivingEntity e, double hyp) {
		return getSideX(hyp, shiftYawIntoDirection(e));
	}
	
	public static double calculateZ(LivingEntity e, double hyp) {
		return getSideZ(hyp, shiftYawIntoDirection(e));
	}
	
	public static double calculateX(float yaw, double hyp) {
		return getSideX(hyp, shiftYawIntoDirection(yaw));
	}
	
	public static double calculateZ(float yaw, double hyp) {
		return getSideZ(hyp, shiftYawIntoDirection(yaw));
	}
	
	public static float shiftYawIntoDirection(LivingEntity e) {
		float yaw = e.getHeadYaw();
		float forward = e.forwardSpeed;
		float strafe = e.sidewaysSpeed;
		if(forward == 0) {
			if(strafe == 0) {
				return 0;
			} else {
				if(strafe > 0) {
					yaw -= 90;
				} else {
					yaw += 90;
				}
			}
		} else {
			if(strafe == 0) {
				if(forward < 0) {
					yaw += 180;
				}
			} else {
				if(forward < 0) {
					if(strafe > 0) {
						yaw += 225;
					} else {
						yaw -= 225;
					}
				} else {
					if(strafe > 0) {
						yaw -= 45;
					} else {
						yaw += 45;
					}
				}
			}
		}
		return yaw;
	}
	
	public static float shiftYawIntoDirection(float yaw) {
		if(!Toldi.CLIENT.options.keyForward.isPressed() && !Toldi.CLIENT.options.keyBack.isPressed()) {
			if(!Toldi.CLIENT.options.keyRight.isPressed() && !Toldi.CLIENT.options.keyLeft.isPressed()) {
				return 0;
			} else {
				if(Toldi.CLIENT.options.keyLeft.isPressed()) {
					yaw -= 90;
				} else {
					yaw += 90;
				}
			}
		} else {
			if(!Toldi.CLIENT.options.keyRight.isPressed() && !Toldi.CLIENT.options.keyLeft.isPressed()) {
				if(Toldi.CLIENT.options.keyBack.isPressed()) {
					yaw += 180;
				}
			} else {
				if(Toldi.CLIENT.options.keyBack.isPressed()) {
					if(Toldi.CLIENT.options.keyLeft.isPressed()) {
						yaw += 225;
					} else {
						yaw -= 225;
					}
				} else {
					if(Toldi.CLIENT.options.keyLeft.isPressed()) {
						yaw -= 45;
					} else {
						yaw += 45;
					}
				}
			}
		}
		return yaw;
	}
}
