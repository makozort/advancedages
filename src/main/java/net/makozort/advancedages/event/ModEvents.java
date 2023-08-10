package net.makozort.advancedages.event;

import net.makozort.advancedages.AdvancedAges;
import net.makozort.advancedages.content.Pollution.SaveTest;
import net.makozort.advancedages.effect.ModEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;
import java.util.*;

public class ModEvents {
    @Mod.EventBusSubscriber(modid = AdvancedAges.MOD_ID)
    public static class ForgeEvents {
        @SubscribeEvent
        public static void onLiving(LivingEvent.LivingTickEvent event) {
            Entity entity = event.getEntity();
            if (entity.getLevel() instanceof ServerLevel) {
                Map<BlockPos, SaveTest.Pollution> map = SaveTest.get(entity.level).getMap();
                map.forEach((BlockPos, pollution) -> {
                    int distance = (entity.getOnPos().distManhattan(BlockPos));
                    if (distance <= 500) {
                        AdvancedAges.LOGGER.info(String.valueOf(distance) + " " + String.valueOf(BlockPos) + " " + String.valueOf(pollution.getPollution()));
                        if (!event.getEntity().hasEffect(ModEffects.POLLUTION.get())) {
                            if (pollution.getPollution() >= 1.0) {
                                event.getEntity().addEffect(new MobEffectInstance(ModEffects.POLLUTION.get(), 200, 0));
                            }
                            if (pollution.getPollution() >= 3.0) {
                                event.getEntity().addEffect(new MobEffectInstance(ModEffects.POLLUTION.get(), 200, 1));
                            }
                            if (pollution.getPollution() >= 4.0) {
                                event.getEntity().addEffect(new MobEffectInstance(ModEffects.POLLUTION.get(), 200, 2));
                            }
                            if (pollution.getPollution() >= 5.0) {
                                event.getEntity().addEffect(new MobEffectInstance(ModEffects.POLLUTION.get(), 200, 3));
                            }
                            if (pollution.getPollution() >= 8.0) {
                                event.getEntity().addEffect(new MobEffectInstance(ModEffects.POLLUTION.get(), 200, 4));
                            }
                        }
                    }
                });
            }
            }
        }
    }


