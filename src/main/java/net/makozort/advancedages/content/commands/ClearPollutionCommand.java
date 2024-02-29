package net.makozort.advancedages.content.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.makozort.advancedages.AdvancedAges;
import net.makozort.advancedages.foundation.gas.MixedVirtualGas;
import net.makozort.advancedages.foundation.gas.GasData;
import net.makozort.advancedages.reg.AllFluids;
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
        dispatcher.register(Commands.literal("pollution").then(Commands.literal("clear").then(Commands.literal("all").executes((command) -> clearPoll(command.getSource())))));
    }

    private int clearPoll(CommandSourceStack source) throws CommandSyntaxException {
        Player player = source.getPlayer();
        if (player.hasPermissions(4)) {
            if (source.getLevel() instanceof ServerLevel) {
                Map<BlockPos, MixedVirtualGas> map = GasData.get(source.getLevel()).getGasMap();
                map.forEach((BlockPos, gas) -> {
                    GasData.get(source.getLevel()).changeGas(BlockPos, AllFluids.CARBON_DIOXIDE.get(), -gas.getGas(AllFluids.CARBON_DIOXIDE.get()), source.getLevel());
                    GasData.get(source.getLevel()).changeGas(BlockPos, AllFluids.NATURAL_GAS.get(), -gas.getGas(AllFluids.NATURAL_GAS.get()), source.getLevel());
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