package net.makozort.advancedages.content.blocks.block.hornblocks;

import com.simibubi.create.content.kinetics.base.DirectionalKineticBlock;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.foundation.block.IBE;
import net.makozort.advancedages.AdvancedAges;
import net.makozort.advancedages.content.blocks.Entity.HornBlockEntity;
import net.makozort.advancedages.reg.AllBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;


import javax.annotation.Nullable;

import static com.simibubi.create.content.kinetics.base.IRotate.StressImpact.HIGH;


public class HornBlock extends DirectionalKineticBlock implements IBE<HornBlockEntity> {

    public static final BooleanProperty LIT = BooleanProperty.create("lit");

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public HornBlock(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.getStateDefinition().any().setValue(LIT, false));
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (level instanceof ServerLevel) {
            if (level.hasNeighborSignal(pos) || level.hasNeighborSignal(pos.above())) {
                if (level.getBlockEntity(pos) instanceof HornBlockEntity hornBlockEntity) {
                    float rpm = (Math.abs(hornBlockEntity.getSpeed()));
                    if (rpm > 0.0F) {
                        if (rpm >= 256.0F) {
                            hornBlockEntity.play(level, pos, GetSoundEvent(), (14*rpm/256), GetCoolDownTime(),ScreenShakeDuration(), state); // range is calculated where 1 = 16 block radius
                        } else {
                            hornBlockEntity.play(level, pos, GetSoundEvent(), (14*rpm/256), GetCoolDownTime(),0, state);
                        }
                        level.scheduleTick(pos, this, GetCoolDownTime());
                    }
                }
            }
        }
    }


    //@Override
    //public SpeedLevel getMinimumRequiredSpeedLevel() {
    //    return SpeedLevel.FAST;
    //}

    public int GetCoolDownTime() {
        return 1800;
    }

    public int ScreenShakeDuration() {return 0;}

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource p_222948_) {
        level.setBlock(pos, state.setValue(LIT, false), 3);
    }

    public SoundEvent GetSoundEvent() {
        return null;
    }

    //BlockEntity stuff
    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public Class<HornBlockEntity> getBlockEntityClass() {return HornBlockEntity.class;}

    @Override
    public BlockEntityType<? extends HornBlockEntity> getBlockEntityType() {return AllBlockEntities.HORN_BLOCK.get();}




    /* kinetics */
    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return state.getValue(FACING).getAxis();
    }
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new HornBlockEntity(AllBlockEntities.HORN_BLOCK.get(), pos, state);
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return face == state.getValue(FACING).getOpposite();
    }


    /* FACING */

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
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(LIT);
        pBuilder.add(FACING);
    }


}
