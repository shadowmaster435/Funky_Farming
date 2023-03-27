package shadowmaster435.funkyfarming.gui.customelements.barTypes;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.joml.Vector4i;
import shadowmaster435.funkyfarming.gui.customelements.ProgressBar;
import shadowmaster435.funkyfarming.gui.util.EZScreenHandler;
import shadowmaster435.funkyfarming.rendering.RenderUtil;

public class CardinalProgressElement<T extends EZScreenHandler<T>>  extends ProgressBar<T> {

    private final String direction;

    public CardinalProgressElement(Vector4i posSize, Vector4i refPosSize, Identifier filePath, int MaxProg, int delagateIndex, EZScreenHandler<T> handler, Screen screen, String direction) {
        super(posSize, refPosSize, filePath, MaxProg, delagateIndex, handler, screen);
        this.handler = handler;
        this.screen = screen;
        this.MaxProg = MaxProg;
        this.x = posSize.x();
        this.y = posSize.x();
        this.width = posSize.x + posSize.z;
        this.height = posSize.w + posSize.w;
        this.filePath = filePath;
        this.refPosSize = refPosSize;
        this.posSize = posSize;
        this.direction = direction;

    }

    public CardinalProgressElement(Vector4i posSize, Vector4i refPosSize, Identifier filePath, int MaxProg, int delagateIndex, EZScreenHandler<T> handler, Screen screen, String direction, Text tooltip) {
        super(posSize, refPosSize, filePath, MaxProg, delagateIndex, handler, screen);
        this.handler = handler;
        this.screen = screen;
        this.MaxProg = MaxProg;
        this.x = posSize.x();
        this.y = posSize.x();
        this.width = posSize.x + posSize.z;
        this.height = posSize.w + posSize.w;
        this.filePath = filePath;
        this.refPosSize = refPosSize;
        this.posSize = posSize;
        this.tooltip = tooltip;
        this.direction = direction;

    }

    public String getDirection() {
        return direction;
    }

    public void renderHorizontal(MatrixStack matrixStack, int Prog) {
        if (Prog >= 1) {
            RenderUtil.guiTextureQuad(this.posSize, new Vector4i(this.refPosSize.x, this.refPosSize.y + (this.refPosSize.w), (int) (this.refPosSize.z * (this.getProgressDelta())), this.refPosSize.w), this.filePath, matrixStack, this.screen);
        }
    }

    public void renderVertical(MatrixStack matrixStack, int Prog) {
        if (Prog >= 1) {
            RenderUtil.guiTextureQuad(this.posSize, new Vector4i(this.refPosSize.x, this.refPosSize.y + (this.refPosSize.w), this.refPosSize.z, (int) (this.refPosSize.w * (this.getProgressDelta()))), this.filePath, matrixStack, this.screen);
        }
    }
}
