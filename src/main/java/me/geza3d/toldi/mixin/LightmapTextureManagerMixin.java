package me.geza3d.toldi.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import me.geza3d.toldi.events.BrightnessCallback;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.util.ActionResult;

@Mixin(LightmapTextureManager.class)
public class LightmapTextureManagerMixin {

	@ModifyVariable(method = "update", at = @At(value = "LOAD"), name = "m")
	public float getGamma(float gamma) {
		if(BrightnessCallback.EVENT.invoker().spoof() == ActionResult.FAIL) return 1000f;
		return gamma;
	}
}
