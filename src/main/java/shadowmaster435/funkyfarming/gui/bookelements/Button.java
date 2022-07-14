package shadowmaster435.funkyfarming.gui.bookelements;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import shadowmaster435.funkyfarming.gui.bookelements.Page;

public abstract class Button {

    public final Identifier id = new Identifier("funkyfarming:textures/gui/book/button.png");

    public Button(int sizex, int sizey, int x, int y, String text, Page dest) {
    }

    public Button(int sizex, int sizey, int x, int y, ItemStack stack, Page dest) {
    }
    
    public Button(int sizex, int sizey, int x, int y, Identifier tex, Page dest) {
        
    }

    public void renderwithtext(int sizex, int sizey, int x, int y, String text, Page dest) {

    }

    public void renderbutton(int x, int y, int sizex, int sizez) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
    }

}
