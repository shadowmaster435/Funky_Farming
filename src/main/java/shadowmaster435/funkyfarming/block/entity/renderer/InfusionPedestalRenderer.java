package shadowmaster435.funkyfarming.block.entity.renderer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.noise.PerlinNoiseSampler;
import net.minecraft.util.math.random.Random;
import org.apache.commons.codec.DecoderException;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.joml.Vector3i;
import shadowmaster435.funkyfarming.animation.Animation;
import shadowmaster435.funkyfarming.animation.Easing;
import shadowmaster435.funkyfarming.animation.Keyframe;
import shadowmaster435.funkyfarming.block.entity.InfusionPedestalEntity;
import shadowmaster435.funkyfarming.rendering.DynamicTexture;
import shadowmaster435.funkyfarming.rendering.QuadGrid;

import java.util.Objects;

public class InfusionPedestalRenderer implements BlockEntityRenderer<InfusionPedestalEntity> {

    public int ticks = 0;


    @Override
    public void render(InfusionPedestalEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (QuadGrid.grids.size() == 0) {
            QuadGrid.grids.add(new QuadGrid(16));
        } else {

            double offset = Math.sin((Objects.requireNonNull(entity.getWorld()).getTime() + tickDelta) / 8.0);
            double offsetcos = Math.cos((Objects.requireNonNull(entity.getWorld()).getTime() + tickDelta) / 8.0);

            QuadGrid grid = QuadGrid.grids.get(0);
      //      grid.addQuadFromPos(new Vec3d(offset, 0 , offsetcos), new Vector3f(1f, 0, 0), 80);
            Random random = Random.create(1948924710);
            PerlinNoiseSampler sampler = new PerlinNoiseSampler(random);
          //  grid.tick();
            /*for (int x = 0; x < 16; ++x) {
                for (int y = 0; y < 16; ++y) {
                    float rgb = (float) sampler.sample((entity.getPos().getX() * 2 + x / 8f), entity.getWorld().getTime() / 16f,  (entity.getPos().getZ() * 2 + y / 8f));
                   *//* if (rgb < 0.05f && rgb >0) {
                        grid.addQuadFromPos(new Vec3d(x / 16f, 0, y / 16f), new Vector3f((float) Math.abs(Math.sin(rgb)), Math.abs(rgb), Math.abs(rgb)), 1);
                    }*//*
                    grid.addQuadFromPos(new Vec3d(x / 16f, 0, y / 16f), new Vector3f((float) (rgb * Math.abs(Math.sin(rgb))), (float) (rgb * Math.abs(Math.sin(rgb))) * 0.5f, (float) ((float) 0.5  * (rgb * Math.abs(Math.sin(rgb))))), 1);

                }
            }*/


            //  grid.renderBlockEntity(entity.getPos(), matrices, tickDelta, vertexConsumers);
/*            DynamicTexture dynamicTexture = new DynamicTexture(16, 16);
            try {
                dynamicTexture.renderQuad(matrices);
            } catch (DecoderException e) {
                throw new RuntimeException(e);
            }*/
        }
        try {
            if (entity.getWorld() != null && entity.infuserPos != null && entity.animating) {
                Random random = Random.create((entity.getPos().getX() + entity.getPos().getY() + entity.getPos().getZ()));
                int animOffset = random.nextBetween(-12000, 12000);
                float animTimer = (entity.animtimer + tickDelta) / 31;
                Vector3f centerPos = new Vector3f(0.5f, 1.55f, 0.5f);
                BlockPos sub = entity.getPos().subtract(entity.infuserPos);
                Vector3f endPos = new Vector3f(-sub.getX(), -sub.getY() + 2, -sub.getZ());
                Easing.Back easing = Easing.BACK_IN;
                easing.setOvershoot(2.5f);
                Animation animation = Keyframe.builder(centerPos, endPos, easing, 1).build();
                Vector3f animPos = animation.getCurrentPos(animTimer, 0);

                float offset = (float) (Math.sin((entity.getWorld().getTime() + tickDelta) / 8.0) / 16.0);
                ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
                ItemStack stack = entity.getItems().get(0);
                int lightAbove = WorldRenderer.getLightmapCoordinates(entity.getWorld(), entity.getPos().up());

                if (!stack.isEmpty()) {
                    matrices.push();

                    matrices.translate(animPos.x, animPos.y + offset, animPos.z);
                    matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees((offset * 6) * 25));
                    matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((entity.getWorld().getTime() + animOffset + tickDelta) * 4));
                    matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((offset * 6) * 25));
                    itemRenderer.renderItem(stack, ModelTransformation.Mode.GROUND, lightAbove, overlay, matrices, vertexConsumers, 0);
                    matrices.pop();

                }
            } else {
                ItemStack stack = entity.getItems().get(0);

                if (entity.getWorld() != null && !stack.isEmpty()) {
                    Random random = Random.create((entity.getPos().getX() + entity.getPos().getY() + entity.getPos().getZ()));
                    int animOffset = random.nextBetween(-12000, 12000);
                    float offset = (float) (Math.sin((entity.getWorld().getTime() + animOffset + tickDelta) / 8.0) / 16.0);
                    ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
                    int lightAbove = WorldRenderer.getLightmapCoordinates(entity.getWorld(), entity.getPos().up());

                    matrices.push();

                    matrices.translate(0.5f, offset + 1.55f, 0.5f);
                    matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees((offset * 6) * 25));
                    matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees((entity.getWorld().getTime() + animOffset + tickDelta) * 4));
                    matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((offset * 6) * 25));
                    itemRenderer.renderItem(stack, ModelTransformation.Mode.GROUND, lightAbove, overlay, matrices, vertexConsumers, 0);
                    matrices.pop();
                }
            }
        } catch (Exception ignored) {

        }

    }
}
