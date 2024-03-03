package net.makozort.advancedages.content.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.makozort.advancedages.AdvancedAges;
import net.makozort.advancedages.content.data.DepositData;
import net.makozort.advancedages.foundation.gas.GasData;
import net.makozort.advancedages.foundation.gas.GasStack;
import net.makozort.advancedages.foundation.gas.MixedVirtualGas;
import net.makozort.advancedages.foundation.gas.VirtualGas;
import net.makozort.advancedages.reg.AllFluids;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;

public class DepositsCommand {
    public DepositsCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("deposits").executes((command) -> clearPoll(command.getSource())));
    }

    private int clearPoll(CommandSourceStack source) throws CommandSyntaxException {
        Player player = source.getPlayer();
        Level level = source.getLevel();
        if (player.hasPermissions(4)) {
            if (source.getLevel() instanceof ServerLevel) {
                ServerLevel serverLevel = (ServerLevel) source.getLevel();
                int radius = 10;
                int startX = player.getBlockX();
                int startY = player.getBlockY();
                int startZ = player.getBlockZ();
                int endX = player.getBlockX() + radius;
                int endY = player.getBlockY() + radius;
                int endZ = player.getBlockZ() + radius;
                for (int x = startX; x <= endX; x++) {
                    for (int y = startY; y <= endY; y++) {
                        for (int z = startZ; z <= endZ; z++) {
                            BlockPos pos = new BlockPos(x, y, z);
                            // Set the block at the current position to TNT
                            GasData.get(level).changeGas(pos, AllFluids.NATURAL_GAS.get(), -12, level);
                        }
                    }
                }
            }
        }
        return 1;
    }


}
