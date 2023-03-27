package shadowmaster435.funkyfarming.gui;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import shadowmaster435.funkyfarming.gui.util.EZScreen;

public class WorktableScreen extends EZScreen<WorktableScreenHandler> {

    public WorktableScreen(WorktableScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title, new Identifier("funkyfarming", "textures/gui/blank_gui.png"));
    }
    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        this.drawAll(matrices, this.getScreenHandler().elements, mouseX, mouseY);
    }
}
