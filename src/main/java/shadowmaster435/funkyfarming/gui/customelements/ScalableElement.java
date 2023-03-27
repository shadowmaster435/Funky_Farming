package shadowmaster435.funkyfarming.gui.customelements;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.joml.Vector2i;
import org.joml.Vector4i;
import shadowmaster435.funkyfarming.gui.util.EZScreenHandler;
import shadowmaster435.funkyfarming.rendering.RenderUtil;

public class ScalableElement<T extends EZScreenHandler<T>> extends BaseElement<T>{

    public int delta;
    public int speed;
    public int maxtime;

    private boolean open = false;

    private boolean animating = false;
    public Vector2i animStartOffset;

    public Vector2i animEndOffset;
    public ScalableElement(Vector4i posSize, Vector2i animStartOffset, Vector2i animEndOffset, Vector4i clickArea, EZScreenHandler<T> handler, Screen screen, int speed, int maxtime) {
        super(posSize, clickArea, new Identifier("funkyfarming:textures/gui/scalable_gui.png"), handler, screen);
        this.speed = speed;
        this.maxtime = maxtime;
        this.posSize = posSize;
        this.refPosSize = clickArea;
        this.filePath = new Identifier("funkyfarming:textures/gui/scalable_gui.png");
        this.handler = handler;
        this.screen = screen;
        this.animEndOffset = animStartOffset;
        this.animStartOffset = animEndOffset;
    }


    public boolean isOpen() {
        return this.open;
    }

    public float ease() {
        float step = (this.delta <= (maxtime / 2)) ? 2 * this.delta * this.delta : 2 * (this.delta - (this.maxtime / 2f)) * (this.maxtime -  (this.delta - (this.maxtime / 2f))) + (this.maxtime / 2f);
        return step / this.maxtime;
    }

    public boolean isAnimating() {
        return this.animating;
    }

    public void tick() {

    }

    public boolean clicked(int mouseX, int mouseY) {
        return (this.hovering(mouseX, mouseY) && MinecraftClient.getInstance().mouse.wasLeftButtonClicked());
    }
    @Override
    public void render(MatrixStack matrices) {
        int deltax = (int) Math.floor(animStartOffset.x + (animEndOffset.x * this.ease()));
        int deltay = (int) Math.floor(animStartOffset.y + (animEndOffset.y * this.ease()));
        
        // Bottom Left Corner
        RenderUtil.guiTextureQuad(new Vector4i(this.posSize.x, this.posSize.y, this.posSize.z + 4, this.posSize.w + 4), new Vector4i(0, 0, 0, 0), this.filePath, matrices, this.screen);

        // Sides
        RenderUtil.guiTextureQuad(new Vector4i(this.posSize.x, this.posSize.y, this.posSize.x + 1, this.posSize.y + deltay), new Vector4i(0, 5, 0, 0), this.filePath, matrices, this.screen);
        RenderUtil.guiTextureQuad(new Vector4i(this.posSize.x, this.posSize.y, this.posSize.x + 1 + deltax, this.posSize.y + 1), new Vector4i(5, 0, 0, 0), this.filePath, matrices, this.screen);
        RenderUtil.guiTextureQuad(new Vector4i(this.posSize.x - 4, this.posSize.y + deltay - 4, this.posSize.x - 3, (this.posSize.y - 3) + deltay), new Vector4i(0, 7, 0, 0), this.filePath, matrices, this.screen);
        RenderUtil.guiTextureQuad(new Vector4i(this.posSize.x - 4 + deltax, this.posSize.y - 4, (this.posSize.x - 3) + deltax, this.posSize.y), new Vector4i(7, 0, 0, 0), this.filePath, matrices, this.screen);

        // Other Corners
        RenderUtil.guiTextureQuad(new Vector4i(this.posSize.x + deltax, this.posSize.y, this.posSize.x + 4, this.posSize.y + 4 + deltay), new Vector4i(0, 7, 0, 0), this.filePath, matrices, this.screen);
        RenderUtil.guiTextureQuad(new Vector4i(this.posSize.x, this.posSize.y + deltay, this.posSize.x + 4 + deltax, this.posSize.y + 4), new Vector4i(7, 0, 0, 0), this.filePath, matrices, this.screen);
        RenderUtil.guiTextureQuad(new Vector4i(this.posSize.x + 4 + deltax, this.posSize.y + 4 + deltay, this.posSize.x + 8 + deltax, this.posSize.y + 8 + deltay), new Vector4i(7, 7, 0, 0), this.filePath, matrices, this.screen);

        // Center
        RenderUtil.guiTextureQuad(new Vector4i(this.posSize.x + 4, this.posSize.y + 4, this.posSize.x + 5 + deltax, this.posSize.y + 5 + deltay), new Vector4i(1, 1, 0, 0), this.filePath, matrices, this.screen);

    }
}
