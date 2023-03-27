package shadowmaster435.funkyfarming.block.entity.renderer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import org.joml.Vector3f;
import org.joml.Vector4f;
import shadowmaster435.funkyfarming.animation.Animation;
import shadowmaster435.funkyfarming.animation.Easing;
import shadowmaster435.funkyfarming.animation.Keyframe;
import shadowmaster435.funkyfarming.block.entity.InfuserEntity;
import shadowmaster435.funkyfarming.rendering.QuadStrip;
import shadowmaster435.funkyfarming.util.ScrollableList;

import java.util.ArrayList;
import java.util.List;

public class InfuserRenderer implements BlockEntityRenderer<InfuserEntity> {

    public ScrollableList<Vector3f> vector3fs = new ScrollableList<>(16, new Vector3f(0, 0, 0));
    public static final Identifier tex = new Identifier("funkyfarming:textures/block/yellow_fibergrass_top.png");

    @Override
    public void render(InfuserEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        VertexConsumer consumer = vertexConsumers.getBuffer(RenderLayer.getSolid());


       /* if (entity.getWorld() != null) {
            float offset = (float) (Math.sin(((entity.getWorld().getTime()) + tickDelta) / 8.0) / 4.0);

            vector3fs.addScroll(new Vector3f(0, offset, 0));



            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder consumer = tessellator.getBuffer();
            Matrix4f mat4 = matrices.peek().getPositionMatrix();
            RenderSystem.setShader(GameRenderer::getRenderTypeLinesProgram);
            RenderSystem.lineWidth(4f);

            consumer.begin(VertexFormat.DrawMode.LINE_STRIP, VertexFormats.POSITION_COLOR);
            Vector3f prevPos = new Vector3f(0,0,0);
            for (int i = 0; i < vector3fs.getSize() - 1; ++i) {
                int extrai = 0;
                if (!(i < vector3fs.getSize() - 2)) {
                    extrai = 1;
                }
                Vector3f vector3f = vector3fs.get(i);
                float k = (float)(vector3f.x - vector3fs.get(i + 1 - extrai).x);
                float l = (float)(vector3f.y - vector3fs.get(i + 1 - extrai).y);
                float m = (float)(vector3f.z - prevPos.z);
                float n = MathHelper.sqrt(k * k + l * l + m * m);
                k /= n;
                l /= n;
                m /= n;
                mat4.translate(0.0625f, 0, 0);
                consumer.vertex(mat4, vector3f.x, vector3f.y, vector3f.z).color(1f,1f,1f,1f).normal(matrices.peek().getNormalMatrix(),k,  l, m).next();


            }
            tessellator.draw();
        }*/
        if (entity.getWorld() != null) {

            float animTimer = 30 / (entity.animtimer + tickDelta);
            Vector3f centerPos = new Vector3f(0.5f,0.25f, 0.5f);
            Vector3f endPos = new Vector3f(0.5f,0.25f,0.5f);

            Animation animation = Keyframe.builder(centerPos, endPos, Easing.BACK_OUT, 30).build();

            Vector3f animPos = animation.getCurrentPos(animTimer, 0).sub(0.5f,-0.25f,0.5f);

            double offset = Math.sin((entity.getWorld().getTime() + tickDelta) / 8.0) / 4.0;
            matrices.push();
            ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
            ItemStack stack = entity.getItems().get(0);
            int lightAbove = WorldRenderer.getLightmapCoordinates(entity.getWorld(), entity.getPos().up());
            if (!stack.isEmpty()) {
               // matrices.translate(animPos.x, animPos.y + offset, animPos.z);
                matrices.translate(0.5f, 0.5f + (offset/ 4), 0.5f);
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees((entity.getWorld().getTime() + tickDelta) * 4));
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((entity.getWorld().getTime() + tickDelta) * 4));
                matrices.multiply(RotationAxis.NEGATIVE_Z.rotationDegrees((entity.getWorld().getTime() + tickDelta) * 0.5f));
                itemRenderer.renderItem(stack, ModelTransformation.Mode.GROUND, lightAbove, overlay, matrices, vertexConsumers, 0);
            }
            matrices.pop();

        }
        if (entity.getWorld() != null) {
            List<Vector3f> list = new ArrayList<>();
            list.add(new Vector3f(0,0,0));
            list.add(new Vector3f(0,0.25f,0));
            list.add(new Vector3f(0,0.5f,0));
            list.add(new Vector3f(0,0.75f,0));
            list.add(new Vector3f(0,1,0));

            QuadStrip strip = new QuadStrip(list, 0.0625f, 0.0625f);
            strip.render(matrices, tickDelta, entity.getWorld().getRandom(), tex, new Vector4f(0,0,1,1), 0, Direction.Axis.X);
        }
    }
}
