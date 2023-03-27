package shadowmaster435.funkyfarming.gui.customelements.barTypes;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.Identifier;
import org.joml.Vector4i;
import shadowmaster435.funkyfarming.gui.util.EZScreenHandler;

public class KeyFrameCardinalProgressBar<T extends EZScreenHandler<T>>  extends CardinalProgressElement<T> {

    public KeyFrameCardinalProgressBar(Vector4i posSize, Vector4i refPosSize, Identifier filePath, int MaxProg, int delagateIndex, EZScreenHandler<T> handler, Screen screen, String direction) {
        super(posSize, refPosSize, filePath, MaxProg, delagateIndex, handler, screen, direction);
    }
}
