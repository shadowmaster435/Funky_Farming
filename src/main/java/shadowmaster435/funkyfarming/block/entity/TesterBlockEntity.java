package shadowmaster435.funkyfarming.block.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import shadowmaster435.funkyfarming.block.TesterBlock;
import shadowmaster435.funkyfarming.block.VoracePlant;
import shadowmaster435.funkyfarming.block.entity.renderer.TesterRenderer;
import shadowmaster435.funkyfarming.init.FFBlocks;
import shadowmaster435.funkyfarming.rendering.DynamicTexture;

public class TesterBlockEntity extends BlockEntity {

    private DynamicTexture dynamicTexture;


    public TesterBlockEntity(BlockPos pos, BlockState state) {
        super(FFBlocks.TESTER_BLOCK_ENTITY, pos, state);
    }

    public void initOnClient() {
        if (MinecraftClient.getInstance().isOnThread()) {
            this.dynamicTexture = DynamicTexture.create(16, 16, 20, 2);
        }
    }

    public DynamicTexture getDynamicTexture() {
        return this.dynamicTexture;
    }
    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Environment(EnvType.CLIENT)
    public void tickTex(DynamicTexture dynamicTexture) {
        dynamicTexture.tick();
    }
    public static void tick(World world, BlockPos pos, BlockState state, TesterBlockEntity be) {
    }
}
