package shadowmaster435.funkyfarming.block.entity.renderer;

import shadowmaster435.funkyfarming.block.entity.WorkTableEntity;
import shadowmaster435.funkyfarming.block.entity.model.WorkTableModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class WorkTableRenderer extends GeoBlockRenderer<WorkTableEntity> {
    public WorkTableRenderer() {
        super(new WorkTableModel());
    }


}