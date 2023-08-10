package net.makozort.advancedages.event;

import net.makozort.advancedages.AdvancedAges;
import net.makozort.advancedages.content.Pollution.PollutionData;
import net.makozort.advancedages.effect.ModEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;

public class ModEvents {
    @Mod.EventBusSubscriber(modid = AdvancedAges.MOD_ID)
    public static class ForgeEvents {
        @SubscribeEvent
        public static void onLiving(LivingEvent.LivingTickEvent event) {
            Entity entity = event.getEntity();
            if (entity.getLevel() instanceof ServerLevel) {
                Map<BlockPos, PollutionData.Pollution> map = PollutionData.get(entity.level).getMap();
                map.forEach((BlockPos, pollution) -> {
                    int distance = (entity.getOnPos().distManhattan(BlockPos));
                    if (distance <= 500) {
                        if (!event.getEntity().hasEffect(ModEffects.POLLUTION.get())) {
                            int reducer = 0;
                            if (entity instanceof Player) {
                                if (((Player) entity).getInventory().getArmor(3).is(new ItemStack((Items.IRON_HELMET)).getItem())) {
                                    // TODO: replace with custom mask (copy from create?)
                                    // TODO: create event to clear player of current stacks
                                    reducer = 4;
                                }
                            }
                            if (pollution.getPollution() >= (1.0)) {
                                event.getEntity().addEffect(new MobEffectInstance(ModEffects.POLLUTION.get(), 6000, (1 - reducer)));
                            }
                            if (pollution.getPollution() >= (3.0 - reducer)) {
                                event.getEntity().addEffect(new MobEffectInstance(ModEffects.POLLUTION.get(), 6000, (2 - reducer)));
                            }
                            if (pollution.getPollution() >= (4.0 - reducer)) {
                                event.getEntity().addEffect(new MobEffectInstance(ModEffects.POLLUTION.get(), 6000, (3 - reducer)));
                            }
                            if (pollution.getPollution() >= (5.0 - reducer)) {
                                event.getEntity().addEffect(new MobEffectInstance(ModEffects.POLLUTION.get(), 6000, (4 - reducer)));
                            }
                            if (pollution.getPollution() >= (8.0 - reducer)) {
                                event.getEntity().addEffect(new MobEffectInstance(ModEffects.POLLUTION.get(), 6000, (5 - reducer)));
                            }
                        }
                    }
                });
            }
        }

        @SubscribeEvent
        public static void onHelm( ) {

        }
    }
}


