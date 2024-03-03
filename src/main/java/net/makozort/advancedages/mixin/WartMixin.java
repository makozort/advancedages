package net.makozort.advancedages.mixin;

import net.makozort.advancedages.AdvancedAges;
import net.makozort.advancedages.reg.AllBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetherWartBlock.class)
public abstract class WartMixin extends Block {


    public WartMixin(Properties pProperties) {
        super(pProperties);
    }

    @Inject(method = "randomTick", at = @At("TAIL"), remap = false)
    private void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom, CallbackInfo ci) {
        if (pState.getValue(NetherWartBlock.AGE) == 3) {
            for (int i = 1; i <= 3; i++) {
                BlockPos abovePos = pPos.above(i);
                BlockState aboveState = pLevel.getBlockState(abovePos);
                AdvancedAges.LOGGER.info(String.valueOf(aboveState.getBlock()));
                if (aboveState.getBlock() == Blocks.SPORE_BLOSSOM) {
                    AdvancedAges.LOGGER.info("john");
                    pLevel.setBlock(pPos, AllBlocks.LUSH_NETHERWART_BLOCK.get().defaultBlockState(), 4);
                    break;
                }
            }
        }
    }

}
