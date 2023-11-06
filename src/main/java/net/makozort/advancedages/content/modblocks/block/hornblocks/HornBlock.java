package net.makozort.advancedages.content.modblocks.block.hornblocks;
import net.makozort.advancedages.AdvancedAges;
import net.makozort.advancedages.content.modblocks.Entity.HornBlockEntity;
import net.makozort.advancedages.reg.AllBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.apache.logging.log4j.core.jmx.Server;
import org.jetbrains.annotations.Nullable;

import java.util.Random;


public class HornBlock extends BaseEntityBlock {

    public static final BooleanProperty LIT = BooleanProperty.create("lit");

    public HornBlock(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.getStateDefinition().any().setValue(LIT,false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (level instanceof ServerLevel) {
            if (level.hasNeighborSignal(pos) || level.hasNeighborSignal(pos.above())) {
                if (level.getBlockEntity(pos) instanceof HornBlockEntity hornBlockEntity) {
                    level.setBlock(pos, state.setValue(LIT,true), 3);
                    hornBlockEntity.play(level,pos, GetSoundEvent(),12,GetCoolDownTime());
                    level.scheduleTick(pos, this, 100);
                }
            }
        }
    }
        public int GetCoolDownTime(){
        return 1800;
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource p_222948_) {
        level.setBlock(pos, state.setValue(LIT,false), 3);
    }

    public SoundEvent GetSoundEvent(){
        return null;
    }
    //BlockEntity stuff
    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new HornBlockEntity(AllBlockEntities.HORN_BLOCK.get(),pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, AllBlockEntities.HORN_BLOCK.get(),
                HornBlockEntity::tick);
    }
}
