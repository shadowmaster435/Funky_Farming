package shadowmaster435.funkyfarming.mixin;


import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import shadowmaster435.funkyfarming.gravity.GravitySphere;

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
