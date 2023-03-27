package shadowmaster435.funkyfarming.gui.customelements;

import net.minecraft.client.util.math.MatrixStack;
import org.joml.Vector2i;
import shadowmaster435.funkyfarming.gui.util.EZScreen;
import shadowmaster435.funkyfarming.gui.util.EZScreenHandler;
import shadowmaster435.funkyfarming.rendering.RenderUtil;

public class SlotGrid<T extends EZScreenHandler<T>>  extends BaseElement<T> {

    public Vector2i pos;
    public Vector2i size;


    public SlotGrid(Vector2i pos, Vector2i size, EZScreenHandler<T> handler) {
        super(pos, RenderedSlot.tex, handler);
        this.pos = pos;
        this.size = size;
    }



    public void renderSlotGrid(MatrixStack matrices, EZScreen<T> screen) {
        for (int x = 0; x < this.size.x; x++) {
            for (int y = 0; y < this.size.y; y++) {
                RenderUtil.guiSlotQuad(new Vector2i(this.pos.x + (x * 18), this.pos.y + (y * 18)), this.filePath, matrices, screen);
            }
        }
    }
}
