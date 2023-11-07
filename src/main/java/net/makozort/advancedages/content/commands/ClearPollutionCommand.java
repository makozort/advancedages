package net.makozort.advancedages.content.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.makozort.advancedages.AdvancedAges;
import net.makozort.advancedages.content.data.PollutionData;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

import java.util.Map;

public class ClearPollutionCommand {
    public ClearPollutionCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("pollution").then(Commands.literal("clear").then(Commands.literal("all").executes((command) -> {
            return clearPoll(command.getSource());
        }))));
    }

    private int clearPoll(CommandSourceStack source) throws CommandSyntaxException {
        Player player = source.getPlayer();
        if (player.hasPermissions(4)) {
            if (source.getLevel() instanceof ServerLevel) {
                Map<BlockPos, PollutionData.Pollution> map = PollutionData.get(source.getLevel()).getMap();
                map.forEach((BlockPos, pollution) -> {
                    PollutionData.get(source.getLevel()).changePollution(BlockPos, -12, source.getLevel());
                    player.sendSystemMessage(Component.literal("pollution clear at " + BlockPos).withStyle(ChatFormatting.GREEN));
                    AdvancedAges.LOGGER.info("pollution clear at " + BlockPos);
                });
            } else {
                player.sendSystemMessage(Component.literal("you don't have permission to do this").withStyle(ChatFormatting.RED));
            }
        }
        return 1;
    }
}