package shadowmaster435.funkyfarming.gui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import shadowmaster435.funkyfarming.init.FFScreens;

public class GuideBookScreenHandler extends ScreenHandler {

    public GuideBookScreenHandler(int syncId) {
        super(FFScreens.GUIDE_BOOK_SCREEN_HANDLER_SCREEN_HANDLER_TYPE, syncId);
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        return null;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return false;
    }
}
