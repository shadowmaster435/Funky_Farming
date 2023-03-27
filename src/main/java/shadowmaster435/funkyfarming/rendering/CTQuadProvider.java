package shadowmaster435.funkyfarming.rendering;

import net.fabricmc.fabric.api.client.model.ModelProviderContext;
import net.fabricmc.fabric.api.client.model.ModelProviderException;
import net.fabricmc.fabric.api.client.model.ModelResourceProvider;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

public class CTQuadProvider implements ModelResourceProvider {
    public static final CTQuad CT_QUAD = new CTQuad(Direction.NORTH, "bl");
    public static final Identifier CT_QUAD_MODEL_BLOCK = new Identifier("funkyfarming:block/ctquad");

    @Override
    public UnbakedModel loadModelResource(Identifier identifier, ModelProviderContext modelProviderContext) throws ModelProviderException {
        if(identifier.equals(CT_QUAD_MODEL_BLOCK)) {
            return CT_QUAD;
        } else {
            return null;
        }
    }
}