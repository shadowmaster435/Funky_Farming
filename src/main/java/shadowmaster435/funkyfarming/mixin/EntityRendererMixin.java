package shadowmaster435.funkyfarming.mixin;


import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.RotationAxis;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import shadowmaster435.funkyfarming.util.RenderUtil;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin {
    @Inject(method = "render", at = @At("HEAD"))
    public void render(Entity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
  /*      matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float) RenderUtil.cameraRot.y));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float) RenderUtil.cameraRot.z));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees((float) RenderUtil.cameraRot.x));
 */
    }

}
