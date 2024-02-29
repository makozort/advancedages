package net.makozort.advancedages.content.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.makozort.advancedages.AdvancedAges;
import net.makozort.advancedages.content.data.DepositData;
import net.makozort.advancedages.foundation.gas.GasData;
import net.makozort.advancedages.foundation.gas.MixedVirtualGas;
import net.makozort.advancedages.reg.AllFluids;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

import java.util.Map;

public class DepositsCommand {
    public DepositsCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("deposits").then(Commands.argument("radius", IntegerArgumentType.integer())).executes((command) -> clearPoll(command.getSource())));
    }

    private int clearPoll(CommandSourceStack source) throws CommandSyntaxException {
        Player player = source.getPlayer();
        if (player.hasPermissions(4)) {
            if (source.getLevel() instanceof ServerLevel) {
                AdvancedAges.LOGGER.info(String.valueOf(DepositData.get(source.getLevel()).oilSearch(player.getOnPos(),400))); //todo: fix this to use the radius provided
                }
            }
        return 1;
    }
}
