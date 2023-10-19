package net.makozort.advancedages.content.modblocks.block;
import net.makozort.advancedages.content.modblocks.Entity.HornBlockEntity;
import net.makozort.advancedages.reg.AllBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;


public class HornBlock extends BaseEntityBlock {

    public HornBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (level instanceof ServerLevel) {
            if (level.hasNeighborSignal(pos) || level.hasNeighborSignal(pos.above())) {
                if (level.getBlockEntity(pos) instanceof HornBlockEntity hornBlockEntity) {
                    hornBlockEntity.play(level,pos, GetSoundEvent(),12,GetCoolDownTime());
                }
            }
        }
    }

        public int GetCoolDownTime(){
        return 1800;
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
