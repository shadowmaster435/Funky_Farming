package shadowmaster435.funkyfarming.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import shadowmaster435.funkyfarming.gravity.GravitySphere;
import shadowmaster435.funkyfarming.util.RenderUtil;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {



    @Inject(at = @At("HEAD"), method = "render")
    private void inject(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f positionMatrix, CallbackInfo info) {
        matrices.peek().getPositionMatrix().translate((float) (((Math.random() - 0.5) * 0.125) * RenderUtil.screenshakeamount),(float) ((Math.random() - 0.5) * RenderUtil.screenshakeamount) * 0.05f, (float) (((Math.random() - 0.5) * 0.125) * RenderUtil.screenshakeamount));

    }
}
