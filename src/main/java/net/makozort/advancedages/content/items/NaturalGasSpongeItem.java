package net.makozort.advancedages.content.items;

import net.makozort.advancedages.foundation.gas.GasData;
import net.makozort.advancedages.foundation.gas.MixedVirtualGas;
import net.makozort.advancedages.reg.AllFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Map;

public class NaturalGasSpongeItem extends Item {
    public NaturalGasSpongeItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (player.level() instanceof ServerLevel) {
            Map<BlockPos, MixedVirtualGas> map = GasData.get(player.level()).getGasMap();
            map.forEach((BlockPos, pollution) -> {
                int distance = (player.getOnPos().distManhattan(BlockPos));
                if (distance <= 30) {
                    GasData.get(level).changeGas(BlockPos, AllFluids.NATURAL_GAS.get(), -12, level);
                }
            });
        }
        player.getItemInHand(hand).hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
        return super.use(level, player, hand);
    }
}

