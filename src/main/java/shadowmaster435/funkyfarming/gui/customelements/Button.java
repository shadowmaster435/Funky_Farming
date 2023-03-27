package shadowmaster435.funkyfarming.gui.customelements;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.joml.Vector3f;
import org.joml.Vector4i;
import shadowmaster435.funkyfarming.gui.util.EZScreenHandler;
import shadowmaster435.funkyfarming.rendering.RenderUtil;

public class Button<T extends EZScreenHandler<T>> extends BaseElement<T> {


    private final int bgType;

    private boolean indexType;

    private int delagateIndex;

    public Button(Vector4i posSize, Vector4i refPosSize, Identifier filePath, int bgType, int delagateIndex, boolean indexType, EZScreenHandler<T> handler, Screen screen) {
        super(posSize, refPosSize, filePath, handler, screen);
        this.handler = handler;
        this.screen = screen;
        this.bgType = bgType;
        this.x = posSize.x();
        this.y = posSize.y();
        this.width = posSize.x + posSize.z;
        this.height = posSize.w + posSize.w;
        this.filePath = filePath;
        this.refPosSize = refPosSize;
        this.posSize = posSize;
        this.indexType = indexType;
        this.delagateIndex = delagateIndex;
    }

    public int getBgType() {
        return bgType;
    }

    public int getDelagateIndex() {
        return delagateIndex;
    }

    public boolean clicked(int mouseX, int mouseY) {
        return (this.hovering(mouseX, mouseY) && MinecraftClient.getInstance().mouse.wasLeftButtonClicked());
    }

    public boolean isIndexType() {
        return this.indexType;
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, int delagateIndex) {
        this.renderButton(matrices, this.handler.getPropertyDelegate().get(delagateIndex));
        if (this.hovering(mouseX, mouseY)) {
            this.renderOutline(matrices);
        }
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, boolean clicked) {
        if (clicked) {
            // Render Pressed Texture
            this.renderButton(matrices, 1);
        } else {
            // Render Un-Pressed Texture
            this.renderButton(matrices, 0);
        }
        if (this.hovering(mouseX, mouseY)) {
            this.renderOutline(matrices);
        }
    }

    public void renderOutline(MatrixStack matrices) {
        RenderUtil.guiColorQuad(new Vector4i(this.x, this.y, this.x + 1, this.y + 1), new Vector3f(1f, 1f, 1f), matrices);
        RenderUtil.guiColorQuad(new Vector4i(this.x, this.height - 1, this.x + 1, this.height), new Vector3f(1f, 1f, 1f), matrices);
        RenderUtil.guiColorQuad(new Vector4i(this.width - 1, this.height - 1, this.width, this.height ), new Vector3f(1f, 1f, 1f), matrices);
        RenderUtil.guiColorQuad(new Vector4i(this.width - 1, this.y, this.width, this.y + 1), new Vector3f(1f, 1f, 1f), matrices);

        RenderUtil.guiColorQuad(new Vector4i(this.x, this.y + 1, this.x + 1, this.height - 1), new Vector3f(1f, 1f, 1f), matrices);
        RenderUtil.guiColorQuad(new Vector4i(this.x + 1, this.y, this.width - 1, this.y + 1), new Vector3f(1f, 1f, 1f), matrices);
        RenderUtil.guiColorQuad(new Vector4i(this.x + 1, this.height - 1, this.width - 1, this.height), new Vector3f(1f, 1f, 1f), matrices);
        RenderUtil.guiColorQuad(new Vector4i(this.width - 1, this.y, this.width, this.y + 1), new Vector3f(1f, 1f, 1f), matrices);
    }

    public void renderButton(MatrixStack matrixStack, int textureIndex) {
        RenderUtil.guiTextureQuad(this.posSize, new Vector4i(this.refPosSize.x, this.refPosSize.y + (this.refPosSize.w * textureIndex), this.refPosSize.z, this.refPosSize.w * (textureIndex + 1)), this.filePath, matrixStack, this.screen);
    }

}
