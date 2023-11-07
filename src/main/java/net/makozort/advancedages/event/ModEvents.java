package net.makozort.advancedages.event;

import com.simibubi.create.content.kinetics.saw.SawBlock;
import net.makozort.advancedages.AdvancedAges;
import net.makozort.advancedages.content.commands.ClearPollutionCommand;
import net.makozort.advancedages.content.data.PollutionData;
import net.makozort.advancedages.content.effect.ModEffects;
import net.makozort.advancedages.reg.Allitems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
                List<Double> list = new ArrayList<>();
                map.forEach((BlockPos, pollution) -> { // this loops through every entry in the pollution and checks if is close enough, applying effects based on the level
                    int distance = (entity.getOnPos().distManhattan(BlockPos));
                    if (distance <= 500) {
                        list.add(pollution.getPollution()); // this part makes it cumulative
                    }
                });
                double total = list.stream().mapToDouble(f -> f.doubleValue()).sum();
                int reducer = 0;
                if (total >= (1.0)) {
                    if (entity instanceof Player) {
                        if (((Player) entity).getInventory().getArmor(3).is(new ItemStack(Allitems.POLLUTION_MASK.get()).getItem())) { // remove some pollution from player if there are wearing mask
                            reducer = 2;
                        }
                    }
                    if (total >= (2)) {
                        event.getEntity().addEffect(new MobEffectInstance(ModEffects.POLLUTION.get(), 100, (1 - reducer)));
                    }
                    if (total >= (6 - reducer)) {
                        event.getEntity().addEffect(new MobEffectInstance(ModEffects.POLLUTION.get(), 100, (2 - reducer)));
                    }
                    if (total >= (8 - reducer)) {
                        event.getEntity().addEffect(new MobEffectInstance(ModEffects.POLLUTION.get(), 100, (3 - reducer)));
                    }
                    if (total >= (10 - reducer)) {
                        event.getEntity().addEffect(new MobEffectInstance(ModEffects.POLLUTION.get(), 100, (4 - reducer)));
                    }
                    if (total >= (16 - reducer)) {
                        event.getEntity().addEffect(new MobEffectInstance(ModEffects.POLLUTION.get(), 100, (5 - reducer)));
                    }
                }
            }
        }

        @SubscribeEvent
        public static void sawHurt(LivingHurtEvent event) { // villager meat
            Entity entity = event.getEntity();
            Level world = event.getEntity().getLevel();
            if (entity.getLevel() instanceof ServerLevel) {
                if (entity instanceof Villager) {
                    if (event.getSource() == SawBlock.damageSourceSaw) {
                        world.addFreshEntity(new ItemEntity(world, entity.getBlockX(), entity.getBlockY() + 2, entity.getBlockZ(),
                                new ItemStack(Allitems.MYSTERY_MEAT.get(), 2)));
                    }
                }
            }
        }


        // handles decaying pollution and clearing old pollution values of 0
        static int tick;

        @SubscribeEvent
        public static void levelTick(TickEvent.LevelTickEvent event) {
            if (event.level instanceof ServerLevel) {
                tick = tick + 1;
                Map<BlockPos, PollutionData.Pollution> map = PollutionData.get(event.level).getMap();
                map.forEach((BlockPos, pollution) -> {
                    if (tick >= 72000) {
                        PollutionData.get(event.level).changePollution(BlockPos, -1, event.level);
                        tick = 0;
                    }
                });
                PollutionData.get(event.level).clearOldPollution();
            }
        }

        @SubscribeEvent
        public static void onCommandRegister(RegisterCommandsEvent event) {
            new ClearPollutionCommand(event.getDispatcher());
            ConfigCommand.register(event.getDispatcher());
        }
    }
}

