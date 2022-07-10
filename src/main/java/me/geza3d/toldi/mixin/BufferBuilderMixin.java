package me.geza3d.toldi.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static me.geza3d.toldi.util.RenderUtil.*;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferVertexConsumer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexFormatElement;

@Mixin(BufferBuilder.class)
public abstract class BufferBuilderMixin {
	
	@Inject(method = "color", at = @At("HEAD"), cancellable = true)
	public void onVertexColor(int red, int green, int blue, int alpha, CallbackInfoReturnable<VertexConsumer> info) {
		if(hasRenderColor() && Thread.currentThread().getName().equals("Render thread")) {
			if(redOverride != null) red = redOverride;
			if(greenOverride != null) green = greenOverride;
			if(blueOverride != null) blue = blueOverride;
			if(alphaOverride != null) alpha = alphaOverride;
			BufferVertexConsumer This = (BufferVertexConsumer)(Object) this;
			VertexFormatElement vertexFormatElement = This.getCurrentElement();
	        if (vertexFormatElement.getType() != VertexFormatElement.Type.COLOR) {
	            info.setReturnValue(This);
	            info.cancel();
	        }
	        This.putByte(0, (byte)red);
	        This.putByte(1, (byte)green);
	        This.putByte(2, (byte)blue);
	        This.putByte(3, (byte)alpha);
	        This.nextElement();
	        info.setReturnValue(This);
            info.cancel();
		}
	}
}
