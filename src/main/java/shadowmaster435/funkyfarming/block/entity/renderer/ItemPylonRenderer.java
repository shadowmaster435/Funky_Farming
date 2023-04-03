package shadowmaster435.funkyfarming.block.entity.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.joml.Vector3f;
import shadowmaster435.funkyfarming.block.Pylon;
import shadowmaster435.funkyfarming.block.entity.ItemPylonEntity;
import shadowmaster435.funkyfarming.init.FFBlocks;

public class ItemPylonRenderer implements BlockEntityRenderer<ItemPylonEntity> {
    public static final Identifier itemPylonBeam = new Identifier("funkyfarming:textures/misc/pylon_beam.png");

    public ItemPylonRenderer(BlockEntityRendererFactory.Context ctx) {

    }

    public static int maxSegments = 50;
    public float delta = 1;

    @Override
    public void render(ItemPylonEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getSolid());
        Vector3f startPosition = new Vector3f(0.5f, 0.9375f, 0.5f);
        Vector3f endPosition = new Vector3f(entity.getPos().getX(),entity.getPos().getY(),entity.getPos().getZ()).add(4,4,4);




        if (entity.getWorld() != null && entity.getWorld().getBlockState(entity.getPos()).getBlock() == FFBlocks.ITEM_PYLON) { // Extra Offset Start Position Calculation
            switch (entity.getWorld().getBlockState(entity.getPos()).get(Pylon.DIRECTION)) {
                case DOWN -> startPosition = new Vector3f(0.5f, 0.0625f, 0.5f);
                case SOUTH -> startPosition = new Vector3f(0.5f, 0.5f, 0.0625f);
                case NORTH -> startPosition = new Vector3f(0.5f, 0.5f, 0.9375f);
                case WEST -> startPosition = new Vector3f(0.9375f, 0.5f, 0.5f);
                case EAST -> startPosition = new Vector3f(0.0625f, 0.5f, 0.5f);
                default -> startPosition = new Vector3f(0.5f, 0.9375f, 0.5f);
            }
        }
        delta = delta + 0.0625f;


        MinecraftClient client = MinecraftClient.getInstance();
        assert client.player != null;
        assert client.cameraEntity != null;

        if (entity.getXlist().size() > 0) { // Crash Prevention

            for (int listIndex = 0; listIndex < entity.getXlist().size(); ++listIndex) {
                Vector3f extraendPositionOffset = new Vector3f(0.5f, 0.9375f, 0.5f);
                BlockPos eppos = new BlockPos(entity.getXlist().get(listIndex), entity.getYlist().get(listIndex), entity.getZlist().get(listIndex));

                // Extra Offset End Position Calculation
                if (entity.getWorld() != null && entity.getWorld().getBlockState(entity.getPos()).getBlock() == FFBlocks.ITEM_PYLON) {
                    switch (entity.getWorld().getBlockState(entity.getPos()).get(Pylon.DIRECTION)) {
                        case DOWN -> extraendPositionOffset = new Vector3f(0.5f, 0.0625f, 0.5f);
                        case SOUTH -> extraendPositionOffset = new Vector3f(0.5f, 0.5f, 0.0625f);
                        case NORTH -> extraendPositionOffset = new Vector3f(0.5f, 0.5f, 0.9375f);
                        case WEST -> extraendPositionOffset = new Vector3f(0.9375f, 0.5f, 0.5f);
                        case EAST -> extraendPositionOffset = new Vector3f(0.0625f, 0.5f, 0.5f);
                        default -> extraendPositionOffset = new Vector3f(0.5f, 0.9375f, 0.5f);
                    }
                }

               // Vector3f endPosition = new Vector3f(-(entity.getPos().getX() - entity.getXlist().get(listIndex) + (extraendPositionOffset.x * -0.1f)), -(entity.getPos().getY() - entity.getYlist().get(listIndex) + (extraendPositionOffset.y * 0.1f)), -(entity.getPos().getZ() - entity.getZlist().get(listIndex) + (extraendPositionOffset.z * -0.1f))).add(startPosition);

                
            }
        }
    }
}
