package shadowmaster435.funkyfarming.block.entity.model;

import net.minecraft.util.Identifier;
import shadowmaster435.funkyfarming.block.entity.WorkTableEntity;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

public class WorkTableModel extends DefaultedBlockGeoModel<WorkTableEntity> {
    private final Identifier MODEL = buildFormattedModelPath(new Identifier("funkyfarming", "worktable"));
    private final Identifier TEXTURE = buildFormattedTexturePath(new Identifier("funkyfarming", "worktable_atlas"));
    private final Identifier ANIMATION = buildFormattedAnimationPath(new Identifier("funkyfarming", "worktable"));



    public WorkTableModel() {
        super(new Identifier("funkyfarming", "worktable"));
    }


    @Override
    public Identifier getAnimationResource(WorkTableEntity animatable) {
        return ANIMATION;
    }


    @Override
    public Identifier getModelResource(WorkTableEntity animatable) {
        return MODEL;
    }


    @Override
    public Identifier getTextureResource(WorkTableEntity animatable) {
        return TEXTURE;

    }


}