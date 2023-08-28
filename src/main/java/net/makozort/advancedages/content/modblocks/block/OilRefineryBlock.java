package net.makozort.advancedages.content.modblocks.block;


import com.simibubi.create.foundation.block.IBE;
import net.makozort.advancedages.content.modblocks.entity.OilRefineryBlockEntity;
import net.makozort.advancedages.reg.AllBlockEntitys;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class OilRefineryBlock extends Block implements IBE<OilRefineryBlockEntity> {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public OilRefineryBlock(Properties properties) {
        super(properties);
    }

    private static final VoxelShape SHAPE =
            Block.box(0, 0, 0, 16, 10, 16);

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return SHAPE;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public Class<OilRefineryBlockEntity> getBlockEntityClass() {
        return OilRefineryBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends OilRefineryBlockEntity> getBlockEntityType() {
        return AllBlockEntitys.OIL_REFINERY_BLOCK_ENTITY.get();
    }

    /* BLOCK ENTITY */


    @Override
    public RenderShape getRenderShape(BlockState p_60550_) {
        return super.getRenderShape(p_60550_);
    }



}

