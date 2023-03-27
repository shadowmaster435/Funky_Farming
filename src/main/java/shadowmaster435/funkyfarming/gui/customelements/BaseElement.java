package shadowmaster435.funkyfarming.gui.customelements;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.joml.Vector2i;
import org.joml.Vector4i;
import shadowmaster435.funkyfarming.gui.util.EZScreenHandler;
import shadowmaster435.funkyfarming.rendering.RenderUtil;

import java.util.ArrayList;
import java.util.List;

public class BaseElement<T extends EZScreenHandler<T>>  {
    public EZScreenHandler<T> handler;


    public int x;

    public int y;

    public int width;

    public int height;

    public Identifier filePath;

    public Vector4i refPosSize;

    public Vector4i posSize;

    public List<BaseElement<T>> elements = new ArrayList<>();



    public Screen screen;


    public BaseElement(Vector4i posSize, Vector4i refPosSize, Identifier filePath, EZScreenHandler<T> handler, Screen screen) {
        this.handler = handler;
        this.screen = screen;
        this.x = posSize.x();
        this.y = posSize.y();
        this.width = posSize.x + posSize.z;
        this.height = posSize.w + posSize.w;
        this.filePath = filePath;
        this.refPosSize = refPosSize;
        this.posSize = posSize;
    }


    public BaseElement(Vector2i pos, Identifier filePath, EZScreenHandler<T> handler, Screen screen) {
        this.handler = handler;
        this.screen = screen;
        this.x = pos.x();
        this.y = pos.y();
        this.filePath = filePath;
    }
    public BaseElement(Vector2i pos, Identifier filePath, EZScreenHandler<T> handler) {
        this.handler = handler;
        this.x = pos.x();
        this.y = pos.y();
        this.filePath = filePath;
    }

    public Screen getScreen() {
        return screen;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public Identifier getFilePath() {
        return filePath;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }



    public boolean hovering(int mouseX, int mouseY) {
        return (mouseX >= this.x && mouseX <= this.width && mouseY >= this.y && mouseY <= this.height);
    }



    public void render(MatrixStack matrices, int mouseX, int mouseY, boolean clicked) {
        RenderUtil.guiTextureQuad(this.posSize, new Vector4i(this.refPosSize.x, this.refPosSize.y + (this.refPosSize.w), this.refPosSize.z, this.refPosSize.w), this.filePath, matrices, this.screen);
    }

    public void render(MatrixStack matrices) {
        RenderUtil.guiTextureQuad(this.posSize, new Vector4i(this.refPosSize.x, this.refPosSize.y + (this.refPosSize.w), this.refPosSize.z, this.refPosSize.w), this.filePath, matrices, this.screen);
    }




}
