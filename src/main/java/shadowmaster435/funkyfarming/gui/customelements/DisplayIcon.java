package shadowmaster435.funkyfarming.gui.customelements;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import org.joml.Vector2i;

public class DisplayIcon {

    private ItemStack stack;


    private Vector2i pos;



    public DisplayIcon(ItemStack stack, Vector2i pos) {
        this.pos = pos;
        this.stack = stack;
    }

    public ItemStack getStack() {
        return stack;
    }

    public Vector2i getPos() {
        return pos;
    }
    

    public void render() {
        MinecraftClient.getInstance().getItemRenderer().renderInGui(this.stack, this.pos.x, this.pos.y );
    }

}
