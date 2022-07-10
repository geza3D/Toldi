package me.geza3d.toldi.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import static me.geza3d.toldi.util.RenderUtil.*;

@Mixin(RenderLayer.class)
public class RenderLayerMixin {

	@Inject(method = "getEntityCutoutNoCull", at = @At("HEAD"), cancellable = true)
	private static void getEntityCutoutNoCull(Identifier texture, boolean affectsOutline, CallbackInfoReturnable<RenderLayer> info) {
        if(entityCutoutNoCullOverride != null) {
        	info.setReturnValue(entityCutoutNoCullOverride);
        	info.cancel();
        }
    }
	
	@Inject(method = "getOutline", at = @At("HEAD"), cancellable = true)
	private static void getOutline(Identifier texture, CallbackInfoReturnable<RenderLayer> info) {
        if(outlineOverride != null) {
        	info.setReturnValue(outlineOverride);
        	info.cancel();
        }
    }
	
	@Inject(method = "getEntityTranslucent", at = @At("HEAD"), cancellable = true)
	private static void getEntityTranslucent(Identifier texture, boolean affectsOutline, CallbackInfoReturnable<RenderLayer> info) {
        if(entityTranslucentOverride != null) {
        	info.setReturnValue(entityTranslucentOverride);
        	info.cancel();
        }
    }
	
	@Inject(method = "getItemEntityTranslucentCull", at = @At("HEAD"), cancellable = true)
	private static void getItemEntityTranslucentCull(Identifier texture, CallbackInfoReturnable<RenderLayer> info) {
        if(itemEntityTranslucentCull != null) {
        	info.setReturnValue(itemEntityTranslucentCull);
        	info.cancel();
        }
    }
}
