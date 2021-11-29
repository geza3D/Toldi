package me.geza3d.toldi.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.geza3d.toldi.events.EntityTickCallback;
import net.minecraft.entity.Entity;

@Mixin(Entity.class)
public class EntityMixin {

	@Inject(method = "tick()V", at = @At("HEAD"))
	public void onTick(CallbackInfo info) {
		EntityTickCallback.TICK.invoker().tick((Entity) (Object) this);
	}
}
