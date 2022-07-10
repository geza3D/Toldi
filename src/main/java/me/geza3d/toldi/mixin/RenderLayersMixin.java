package me.geza3d.toldi.mixin;

import static me.geza3d.toldi.util.RenderUtil.*;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.item.ItemStack;

@Mixin(RenderLayers.class)
public class RenderLayersMixin {

	@Inject(method = "getItemLayer", at = @At("HEAD"), cancellable = true)
	private static void getItemLayer(ItemStack stack, boolean direct, CallbackInfoReturnable<RenderLayer> info) {
        if(itemEntityTranslucentCull != null) {
        	info.setReturnValue(itemEntityTranslucentCull);
        	info.cancel();
        }
    }
}
