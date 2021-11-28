package me.geza3d.toldi.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.geza3d.toldi.Toldi;
import me.geza3d.toldi.events.JoinLeaveCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
	
	@Inject(method = "disconnect(Lnet/minecraft/client/gui/screen/Screen;)V", at = @At("HEAD"))
	public void onDisconnection(Screen screen, CallbackInfo info) {
		if(Toldi.CLIENT.world != null) JoinLeaveCallback.LEAVE.invoker().handle();
	}
}
