package shadowmaster435.funkyfarming.gui.bookelements;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Style;

public class TextElement {

    public String text;
    public int x;
    public int y;
    public int edgepos;


    public int getEdgepos() {
        return edgepos;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getText() {
        return text;
    }

    public TextElement(String text, int x, int y, int edgepos) {
        super();
        this.text = text;
        this.x = x;
        this.y = y;
        this.edgepos = edgepos;
    }


    public void renderText(String text, int x, int y, int width) {
        TextRenderer renderer = MinecraftClient.getInstance().textRenderer;
        renderer.drawTrimmed(StringVisitable.plain(text), x, y, width, 0);
    }

    public void renderTextElement(TextElement element) {
        this.renderText(element.getText(), element.getX(), element.getY(), element.getEdgepos());
    }
}
