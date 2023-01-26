package shadowmaster435.funkyfarming.block.entity.renderer;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import shadowmaster435.funkyfarming.block.entity.MechaLillyEntity;
import shadowmaster435.funkyfarming.block.entity.model.MechaLillyModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class MechaLillyRenderer extends GeoBlockRenderer<MechaLillyEntity> {
    public MechaLillyRenderer() {
        super(new MechaLillyModel());
    }


}