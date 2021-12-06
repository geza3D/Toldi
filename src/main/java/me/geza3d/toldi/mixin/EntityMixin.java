package me.geza3d.toldi.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.geza3d.toldi.events.EntityCallback;
import me.geza3d.toldi.events.EntityChangeLookDirectionCallback;
import net.minecraft.entity.Entity;
import net.minecraft.util.ActionResult;

@Mixin(Entity.class)
public class EntityMixin {

	@Inject(method = "tick()V", at = @At("HEAD"))
	public void onTick(CallbackInfo info) {
		EntityCallback.TICK.invoker().tick((Entity) (Object) this);
	}
	
	@Inject(method = "changeLookDirection(DD)V", at = @At("HEAD"), cancellable = true)
	public void onChangeLookDirection(double x, double y, CallbackInfo info) {
		ActionResult result = EntityChangeLookDirectionCallback.EVENT.invoker().tick((Entity) (Object) this,x,y);
		
		if(result == ActionResult.FAIL) {
			info.cancel();
		}
	}
}
