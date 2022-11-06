package shadowmaster435.funkyfarming.gui.bookelements;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.item.ItemStack;

public abstract class DisplaySlot  {
    public boolean isbutton = false;
    public int x = 0;
    public int y = 0;

    public Page dest = null;
    public ItemStack itemStack = null;



    public DisplaySlot(int x, int y, boolean isbutton, Page dest, ItemStack itemStack) {
        super();
        this.x = x;
        this.y = y;
        this.dest = dest;
        this.itemStack = itemStack;
        this.isbutton = isbutton;

    }

    public DisplaySlot(int x, int y, ItemStack itemStack) {
        super();

        this.x = x;
        this.y = y;
        this.itemStack = itemStack;
    }

    public void render(ItemStack stack, int x, int y) {
        ItemRenderer renderer = MinecraftClient.getInstance().getItemRenderer();
        renderer.renderGuiItemIcon(stack, x, y);
    }

    public void renderItem(DisplaySlot displaySlot) {
        render(displaySlot.itemStack, displaySlot.x, displaySlot.y);
    }

}
