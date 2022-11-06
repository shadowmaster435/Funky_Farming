package shadowmaster435.funkyfarming.util;

import net.minecraft.util.Identifier;
import shadowmaster435.funkyfarming.gui.bookelements.Page;
import shadowmaster435.funkyfarming.gui.bookelements.TextElement;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class GuidePages {

    public static final ArrayList<Page> pages = new ArrayList<>();

    public static void FrontPage() {
        ArrayList<TextElement> textElements = new ArrayList<>();
        textElements.add(new TextElement("this is a test string", 30, 60, 1000));
        textElements.add(new TextElement("this is a test string but for multiple lines", 120, 60, 21));
        pages.add(new Page(0, null, null, textElements, new Identifier("funkyfarming:frontpage")));
    }

}
