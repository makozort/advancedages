package net.makozort.advancedages.mixin;

import net.makozort.advancedages.networking.ModPackets;
import net.makozort.advancedages.networking.packet.BombPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FireBlock.class)
public class FireMixin {

    @Inject(at = @At("TAIL"), method = "tick", remap = false)
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom, CallbackInfo ci) {
        for (Direction direction : Direction.values()) {
            BlockPos neighborPos = pPos.relative(direction);
            BlockState neighborState = pLevel.getBlockState(neighborPos);
            if (neighborState.is(Blocks.BARRIER)) {
                if (!pLevel.isClientSide && pLevel instanceof ServerLevel) {
                    ServerLevel serverLevel = (ServerLevel) pLevel;
                    for (ServerPlayer player : serverLevel.players()) {
                        if (player.blockPosition().distSqr(pPos) <= (40) * (40)) {
                            ModPackets.sendToPlayer(new BombPacket(pPos,.01f,true,false,false),player);
                        }
                    }
                }
            }
        }
    }
}
