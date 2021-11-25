package me.geza3d.toldi.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.geza3d.toldi.Toldi;
import net.minecraft.client.gui.screen.TitleScreen;

@Mixin(TitleScreen.class)
public class TryMixin {

	@Inject(method = "init()V", at = @At("HEAD"))
	public void tri(CallbackInfo info) {
		Toldi.LOGGER.info("Yes");
	}
}
