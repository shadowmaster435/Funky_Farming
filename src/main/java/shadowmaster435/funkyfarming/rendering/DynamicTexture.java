package shadowmaster435.funkyfarming.rendering;

import com.eliotlash.mclib.math.functions.limit.Min;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.impl.client.particle.FabricSpriteProviderImpl;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.texture.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.data.client.TextureMap;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.PerlinNoiseSampler;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.Objects;

import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;

@Environment(EnvType.CLIENT)
public class DynamicTexture {
    private final int width;
    private final int height;
    private final Identifier identifier;
    private final NativeImageBackedTexture image;
    private int tick = 1;
    private int maxTick = 2;
    private int tickDiv = 1;
    public DynamicTexture(int width, int height) {
        this.width = width;
        this.height = height;
        this.image = new NativeImageBackedTexture(width, height, false);
        this.identifier = MinecraftClient.getInstance().getTextureManager().registerDynamicTexture("noise", this.image);
    }
    public DynamicTexture(int width, int height, int maxTick, int tickDiv) {
        this.width = width;
        this.height = height;
        this.image = new NativeImageBackedTexture(width, height, true);
        this.identifier = MinecraftClient.getInstance().getTextureManager().registerDynamicTexture("noise", this.image);
        this.image.bindTexture();
        this.tickDiv = tickDiv;
        this.maxTick = maxTick;
    }
    public void setTick(int tick) {
        this.tick = tick;
    }
    public void tick() {
        if (this.tick < maxTick) {
            ++this.tick;
        } else {
            this.tick = 1;
        }
    }

    public void remove() {
        Objects.requireNonNull(this.image.getImage()).untrack();
    }
    public void modify() {
        PerlinNoiseSampler sampler = new PerlinNoiseSampler(Random.create(0));
        for (int x = 0; x < this.width; ++x) {
            for (int y = 0; y < this.height; ++y) {

                double rgb = Math.min(Math.abs(255 * sampler.sample(x * (1f / this.image.getImage().getWidth()),y * (1f / this.image.getImage().getHeight()), (float) this.tick / (float) this.tickDiv)), 255);
                int alp = (rgb < 0) ? 0 : 255;
                this.image.getImage().setColor(x, y, this.getARGB((int) rgb, (int) rgb, (int) rgb, alp));
            }
        }
    }
    private static final PerlinNoiseSampler sampler = new PerlinNoiseSampler(Random.create(324234));

    public void modify(BlockPos pos) {
        if (this.image.getImage() != null) {
            for (int x = 0; x < this.width; ++x) {
                for (int y = 0; y < this.height; ++y) {
                    double rgb = Math.min(Math.abs(255 * sampler.sample((float) x / this.image.getImage().getWidth() - 1 + pos.getX(), (float) y / this.image.getImage().getHeight() - 1 + pos.getY(), (float) this.tick / (float) this.tickDiv)), 255);
                    int alp = (rgb < 0) ? 0 : 255;
                    this.image.getImage().setColor(x, y, this.getARGB((int) rgb, (int) rgb, (int) rgb, alp));
                }
            }
        }
    }
    public static DynamicTexture create(int width, int height, int maxTick, int tickDiv) {
        return new DynamicTexture(width, height, maxTick, tickDiv);
    }

    public int getARGB(int r, int g, int b, int a) {
        return (a<<24) | (r<<16) | (g<<8) | b;
    }
    public void renderQuad(MatrixStack matrixStack) {
        this.image.upload();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder builder = tessellator.getBuffer();
        Matrix4f mat4 = matrixStack.peek().getPositionMatrix();
        RenderSystem.enableTexture();
        RenderSystem.setShader(GameRenderer::getPositionTexColorProgram);
        RenderSystem.setShaderTexture(0, this.identifier);
        builder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
        builder.vertex(mat4, 1,0,0).texture(1,0).color(255, 255, 255, 255).next();
        builder.vertex(mat4, 1,1,0).texture(1,1).color(255, 255, 255, 255).next();
        builder.vertex(mat4, 0,1,0).texture(0,1).color(255, 255, 255, 255).next();
        builder.vertex(mat4, 0,0,0).texture(0,0).color(255, 255, 255, 255).next();
        tessellator.draw();
        RenderSystem.disableTexture();
        this.modify();
    }
    public void renderQuad(MatrixStack matrixStack, BlockPos pos) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder builder = tessellator.getBuffer();
        Matrix4f mat4 = matrixStack.peek().getPositionMatrix();
        RenderSystem.enableTexture();
        RenderSystem.enableDepthTest();
        RenderSystem.setShader(GameRenderer::getPositionTexColorProgram);
        RenderSystem.setShaderTexture(0, this.identifier);
        builder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
        this.modify(pos);
        this.image.upload();
        builder.vertex(mat4, 1,0,0).texture(1,0).color(255, 255, 255, 255).next();
        builder.vertex(mat4, 1,1,0).texture(1,1).color(255, 255, 255, 255).next();
        builder.vertex(mat4, 0,1,0).texture(0,1).color(255, 255, 255, 255).next();
        builder.vertex(mat4, 0,0,0).texture(0,0).color(255, 255, 255, 255).next();
        tessellator.draw();
        RenderSystem.disableTexture();
    }
}
