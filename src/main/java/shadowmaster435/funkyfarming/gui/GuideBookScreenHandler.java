package shadowmaster435.funkyfarming.gui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;

public class GuideBookScreenHandler extends ScreenHandler {

    public GuideBookScreenHandler(int syncId) {
        super(null, syncId);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int index) {
        return null;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return false;
    }
}
