package shadowmaster435.funkyfarming.gui.customelements;

import net.fabricmc.loader.impl.lib.gson.JsonReader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import org.joml.Vector4i;
import shadowmaster435.funkyfarming.gui.util.EZScreenHandler;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;

public class GuiBlock<T extends EZScreenHandler<T>> extends BaseElement<T>{
    public GuiBlock(Vector4i posSize, Vector4i refPosSize, Identifier filePath, EZScreenHandler<T> handler, Screen screen) {
        super(posSize, refPosSize, filePath, handler, screen);
        this.filePath = filePath;
        this.screen = screen;
        this.handler = handler;
    }

    public void render() {

    }



}
