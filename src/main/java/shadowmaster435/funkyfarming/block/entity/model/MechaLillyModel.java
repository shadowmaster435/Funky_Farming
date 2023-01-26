package shadowmaster435.funkyfarming.block.entity.model;


import net.minecraft.util.Identifier;
import shadowmaster435.funkyfarming.block.entity.MechaLillyEntity;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

public class MechaLillyModel extends DefaultedBlockGeoModel<MechaLillyEntity> {
    private final Identifier MODEL = buildFormattedModelPath(new Identifier("funkyfarming", "mechaflower"));
    private final Identifier TEXTURE = buildFormattedTexturePath(new Identifier("funkyfarming", "mechaflower_atlas"));
    private final Identifier ANIMATION = buildFormattedAnimationPath(new Identifier("funkyfarming", "mechaflower"));

    public MechaLillyModel() {
        super(new Identifier("funkyfarming", "mechaflower"));
    }


    @Override
    public Identifier getAnimationResource(MechaLillyEntity animatable) {
        return ANIMATION;
    }


    @Override
    public Identifier getModelResource(MechaLillyEntity animatable) {
        return MODEL;
    }


    @Override
    public Identifier getTextureResource(MechaLillyEntity animatable) {
        return TEXTURE;

    }


}