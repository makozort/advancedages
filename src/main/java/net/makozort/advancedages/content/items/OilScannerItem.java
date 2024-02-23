package net.makozort.advancedages.content.items;


import net.makozort.advancedages.reg.AllBlocks;
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

public class OilScannerItem extends Item {
    public OilScannerItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (level instanceof ServerLevel) {
            BlockPos playerPos = player.getOnPos();
            for (int y = 255; y >= -16; y--) {
                BlockPos searchPos = new BlockPos(playerPos.getX(), y, player.getBlockZ());
                Block block = level.getBlockState(searchPos).getBlock();
                if (block == AllBlocks.CRUDE_OIL_BLOCK.get()) {
                    player.sendSystemMessage(Component.literal("Oil found somewhere above or below you").withStyle(ChatFormatting.GREEN));
                    level.playSound(null, playerPos, SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.BLOCKS, 1.0f, 1.0f);
                    return InteractionResultHolder.success(player.getItemInHand(hand));
                }
            }
            player.sendSystemMessage(Component.literal("no crude oil found here...").withStyle(ChatFormatting.RED));
        }
        return super.use(level, player, hand);
    }
}

