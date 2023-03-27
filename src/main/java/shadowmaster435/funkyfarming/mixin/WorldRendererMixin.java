package shadowmaster435.funkyfarming.mixin;

import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import shadowmaster435.funkyfarming.rendering.RenderUtil;
import shadowmaster435.funkyfarming.util.MiscUtil;
import shadowmaster435.funkyfarming.util.QuadGrid;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {


    @Inject(at = @At("TAIL"), method = "tick")

    public void tick(CallbackInfo ci) {
        try {
            QuadGrid.tickAll();
        } catch (Exception ignored) {

        }
    }

    @Inject(at = @At("TAIL"), method = "render")
    private void inject(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f positionMatrix, CallbackInfo info) {
        matrices.peek().getPositionMatrix().translate((float) (((Math.random() - 0.5) * 0.125) * RenderUtil.screenshakeamount),(float) ((Math.random() - 0.5) * RenderUtil.screenshakeamount) * 0.05f, (float) (((Math.random() - 0.5) * 0.125) * RenderUtil.screenshakeamount));

    }
}
