package shadowmaster435.funkyfarming.block.entity.renderer;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import shadowmaster435.funkyfarming.block.entity.RiftRushEntity;

import java.util.Vector;

public class RiftRushRenderer implements BlockEntityRenderer<RiftRushEntity> {

    @Override
    public void render(RiftRushEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        matrices.pop();
    }
}
