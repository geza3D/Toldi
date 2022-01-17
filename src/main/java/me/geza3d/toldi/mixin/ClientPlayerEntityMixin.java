package me.geza3d.toldi.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.geza3d.toldi.events.SendMovementPacketsCallback;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.ActionResult;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {

	@Inject(method = "sendMovementPackets", at = @At("HEAD"), cancellable = true)
	private void onSendMovementPacketsPre(CallbackInfo info) {
		ActionResult result = SendMovementPacketsCallback.PRE.invoker().sendPackets((ClientPlayerEntity)(Object)this);
		if(result == ActionResult.FAIL) info.cancel();
	}
	
	@Inject(method = "sendMovementPackets", at = @At("RETURN"))
	private void onSendMovementPacketsPost(CallbackInfo info) {
		SendMovementPacketsCallback.POST.invoker().sendPackets((ClientPlayerEntity)(Object)this);
	}
}
