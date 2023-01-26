package shadowmaster435.funkyfarming.init;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import ladysnake.satin.api.event.ShaderEffectRenderCallback;
import ladysnake.satin.api.managed.ManagedCoreShader;
import ladysnake.satin.api.managed.ManagedFramebuffer;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import ladysnake.satin.api.managed.uniform.*;
import ladysnake.satin.api.util.RenderLayerHelper;
import ladysnake.satin.mixin.client.render.RenderLayerMixin;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.block.entity.EndPortalBlockEntityRenderer;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL;
import shadowmaster435.funkyfarming.block.entity.renderer.GatewayRenderer;

public class FFShaders {
    public static int ticks;

    public static final ManagedCoreShader gateway = ShaderEffectManager.getInstance().manageCoreShader(new Identifier("funkyfarming", "rainbow"));

    public static final Uniform1f uniformSTime = gateway.findUniform1f("GameTime");
    public static final UniformMat4 gatewaymodelmat = gateway.findUniformMat4("ModelViewMat");
    public static final UniformMat4 gatewayprojmat = gateway.findUniformMat4("ProjMat");
    public static final Uniform4f gatewaytextproj = gateway.findUniform4f("texProj0");
    public static final Uniform3f gatewaypos = gateway.findUniform3f("Position");
    public static final SamplerUniform gatewaybg = gateway.findSampler("Sampler0");
    public static final SamplerUniform gatewayextra = gateway.findSampler("Sampler1");



    public static void initClient() {

        ClientTickEvents.END_CLIENT_TICK.register(client -> ticks++);

    }

}
