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
import shadowmaster435.funkyfarming.util.BookUtil;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {

    @Inject(at = @At("HEAD"), method = "render")
    private void init(CallbackInfo info) {
        if (MinecraftClient.getInstance().player != null) {

            if (MinecraftClient.getInstance().mouse.wasRightButtonClicked()) {
                if (MinecraftClient.getInstance().player.getEquippedStack(EquipmentSlot.MAINHAND).getItem() == FFItems.GUIDE_BOOK) {
                    if (GuideBook.isopen) {
                        BookUtil.render(MinecraftClient.getInstance().player.getInventory());
                    }
                }
            }
        }
    }
}
