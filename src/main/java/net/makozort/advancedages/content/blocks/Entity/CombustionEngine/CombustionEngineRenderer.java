package net.makozort.advancedages.content.blocks.Entity.CombustionEngine;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class CombustionEngineRenderer extends KineticBlockEntityRenderer<combustionEngineBlockEntity> {

    public CombustionEngineRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }


    @Override
    protected void renderSafe(combustionEngineBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {


        BlockState state = getRenderedBlockState(be);
        RenderType type = getRenderType(be, state);
        if (type != null)
            renderRotatingBuffer(be, getRotatedModel(be, state), ms, buffer.getBuffer(type), light);
    }





    @Override
    protected SuperByteBuffer getRotatedModel(combustionEngineBlockEntity be, BlockState state) {
        return CachedBufferer.partialFacing(AllPartialModels.SHAFT_HALF, be.getBlockState(), be.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING).getOpposite());
    }
}