package me.geza3d.toldi.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.geza3d.toldi.events.KeyCallback;
import net.minecraft.client.Keyboard;
import net.minecraft.util.ActionResult;

@Mixin(Keyboard.class)
public class KeyboardMixin {

	@Inject(method = "onKey", at = @At("HEAD"), cancellable = true)
	public void onKey(long window, int key, int scancode, int action, int modifiers, CallbackInfo info) {
		if(KeyCallback.EVENT.invoker().press(key, action) == ActionResult.FAIL) {
			info.cancel();
		}
	}
}
