package me.geza3d.toldi.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import me.geza3d.toldi.Toldi;
import me.geza3d.toldi.events.CameraSpoofCallback;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ActionResult;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

	@ModifyVariable(method = "shouldRenderBlockOutline", at = @At(value = "STORE", ordinal = 0))
	public Entity onBlockOutline(Entity entity) {
		if(CameraSpoofCallback.EVENT.invoker().spoof() == ActionResult.FAIL) {
			return Toldi.CLIENT.player;
		}
		return entity;
	}
}
