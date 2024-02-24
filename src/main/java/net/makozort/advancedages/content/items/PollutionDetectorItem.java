package net.makozort.advancedages.content.items;

import net.makozort.advancedages.AdvancedAges;
import net.makozort.advancedages.content.data.PollutionData;
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

public class PollutionDetectorItem extends Item {
    public PollutionDetectorItem(Properties p_41383_) {
        super(p_41383_);
    }


    double localPollution = 0.0;
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (player.level() instanceof ServerLevel) {
            Map<BlockPos, PollutionData.Pollution> map = PollutionData.get(player.level()).getMap();
            map.forEach((BlockPos, pollution) -> {
                int distance = (player.getOnPos().distManhattan(BlockPos));
                if (distance <= 500) {
                    if (pollution.getPollution() > 0) {
                        player.sendSystemMessage(Component.literal("pollution level " + pollution.getPollution() + " found at " + BlockPos).withStyle(ChatFormatting.RED));
                        localPollution = (localPollution + pollution.getPollution());
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
