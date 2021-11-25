package me.geza3d.toldi.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.geza3d.toldi.events.PacketCallback;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.util.ActionResult;

@Mixin(ClientConnection.class)
public class ClientConnectionMixin {

	@Inject(method = "handlePacket", at = @At("HEAD"), cancellable = true)
	private static void onPacketIn(Packet<?> packet, PacketListener listener, CallbackInfo info) {
		ActionResult result = PacketCallback.IN.invoker().packet(packet);
		
		if(result == ActionResult.FAIL) {
			info.cancel();
		}
	}
	
	@Inject(method = "send", at = @At("HEAD"), cancellable = true)
	private void onPacketOut(Packet<?> packet, CallbackInfo info) {
		ActionResult result = PacketCallback.OUT.invoker().packet(packet);
		
		if(result == ActionResult.FAIL) {
			info.cancel();
		}
	}
}
