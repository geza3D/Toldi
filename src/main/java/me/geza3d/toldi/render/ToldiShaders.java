package me.geza3d.toldi.render;

import java.io.IOException;

import me.geza3d.toldi.events.InitCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderPhase.Shader;
import net.minecraft.client.render.VertexFormats;

public class ToldiShaders {

	private static net.minecraft.client.render.Shader outline_alpha;
	
	public static final Shader OUTLINE_ALPHA = new Shader(ToldiShaders::getOutlineAlphaShader);
	
	public static void loadShader(MinecraftClient client) {
		InitCallback.EVENT.register(()-> {
			try {
				outline_alpha = new net.minecraft.client.render.Shader(client.getResourceManager(), "rendertype_outline_alpha", VertexFormats.POSITION_COLOR_TEXTURE);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
	
	public static net.minecraft.client.render.Shader getOutlineAlphaShader() {
		return outline_alpha;
	}
}
