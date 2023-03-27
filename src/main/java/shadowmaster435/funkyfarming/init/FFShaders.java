package shadowmaster435.funkyfarming.init;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import ladysnake.satin.api.event.ShaderEffectRenderCallback;
import ladysnake.satin.api.managed.ManagedFramebuffer;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import ladysnake.satin.api.managed.uniform.Uniform1f;
import ladysnake.satin.api.util.RenderLayerHelper;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;

public class FFShaders {
    public static int ticks;


    public static final ManagedShaderEffect illusionEffect = ShaderEffectManager.getInstance().manage(new Identifier("funkyfarming", "shaders/post/illusion.json"),
            effect -> effect.setUniformValue("ColorModulate", 1.2f, 0.7f, 0.2f, 1.0f));
    public static final ManagedFramebuffer illusionBufferOut = illusionEffect.getTarget("final");
    public static final ManagedFramebuffer illusionBufferIn = illusionEffect.getTarget("swap");


    public static final RenderLayer blockRenderLayer = illusionBufferOut.getRenderLayer(RenderLayer.getLightning());
    private static final Uniform1f uniformSTime = illusionEffect.findUniform1f("STime");

    public static void initClient() {
        RenderLayer blockRenderLayer = illusionBufferOut.getRenderLayer(RenderLayer.getTranslucent());
        RenderLayerHelper.registerBlockRenderLayer(blockRenderLayer);

        ClientTickEvents.END_CLIENT_TICK.register(client -> ticks++);
        ClientTickEvents.END_CLIENT_TICK.register(client -> uniformSTime.set((ticks) * 0.05f));
        ShaderEffectRenderCallback.EVENT.register(tickDelta -> {
            MinecraftClient client = MinecraftClient.getInstance();
            illusionEffect.render(tickDelta);
            client.getFramebuffer().beginWrite(true);
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcFactor.ZERO, GlStateManager.DstFactor.ONE);
            illusionBufferOut.draw(client.getWindow().getFramebufferWidth(), client.getWindow().getFramebufferHeight(), false);
            illusionBufferOut.clear();
            client.getFramebuffer().beginWrite(true);
            RenderSystem.disableBlend();

            }
        );
    }

}
