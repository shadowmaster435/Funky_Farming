package shadowmaster435.funkyfarming.block.entity.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import org.joml.Vector3d;
import org.joml.Vector3f;
import shadowmaster435.funkyfarming.block.Pylon;
import shadowmaster435.funkyfarming.block.entity.PylonEntity;
import shadowmaster435.funkyfarming.init.FFBlocks;

public class PylonRenderer implements BlockEntityRenderer<PylonEntity> {

    public PylonRenderer(BlockEntityRendererFactory.Context ctx) {

    }

    public static int maxSegments = 50;

    @Override
    public void render(PylonEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.LINE_STRIP);
        Vector3f startPosition = new Vector3f(0.5f, 0.9375f, 0.5f);
        if (entity.getWorld() != null && entity.getWorld().getBlockState(entity.getPos()).getBlock() == FFBlocks.PYLON) { // Extra Offset Start Position Calculation
            switch (entity.getWorld().getBlockState(entity.getPos()).get(Pylon.DIRECTION)) {
                case DOWN -> startPosition = new Vector3f(0.5f, 0.0625f, 0.5f);
                case SOUTH -> startPosition = new Vector3f(0.5f, 0.5f, 0.0625f);
                case NORTH -> startPosition = new Vector3f(0.5f, 0.5f, 0.9375f);
                case WEST -> startPosition = new Vector3f(0.9375f, 0.5f, 0.5f);
                case EAST -> startPosition = new Vector3f(0.0625f, 0.5f, 0.5f);
                default -> startPosition = new Vector3f(0.5f, 0.9375f, 0.5f);
            }
        }

        RenderSystem.disableDepthTest();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        RenderSystem.disableTexture();
        RenderSystem.disableBlend();

        MinecraftClient client = MinecraftClient.getInstance();
        assert client.player != null;
        assert client.cameraEntity != null;

        if (entity.getXlist().size() > 0) { // Crash Prevention

            for (int listIndex = 0; listIndex < entity.getXlist().size(); ++listIndex) {
                Vector3f extraendPositionOffset = new Vector3f(0.5f, 0.9375f, 0.5f);
                BlockPos eppos = new BlockPos(entity.getXlist().get(listIndex), entity.getYlist().get(listIndex), entity.getZlist().get(listIndex));

                // Extra Offset End Position Calculation
                if (entity.getWorld() != null && entity.getWorld().getBlockState(entity.getPos()).getBlock() == FFBlocks.PYLON) {
                    switch (entity.getWorld().getBlockState(entity.getPos()).get(Pylon.DIRECTION)) {
                        case DOWN -> extraendPositionOffset = new Vector3f(0.5f, 0.0625f, 0.5f);
                        case SOUTH -> extraendPositionOffset = new Vector3f(0.5f, 0.5f, 0.0625f);
                        case NORTH -> extraendPositionOffset = new Vector3f(0.5f, 0.5f, 0.9375f);
                        case WEST -> extraendPositionOffset = new Vector3f(0.9375f, 0.5f, 0.5f);
                        case EAST -> extraendPositionOffset = new Vector3f(0.0625f, 0.5f, 0.5f);
                        default -> extraendPositionOffset = new Vector3f(0.5f, 0.9375f, 0.5f);
                    }
                }

                Vector3f endPosition = new Vector3f(-(entity.getPos().getX() - entity.getXlist().get(listIndex) + (extraendPositionOffset.x * -0.1f)), -(entity.getPos().getY() - entity.getYlist().get(listIndex) + (extraendPositionOffset.y * 0.1f)), -(entity.getPos().getZ() - entity.getZlist().get(listIndex) + (extraendPositionOffset.z * -0.1f))).add(startPosition);

                matrices.push();
                
                for (int segmentIndex = 0; segmentIndex < maxSegments; ++segmentIndex) {
                    // Random Vertex Pos Offset
                    float randomOffsetX = (float) ((Math.random() - 0.5) * 0.25);
                    float randomOffsetY = (float) ((Math.random() - 0.5) * 0.25);
                    float randomOffsetZ = (float) ((Math.random() - 0.5) * 0.25);
                    
                    int blue = (Math.random() > 0.5) ? 255 : 0; // Randomly Color Segment White

                    float normalX = (endPosition.x * (segmentIndex * 0.05f) + randomOffsetX) - (endPosition.x * ((segmentIndex + 1) * 0.05f) + randomOffsetX);
                    float normalY = (endPosition.y * (segmentIndex * 0.05f) + randomOffsetY) - (endPosition.y * ((segmentIndex + 1) * 0.05f) + randomOffsetY);
                    float normalZ = (endPosition.z * (segmentIndex * 0.05f) + randomOffsetZ) - (endPosition.z * ((segmentIndex + 1) * 0.05f) + randomOffsetZ);

                    // Lerped Vertex Pos Offset
                    float lerpedOffsetX = MathHelper.lerp(segmentIndex * 0.0205f, startPosition.x, endPosition.x);
                    float lerpedOffsetY = MathHelper.lerp(segmentIndex * 0.0205f, startPosition.y, endPosition.y);
                    float lerpedOffsetZ = MathHelper.lerp(segmentIndex * 0.0205f, startPosition.z, endPosition.z);

                    if (segmentIndex == 0) { // Render Invisible Vertex At Start To Fix Bug
                        vertexConsumer.vertex(matrices.peek().getPositionMatrix(), startPosition.x, startPosition.y, startPosition.z).color(255, 255, 255, 0).normal(matrices.peek().getNormalMatrix(), normalX, normalY, normalZ).next();
                    }


                    if (segmentIndex > 0 && segmentIndex < 49 ) { // Finally Render Bolt Segments
                        
                        vertexConsumer.vertex(matrices.peek().getPositionMatrix(), lerpedOffsetX + randomOffsetX, lerpedOffsetY + randomOffsetY, lerpedOffsetZ + randomOffsetZ).color(255, 255, blue, 255).normal(matrices.peek().getNormalMatrix(), normalX, normalY, normalZ).next();
                        vertexConsumer.vertex(matrices.peek().getPositionMatrix(), lerpedOffsetX + randomOffsetX, lerpedOffsetY + randomOffsetY, lerpedOffsetZ + randomOffsetZ).color(255, 255, 255 - blue, 255).normal(matrices.peek().getNormalMatrix(), normalX, normalY, normalZ).next();

                        } else {

                            vertexConsumer.vertex(matrices.peek().getPositionMatrix(), lerpedOffsetX, lerpedOffsetY, lerpedOffsetZ).color(255, 255, blue, 255).normal(matrices.peek().getNormalMatrix(), normalX, normalY, normalZ).next();
                            vertexConsumer.vertex(matrices.peek().getPositionMatrix(), lerpedOffsetX, lerpedOffsetY, lerpedOffsetZ).color(255, 255, 255 - blue, 255).normal(matrices.peek().getNormalMatrix(), normalX, normalY, normalZ).next();

                            if (segmentIndex == 49) {
                                // Render Invisible Vertex At End To Fix Bug
                                vertexConsumer.vertex(matrices.peek().getPositionMatrix(), endPosition.x, endPosition.y, endPosition.z).color(255, 255, 255, 0).normal(matrices.peek().getNormalMatrix(), normalX, normalY, normalZ).next();
                                
                                matrices.pop();

                            }
                        }
                    }
                
                RenderSystem.enableBlend();
                RenderSystem.enableTexture();
                RenderSystem.disableDepthTest();
                
            }
        }
    }
}
