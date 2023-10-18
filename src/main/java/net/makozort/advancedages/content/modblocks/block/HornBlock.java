package net.makozort.advancedages.content.modblocks.block;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;


public class HornBlock extends Block {
    public HornBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (level.hasNeighborSignal(pos) || level.hasNeighborSignal(pos.above())) {
            if (level instanceof ServerLevel) {
                Play(level,pos, GetSoundEvent(), 8);
            }
        }
    }

    public void Play(Level level,BlockPos pos, SoundEvent soundEvent, float range) {
        level.playSound(null, pos, soundEvent, SoundSource.BLOCKS, range ,1.0f); // NOTE: each full number for range represents 16 blocks
    }


    public SoundEvent GetSoundEvent(){
        return null;
    }

}
