package shadowmaster435.funkyfarming.gui.customelements;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.joml.Vector4i;
import shadowmaster435.funkyfarming.gui.util.EZScreenHandler;
import shadowmaster435.funkyfarming.rendering.RenderUtil;

public class ProgressBar<T extends EZScreenHandler<T>>  extends BaseElement<T> {

    public float MaxProg;

    public float Prog = 0;

    public int delagateIndex;

    public Text tooltip;
    public ProgressBar(Vector4i posSize, Vector4i refPosSize, Identifier filePath, int MaxProg, int delagateIndex, EZScreenHandler<T> handler, Screen screen) {
        super(posSize, refPosSize, filePath, handler, screen);
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
        this.delagateIndex = delagateIndex;
    }

    public ProgressBar(Vector4i posSize, Vector4i refPosSize, Identifier filePath, int MaxProg, int delagateIndex, EZScreenHandler<T> handler, Screen screen, Text tooltip) {
        super(posSize, refPosSize, filePath, handler, screen);
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
        this.delagateIndex = delagateIndex;
    }

    public int getDelagateIndex() {
        return delagateIndex;
    }

    public float getProgressDelta() {
        if (this.Prog !=0) {
            return (this.MaxProg / this.Prog);
        } else {
            return 0;
        }
    }
    public void renderTooltip(MatrixStack matrixStack, int mouseX, int mouseY) {
        this.screen.renderTooltip(matrixStack, this.tooltip, mouseX, mouseY);
    }


    public void render(MatrixStack matrixStack) {
        if (Prog >= 1) {
            RenderUtil.guiTextureQuad(this.posSize, new Vector4i(this.refPosSize.x, this.refPosSize.y + (this.refPosSize.w), this.refPosSize.z, this.refPosSize.w), this.filePath, matrixStack, this.screen);
        }
    }
}
