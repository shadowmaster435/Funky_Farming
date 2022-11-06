package shadowmaster435.funkyfarming.gui.bookelements;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.Identifier;
import shadowmaster435.funkyfarming.util.GuidePages;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Objects;

public class Page {

    public int slotcount;
    public ArrayList<Button> buttons;
    public ArrayList<DisplaySlot> displays;
    public ArrayList<TextElement> textElements;

    public Identifier id;
    public Page(int slotcount, @Nullable ArrayList<Button> buttons, @Nullable ArrayList<DisplaySlot> displays, ArrayList<TextElement> textElements, Identifier id) {
        super();
        this.slotcount = slotcount;
        this.displays = displays;
        this.buttons = buttons;
        this.textElements = textElements;
        this.id = id;
    }

    public void load(Page dest, Identifier id) {
        if (dest.id == id) {

        }
    }

    public static void renderpage(PlayerInventory inventory, int mousex, int mousey) {
        for (Page page : GuidePages.pages) {
            if (Objects.equals(Objects.requireNonNull(inventory.getMainHandStack().getNbt()).getString("currentpage"), page.id.toString())) {
                for (TextElement textElement : page.textElements) {
                    textElement.renderTextElement(textElement);
                }
                for (Button button : page.buttons) {
                    button.renderbutton(button, mousex, mousey);
                }
                for (DisplaySlot displaySlot : page.displays) {
                    displaySlot.renderItem(displaySlot);
                }
            }
        }
    }

}
