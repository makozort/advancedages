package net.makozort.advancedages.content.ModItems;

import net.makozort.advancedages.content.Pollution.PollutionData;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;


import java.awt.*;
import java.util.Map;

public class PollutionDetectorItem extends Item {
    public PollutionDetectorItem(Properties p_41383_) {
        super(p_41383_);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
            if (player.getLevel() instanceof ServerLevel) {
                Map<BlockPos, PollutionData.Pollution> map = PollutionData.get(player.level).getMap();
                map.forEach((BlockPos, pollution) -> {
                    int distance = (player.getOnPos().distManhattan(BlockPos));
                    if (distance <= 500) {
                        player.sendSystemMessage(Component.literal("pollution level " + String.valueOf(pollution.getPollution()) + " found at " + String.valueOf(BlockPos)));
                        player.getItemInHand(hand).hurtAndBreak(1,player,p -> p.broadcastBreakEvent(hand));
                    }
            });
        }
        return super.use(level,player,hand);
    }
}