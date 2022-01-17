package me.geza3d.toldi.util;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.entity.vehicle.BoatEntity;

public class CombatUtil {

	public static boolean attackPassive;
	public static boolean attackNeutral;
	public static boolean attackAggressive;
	public static boolean attackInanimate;
	public static boolean attackPlayer;
	public static boolean raytrace;
	
	public static enum TargetMode{
		DISTANCE, HEALTH
	}
	
	public static Entity getTarget(ClientWorld world, ClientPlayerEntity player, double distance, TargetMode mode) {
		Entity target = null;
		float best = Float.MAX_VALUE;
		for(Entity entity : world.getEntities()) {
			if(entity == player) continue;
			if(getDistanceBetweenEntities(entity, player) > distance) continue;
			if(entity instanceof LivingEntity) {
				if(((LivingEntity) entity).getHealth() <= 0 || ((LivingEntity) entity).isDead()) {
					continue;
				}
			}
			boolean canBeAttacked = false;
			if(attackPassive) {
				if(entity instanceof PassiveEntity) {
					canBeAttacked = true;
				}
			}
			if(attackAggressive) {
				if(entity instanceof HostileEntity) {
					canBeAttacked = true;
				}
			}
			
			if(attackNeutral) {
				if(entity instanceof PiglinEntity ||
						entity instanceof IronGolemEntity ||
						entity instanceof EndermanEntity) {
					canBeAttacked = true;
				}
			}
			if(attackInanimate) {
				if(entity instanceof BoatEntity ||
						entity instanceof AbstractMinecartEntity) {
					canBeAttacked = true;
				}
			}
			if(attackPlayer) {
				if(entity instanceof PlayerEntity) {
					canBeAttacked = true;
				}
			}
			if(canBeAttacked) {
				float value = getValueOfEntity(entity, player, mode);
				if(value <= best) {
					target = entity;
					best = value;
				}
			}
		}
		return target;
	}
	
	private static float getValueOfEntity(Entity entity, ClientPlayerEntity player, TargetMode mode) {
		if(entity instanceof LivingEntity) {
			switch(mode) {
				case DISTANCE:
					return (float) getDistanceBetweenEntities(entity, player);
				case HEALTH:
					return ((LivingEntity)entity).getHealth() + ((LivingEntity)entity).getAbsorptionAmount();
			}
		}
		return Float.MAX_VALUE;
	}
	
	public static double getDistanceBetweenEntities(Entity entity1, Entity entity2) {
		double x = entity1.getX() - entity2.getX();
		double y = entity1.getY() - entity2.getY();
		double z = entity1.getZ() - entity2.getZ();
		return Math.sqrt(x*x + y*y + z*z);
	}
}
