package net.makozort.advancedages.content.items;


import net.makozort.advancedages.AdvancedAges;
import net.makozort.advancedages.reg.AllFluids;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

public class OilScannerItem extends Item {
    public OilScannerItem(Properties p_41383_) {
        super(p_41383_);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (level instanceof ServerLevel) {
            BlockPos pos = player.blockPosition().below();
            for (int y = pos.getY(); y >= pos.getY() - 500; y--) {
                BlockPos checkPos = new BlockPos(pos.getX(), y, pos.getZ());
                BlockState state = level.getBlockState(checkPos);
                FluidState fluidState = state.getFluidState();
                if (!fluidState.isEmpty() && fluidState.is(AllFluids.CRUDE_OIL.get().getSource())) {
                    player.sendSystemMessage(Component.literal("Oil found somewhere below you").withStyle(ChatFormatting.GREEN));
                    level.playSound(null, player.getOnPos(), SoundEvents.LAVA_POP, SoundSource.BLOCKS, 1.0f, 1.0f);
                    return InteractionResultHolder.success(player.getItemInHand(hand));
                }
            }
        }
        return super.use(level, player, hand);
    }
}

