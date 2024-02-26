package net.makozort.advancedages.content.blocks.Entity;

import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.millstone.MillstoneBlockEntity;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;

public class OilFilterRenderer extends KineticBlockEntityRenderer<OilFilterBlockEntity> {

    public OilFilterRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected SuperByteBuffer getRotatedModel(OilFilterBlockEntity be, BlockState state) {
        return CachedBufferer.partial(AllPartialModels.MILLSTONE_COG, state);
    }
}