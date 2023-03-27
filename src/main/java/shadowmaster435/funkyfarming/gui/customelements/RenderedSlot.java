package shadowmaster435.funkyfarming.gui.customelements;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.joml.Vector2i;
import shadowmaster435.funkyfarming.gui.util.EZScreen;
import shadowmaster435.funkyfarming.gui.util.EZScreenHandler;
import shadowmaster435.funkyfarming.rendering.RenderUtil;

public class RenderedSlot<T extends EZScreenHandler<T>> extends BaseElement<T> {

    private final Vector2i pos;
    public static final Identifier tex = new Identifier("funkyfarming:textures/gui/slot.png");

    public RenderedSlot(Vector2i pos, EZScreenHandler<T> handler) {
        super(pos, tex, handler);
        this.pos = pos;
    }



    public void renderSlot(MatrixStack matrices, EZScreen<T> screen) {
        RenderUtil.guiSlotQuad(this.pos, this.filePath, matrices, screen);
    }

}
