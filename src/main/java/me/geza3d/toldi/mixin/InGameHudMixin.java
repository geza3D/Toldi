package me.geza3d.toldi.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import me.geza3d.toldi.Toldi;
import me.geza3d.toldi.events.CameraSpoofCallback;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

@Mixin(InGameHud.class)
public class InGameHudMixin {

	@Inject(method = "getCameraPlayer", at = @At("HEAD"), cancellable = true)
	private void onGetCameraPlayer(CallbackInfoReturnable<PlayerEntity> info) {
		ActionResult result = CameraSpoofCallback.CAMERAPLAYER.invoker().spoof();
		
		if(result == ActionResult.FAIL) {
			info.setReturnValue(Toldi.CLIENT.player);
			info.cancel();
		}
	}
}
