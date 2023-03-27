package shadowmaster435.funkyfarming.mixin;


import net.minecraft.entity.passive.SheepEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import shadowmaster435.funkyfarming.block.entity.ShearuelliaEntity;

@Mixin(SheepEntity.class)
public class SheepEntityMixin {
    @Inject(at = @At("TAIL"), method = "onEatingGrass")
    public void eat(CallbackInfo ci) {
        ShearuelliaEntity.checkSheep = true;
    }
}
