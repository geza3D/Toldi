package me.geza3d.toldi.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.geza3d.toldi.events.EntityCallback;
import net.minecraft.entity.Entity;
import net.minecraft.util.ActionResult;

@Mixin(targets = "net.minecraft.client.world.ClientWorld$ClientEntityHandler")
public class ClientEntityHandlerMixin {

	@Inject(method = "stopTicking", at = @At("HEAD"), cancellable = true)
	public void onStopTicking(Entity entity, CallbackInfo info) {
		ActionResult result = EntityCallback.STOP_TICKING.invoker().tick(entity);
		
		if(result == ActionResult.FAIL) {
			info.cancel();
		}
	}
}
