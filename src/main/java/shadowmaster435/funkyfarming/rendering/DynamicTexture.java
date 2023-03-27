package shadowmaster435.funkyfarming.rendering;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.data.client.TextureMap;
import net.minecraft.util.math.noise.PerlinNoiseSampler;
import net.minecraft.util.math.random.Random;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;

import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;

public class DynamicTexture {

    private final int width;

    private final int height;

    private final BufferedImage image;

    public DynamicTexture(int width, int height) {
        this.width = width;
        this.height = height;
        this.image = this.create();
        GL30.glGenTextures();
    }

    private BufferedImage create() {
        return new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }

    public ByteBuffer createBuffer(int xsize, int ysize) throws DecoderException {
        ByteBuffer byteBuffer = BufferUtils.createByteBuffer(4 * xsize * ysize);
        Random random = Random.create(1948924710);

        PerlinNoiseSampler sampler = new PerlinNoiseSampler(random);

        for (int x = 0; x < xsize; ++x) {
            for (int y = 0; y < ysize; ++y) {
                assert MinecraftClient.getInstance().world != null;
                float rgb = (float) sampler.sample((x / 8f), MinecraftClient.getInstance().world.getTime() / 16f, (y / 8f));
                byteBuffer.put((byte) rgb);
                byteBuffer.put((byte) rgb);

                byteBuffer.put((byte) rgb);
                byteBuffer.put((byte) 1f);
            }
        }
        byteBuffer.flip();
            return byteBuffer;
    }

    public void renderQuad(MatrixStack stack) throws DecoderException {
        glEnable(GL_TEXTURE_2D);
    //    System.out.println(Arrays.toString(createBuffer(16, 16).array()));
        int id = glGenTextures();
        glGenTextures(createBuffer(16, 16).asIntBuffer());
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

//Setup wrap mode, i.e. how OpenGL will handle pixels outside of the expected range
//Note: GL_CLAMP_TO_EDGE is part of GL12
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, createBuffer(16, 16));
        glBindTexture(GL_TEXTURE_2D, id);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder builder = tessellator.getBuffer();
        RenderSystem.enableTexture();
        RenderSystem.enableBlend();
        RenderSystem.disableDepthTest();
        RenderSystem.setShaderTexture(0, id);
        stack.push();
        builder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
        builder.vertex(stack.peek().getPositionMatrix(), 0,  0, 0).texture(0,0).color(1f, 1f, 1f, 1f).next();
        builder.vertex(stack.peek().getPositionMatrix(), 1, 0, 0).texture(1,0).color(1f, 1f, 1f, 1f).next();
        builder.vertex(stack.peek().getPositionMatrix(), 1, 1, 0).texture(1,1).color(1f, 1f, 1f, 1f).next();
        builder.vertex(stack.peek().getPositionMatrix(), 0, 1, 0).texture(0,1).color(1f, 1f, 1f, 1f).next();
        tessellator.draw();

        stack.pop();
        RenderSystem.disableDepthTest();
        RenderSystem.disableTexture();
    }

    public int getARGB(int r, int g, int b, int a) {
        return (a<<24) | (r<<16) | (g<<8) | b;
    }
    public InputStream asStream(BufferedImage image) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, "png", os);
        return new ByteArrayInputStream(os.toByteArray());
    }



}
