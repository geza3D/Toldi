package me.geza3d.toldi.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import me.geza3d.toldi.events.BrightnessCallback;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

@Mixin(LightmapTextureManager.class)
public class LightmapTextureManagerMixin {

	@Inject(method = "getBrightness", at = @At(value = "HEAD"), cancellable = true)
	private void onGetBrightness(World world, int lightLevel, CallbackInfoReturnable<Float> info) {
		if(BrightnessCallback.EVENT.invoker().spoof() == ActionResult.FAIL) {
			info.setReturnValue(1000f);
			info.cancel();
		}
		
	}
}
