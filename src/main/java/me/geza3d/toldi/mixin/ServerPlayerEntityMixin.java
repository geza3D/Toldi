package me.geza3d.toldi.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.geza3d.toldi.events.ServerPlayerEntityCallback;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {

	@Inject(method = "tick()V", at = @At("HEAD"), cancellable = true)
	public void onTick(CallbackInfo info) {
		ActionResult result = ServerPlayerEntityCallback.TICK.invoker().tick();
		
		if(result == ActionResult.FAIL) {
			info.cancel();
		}
	}
	
}
