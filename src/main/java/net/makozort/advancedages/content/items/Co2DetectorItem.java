package net.makozort.advancedages.content.items;

import net.makozort.advancedages.foundation.gas.MixedVirtualGas;
import net.makozort.advancedages.foundation.gas.pollution.GasData;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Map;

import static net.makozort.advancedages.reg.AllFluids.CARBON_DIOXIDE;

public class Co2DetectorItem extends Item {
    public Co2DetectorItem(Properties p_41383_) {
        super(p_41383_);
    }


    double localPollution = 0.0;
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (player.level() instanceof ServerLevel) {
            Map<BlockPos, MixedVirtualGas> map = GasData.get(player.level()).getGasMap();
            map.forEach((BlockPos, gasses) -> {
                int distance = (player.getOnPos().distManhattan(BlockPos));
                if (distance <= 500) {
                    if (gasses.getGas(CARBON_DIOXIDE.get()) > 0) {
                        player.sendSystemMessage(Component.literal("pollution level " + gasses.getGas(CARBON_DIOXIDE.get()) + " found at " + BlockPos).withStyle(ChatFormatting.RED));
                        localPollution = (localPollution + gasses.getGas(CARBON_DIOXIDE.get()));
                    }
                }
            });
            player.sendSystemMessage(Component.literal("Total Local Pollution Level " + localPollution).withStyle(ChatFormatting.YELLOW));
        }
        player.getItemInHand(hand).hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
        localPollution = 0;

        return super.use(level, player, hand);
    }
}
