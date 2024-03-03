package net.makozort.advancedages.content.blocks.Entity.RefineLogic;

import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.MaterialManager;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.SingleRotatingInstance;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;
import net.makozort.advancedages.content.blocks.Entity.OilFilterBlockEntity;


public class OilFilterCogInstance extends SingleRotatingInstance<OilFilterBlockEntity> {

    public OilFilterCogInstance(MaterialManager materialManager, OilFilterBlockEntity blockEntity) {
        super(materialManager, blockEntity);
    }

    @Override
    protected Instancer<RotatingData> getModel() {
        return getRotatingMaterial().getModel(AllPartialModels.MILLSTONE_COG, blockEntity.getBlockState());
    }

}
