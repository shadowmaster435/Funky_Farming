package shadowmaster435.funkyfarming.block.entity.model;


import net.minecraft.util.Identifier;
import shadowmaster435.funkyfarming.block.entity.GatewayEntity;
import shadowmaster435.funkyfarming.block.entity.MechaLillyEntity;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

public class GatewayModel extends DefaultedBlockGeoModel<GatewayEntity> {
    private final Identifier MODEL = buildFormattedModelPath(new Identifier("funkyfarming", "gateway"));
    private final Identifier TEXTURE = buildFormattedTexturePath(new Identifier("funkyfarming", "gateway_atlas"));
    private final Identifier ANIMATION = buildFormattedAnimationPath(new Identifier("funkyfarming", "gateway"));

    public GatewayModel() {
        super(new Identifier("funkyfarming", "gateway"));
    }


    @Override
    public Identifier getAnimationResource(GatewayEntity animatable) {
        return ANIMATION;
    }


    @Override
    public Identifier getModelResource(GatewayEntity animatable) {
        return MODEL;
    }


    @Override
    public Identifier getTextureResource(GatewayEntity animatable) {
        return TEXTURE;

    }


}