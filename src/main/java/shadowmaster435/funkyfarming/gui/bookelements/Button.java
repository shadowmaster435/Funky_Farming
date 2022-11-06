package shadowmaster435.funkyfarming.gui.bookelements;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public abstract class Button {

    public Page dest;
    public int sizex;
    public int sizey;
    public int x;
    public int y;
    public String text;

    public static final Identifier id = new Identifier("funkyfarming:textures/gui/book/button.png");
    public static final Identifier idhovering = new Identifier("funkyfarming:textures/gui/book/button_hovering.png");

    public Button(int sizex, int sizey, int x, int y, String text, Page dest) {
        super();
        this.dest = dest;
    }

    public Button(int sizex, int sizey, int x, int y, ItemStack stack, Page dest) {
        super();
        this.dest = dest;
    }
    
    public Button(int sizex, int sizey, int x, int y, Identifier tex, Page dest) {
        super();
        this.dest = dest;
    }

    public void renderwithtext(int sizex, int sizey, int x, int y, String text, Page dest) {
        TextElement element = new TextElement(text,  (int) (x + (x - sizex * 0.5)),(int)(y + (y - sizey * 0.5)),(int) (x - sizex * 0.5));
        element.renderText(element.getText(), element.getX(), element.getY(), element.getEdgepos());
    }


    public void render(int x, int y, int sizex, int sizey, boolean hovering, Button button) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.enableTexture();

        //render with hovering texture if hovering
        if (hovering) {
            MinecraftClient.getInstance().getTextureManager().bindTexture(idhovering);
        } else {
            MinecraftClient.getInstance().getTextureManager().bindTexture(id);
        }

        if (button.text != null) {
            renderwithtext(x,y,sizex,sizey, button.text, button.dest);
        }
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
        bufferBuilder.vertex(x, y, 0).texture(0,0);
        bufferBuilder.vertex(x + sizex, y, 0).texture(1,0);
        bufferBuilder.vertex(x + sizex, y + sizey, 0).texture(1,1);
        bufferBuilder.vertex(x, y + sizey, 0).texture(0,1);
        tessellator.draw();
        RenderSystem.disableTexture();
    }

    public void renderbutton(Button button, int mousex, int mousey) {

        this.render(button.x, button.y, button.sizex, button.sizey, hovering(mousex, mousey), button);
    }

    public boolean hovering(int mousex, int mousey) {
        return (mousex <= this.x + this.sizex && mousey <= this.y + this.sizey && mousex >= this.x && mousey >= this.y);
    }


}
