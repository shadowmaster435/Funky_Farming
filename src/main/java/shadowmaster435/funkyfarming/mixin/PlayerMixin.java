package shadowmaster435.funkyfarming.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import shadowmaster435.funkyfarming.init.FFItems;
import shadowmaster435.funkyfarming.item.GuideBook;

@Mixin(PlayerEntity.class)
public abstract class PlayerMixin {
    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @Shadow public abstract PlayerInventory getInventory();

    @Shadow public abstract void slowMovement(BlockState state, Vec3d multiplier);

    @Shadow protected abstract Entity.MoveEffect getMoveEffect();

    @Shadow @Final public PlayerScreenHandler playerScreenHandler;

    @Inject(at = @At("TAIL"), method = "tick")
    private void init(CallbackInfo info) {
        if (MinecraftClient.getInstance().player != null) {

            if (MinecraftClient.getInstance().mouse.wasRightButtonClicked()) {
                if (getEquippedStack(EquipmentSlot.MAINHAND).getItem() == FFItems.GUIDE_BOOK) {

                    if (GuideBook.isopen) {
                        MinecraftClient.getInstance().mouse.unlockCursor();
                        MinecraftClient.getInstance().player.setMovementSpeed(0);
                    } else {
                        MinecraftClient.getInstance().mouse.lockCursor();
                    }
                }
            }
        }
    }
}
