package shadowmaster435.funkyfarming.rendering;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import shadowmaster435.funkyfarming.util.ScrollableList;

import java.util.ArrayList;

public class SpecialVertex {

    private Vector4f rgba;
    private Vector3f pos;
    public SpecialVertex(Vector3f pos, Vector4f rgba) {
        this.rgba = rgba;
        this.pos = pos;
    }

    public void setRgba(Vector4f rgba) {
        this.rgba = rgba;
    }

    public void setPos(Vector3f pos) {
        this.pos = pos;
    }

    public Vector4f getRgba() {
        return rgba;
    }

    public Vector3f getPos() {
        return pos;
    }

    public static void render(ArrayList<SpecialVertex> vertices, MatrixStack matrices, float tickDelta, VertexConsumerProvider provider, RenderLayer layer) {
        VertexConsumer consumer = provider.getBuffer(layer);
        Matrix4f mat4 = matrices.peek().getPositionMatrix();
        Vector3f prevPos = vertices.get(0).pos;
        for (SpecialVertex vertex : vertices) {
            Vector3f dot = prevPos.normalize(vertex.pos);
            if (vertices.indexOf(vertex) != 0) {
                consumer.vertex(mat4, vertex.pos.x, vertex.pos.y, vertex.pos.z).color(vertex.rgba.x, vertex.rgba.y, vertex.rgba.z, vertex.rgba.w).normal(matrices.peek().getNormalMatrix(), dot.x, dot.y, dot.z);
            } else {
                consumer.vertex(mat4, vertex.pos.x, vertex.pos.y, vertex.pos.z).color(vertex.rgba.x, vertex.rgba.y, vertex.rgba.z, vertex.rgba.w).normal(matrices.peek().getNormalMatrix(), vertex.pos.x, vertex.pos.y, vertex.pos.z);

            }
            prevPos = vertex.pos;
        }
    }
    public static void render(ScrollableList<SpecialVertex> vertices, MatrixStack matrices, float tickDelta, VertexConsumerProvider provider, RenderLayer layer) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder consumer = tessellator.getBuffer();
        VertexConsumer consumer1 = provider.getBuffer(RenderLayer.LINE_STRIP);
        Matrix4f mat4 = matrices.peek().getPositionMatrix();
        Vector3f prevPos = vertices.get(0).pos;
        RenderSystem.setShader(GameRenderer::getRenderTypeLinesProgram);
        RenderSystem.lineWidth(4f);
        ArrayList<SpecialVertex> list = vertices.asList();
        consumer.begin(VertexFormat.DrawMode.LINE_STRIP, VertexFormats.POSITION_COLOR);
        matrices.push();
        for (SpecialVertex vertex : list) {
            Vector3f dot = prevPos.normalize(vertex.pos);
            mat4.translate(vertex.pos.x, vertex.pos.y,vertex.pos.z);
            consumer.vertex(mat4, vertex.pos.x, vertex.pos.y,vertex.pos.z).color(vertex.rgba.x, vertex.rgba.y, vertex.rgba.z, 1f).normal(prevPos.x,prevPos.y,prevPos.z).next();
            prevPos = vertex.pos;
        }
        matrices.pop();
        tessellator.draw();
    }
}
