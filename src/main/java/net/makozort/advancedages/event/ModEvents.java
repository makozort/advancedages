package net.makozort.advancedages.event;

import net.makozort.advancedages.AdvancedAges;
import net.makozort.advancedages.content.Pollution.PollutionData;
import net.makozort.advancedages.effect.ModEffects;
import net.makozort.advancedages.reg.Allitems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;

public class ModEvents extends BlockEntity {
    public ModEvents(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
        super(p_155228_, p_155229_, p_155230_);
    }

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
                            int reducer = 0;
                        if (pollution.getPollution() > (1.0)) {
                            if (entity instanceof Player) {
                                if (((Player) entity).getInventory().getArmor(3).is(new ItemStack(Allitems.POLLUTION_MASK.get()).getItem())) {
                                    reducer = 4;
                                }
                            }
                            if (pollution.getPollution() >= (1.0)) {
                                event.getEntity().addEffect(new MobEffectInstance(ModEffects.POLLUTION.get(), 100, (1 - reducer)));
                            }
                            if (pollution.getPollution() >= (3.0 - reducer)) {
                                event.getEntity().addEffect(new MobEffectInstance(ModEffects.POLLUTION.get(), 100, (2 - reducer)));
                            }
                            if (pollution.getPollution() >= (4.0 - reducer)) {
                                event.getEntity().addEffect(new MobEffectInstance(ModEffects.POLLUTION.get(), 100, (3 - reducer)));
                            }
                            if (pollution.getPollution() >= (5.0 - reducer)) {
                                event.getEntity().addEffect(new MobEffectInstance(ModEffects.POLLUTION.get(), 100, (4 - reducer)));
                            }
                            if (pollution.getPollution() >= (8.0 - reducer)) {
                                event.getEntity().addEffect(new MobEffectInstance(ModEffects.POLLUTION.get(), 100, (5 - reducer)));
                            }
                        }
                        }
                });
            }
        }

        static int tick;
        @SubscribeEvent
        public static void levelTick(TickEvent.LevelTickEvent event) {
            tick = tick + 1;
            Map<BlockPos, PollutionData.Pollution> map = PollutionData.get(event.level).getMap();
            map.forEach((BlockPos, pollution) -> {
                if (tick >= 72000) {
                    PollutionData.get(event.level).changePollution(BlockPos,-1,event.level);
                    tick = 0;
                }
            });
        }
    }
}

