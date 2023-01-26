package shadowmaster435.funkyfarming.mixin;


import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import shadowmaster435.funkyfarming.gravity.GravitySphere;
import shadowmaster435.funkyfarming.util.RenderUtil;

import java.util.Objects;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow private Vec3d pos;
    @Shadow private Vec3d velocity;

    @Shadow public abstract boolean isOnGround();

    @Inject(method = "setVelocity(Lnet/minecraft/util/math/Vec3d;)V", at = @At("TAIL"))
    public void render(Vec3d velocity, CallbackInfo ci) {
        for (GravitySphere sphere : GravitySphere.spheres) {
            if (velocity != null) {
                this.velocity = sphere.getVelocity(this.pos, this.velocity, this.isOnGround());
            } else {
                this.velocity = velocity;
            }
        }

    }

}
