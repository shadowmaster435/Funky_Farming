package shadowmaster435.funkyfarming.gui.bookelements;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Style;

public class TextElement {

    public TextElement(String text, int x, int y, int edgepos) {
        super();
        this.renderText(text, x, y, edgepos);
    }

    public void renderText(String text, int x, int y, int width) {
        TextRenderer renderer = MinecraftClient.getInstance().textRenderer;
        renderer.drawTrimmed(StringVisitable.plain(text), x, y, width, 0);
    }

}
