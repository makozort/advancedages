package net.makozort.advancedages.event;

import net.makozort.advancedages.AdvancedAges;
import net.makozort.advancedages.content.blocks.entity.ThumperBlockEntity;
import net.makozort.advancedages.content.capabilities.blaze.BlazeAddiction;
import net.makozort.advancedages.content.capabilities.blaze.BlazeAddictionProvider;
import net.makozort.advancedages.content.commands.ClearPollutionCommand;
import net.makozort.advancedages.content.commands.DepositsCommand;
import net.makozort.advancedages.foundation.gas.GasData;
import net.makozort.advancedages.foundation.gas.MixedVirtualGas;
import net.makozort.advancedages.reg.AllEffects;
import net.makozort.advancedages.reg.AllFluids;
import net.makozort.advancedages.reg.Allitems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
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

        // handles decaying pollution and clearing old pollution values of 0
        static int tick;

        @SubscribeEvent
        public static void onLiving(LivingEvent.LivingTickEvent event) {
            Entity entity = event.getEntity();
            if (entity.level() instanceof ServerLevel) {
                Map<BlockPos, MixedVirtualGas> map = GasData.get(entity.level()).getGasMap();
                List<Integer> list = new ArrayList<>();
                map.forEach((BlockPos, pollution) -> { // this loops through every entry in the pollution and checks if is close enough, applying effects based on the level
                    int distance = (entity.getOnPos().distManhattan(BlockPos));
                    if (distance <= 500) {
                        list.add(pollution.getGas(AllFluids.CARBON_DIOXIDE.get()));
                        list.add(pollution.getGas(AllFluids.NATURAL_GAS.get()) * 2);
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
                        event.getEntity().addEffect(new MobEffectInstance(AllEffects.POLLUTION.get(), 100, (1 - reducer))); //TODO: change this over to the effect sie
                    }
                    if (total >= (6 - reducer)) {
                        event.getEntity().addEffect(new MobEffectInstance(AllEffects.POLLUTION.get(), 100, (2 - reducer)));
                    }
                    if (total >= (8 - reducer)) {
                        event.getEntity().addEffect(new MobEffectInstance(AllEffects.POLLUTION.get(), 100, (3 - reducer)));
                    }
                    if (total >= (10 - reducer)) {
                        event.getEntity().addEffect(new MobEffectInstance(AllEffects.POLLUTION.get(), 100, (4 - reducer)));
                    }
                    if (total >= (16 - reducer)) {
                        event.getEntity().addEffect(new MobEffectInstance(AllEffects.POLLUTION.get(), 100, (5 - reducer)));
                    }
                }
            }
        }

        @SubscribeEvent
        public static void onPlayerInteract(PlayerInteractEvent.RightClickBlock event) {
            if (!event.getLevel().isClientSide() && event.getHand() == InteractionHand.MAIN_HAND) {
                BlockPos pos = event.getPos();
                ServerLevel level = (ServerLevel) event.getLevel();
                BlockEntity blockEntity = level.getBlockEntity(pos);
                Item item = event.getEntity().getItemInHand(InteractionHand.MAIN_HAND).getItem();
                if (blockEntity instanceof ThumperBlockEntity thumperBlock && item == Allitems.OIL_SCANNER_ITEM.asItem()) {
                    event.getEntity().setItemInHand(InteractionHand.MAIN_HAND, Allitems.EMPTY_IV_BAG.asStack());
                    event.getEntity().sendSystemMessage(Component.literal(String.valueOf(thumperBlock.getFoundDeposits())).withStyle(ChatFormatting.YELLOW));
                    level.playSound(null, pos, SoundEvents.LAVA_POP, SoundSource.MASTER, 1, 1);

                }
            }
        }

        @SubscribeEvent
        public static void sawHurt(LivingHurtEvent event) { // villager meat
            Entity entity = event.getEntity();
            Level world = event.getEntity().level();
            if (entity.level() instanceof ServerLevel) {
                if (entity instanceof Villager) {
                    AdvancedAges.LOGGER.info(event.getSource().toString());
                    if (event.getSource().toString().equals("DamageSource (create.mechanical_saw)")) { //TODO: make this not completely fucked (or get rid of it)
                        world.addFreshEntity(new ItemEntity(world, entity.getBlockX(), entity.getBlockY() + 2, entity.getBlockZ(),
                                new ItemStack(Allitems.MYSTERY_MEAT.get(), 2)));
                    }
                }
            }
        }

        @SubscribeEvent
        public static void furnaceEvent(FurnaceFuelBurnTimeEvent event) {
            if (event.getItemStack().is(Allitems.HEAVY_OIL_BUCKET.get())) {
                event.setBurnTime(32767);
            }
        }

        @SubscribeEvent
        public static void levelTick(TickEvent.LevelTickEvent event) {
            if (event.level instanceof ServerLevel) {
                tick = tick + 1;
                Map<BlockPos, MixedVirtualGas> map = GasData.get(event.level).getGasMap();
                map.forEach((BlockPos, gas) -> {
                    if (tick >= 72000) {
                        GasData.get(event.level).changeGas(BlockPos, AllFluids.CARBON_DIOXIDE.get(), -1, event.level);
                        tick = 0;
                    }
                });
                GasData.get(event.level).clearOldPollution();
            }
        }

        @SubscribeEvent
        public static void onCommandRegister(RegisterCommandsEvent event) {
            new ClearPollutionCommand(event.getDispatcher());
            new DepositsCommand(event.getDispatcher());
            ConfigCommand.register(event.getDispatcher());
        }

        @SubscribeEvent
        public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof Player) {
                if (!event.getObject().getCapability(BlazeAddictionProvider.BLAZE_ADDICTION).isPresent()) {
                    event.addCapability(new ResourceLocation(AdvancedAges.MOD_ID, "properties"), new BlazeAddictionProvider());
                }
            }
        }

        @SubscribeEvent
        public static void onPlayerCloned(PlayerEvent.Clone event) {
            if (event.isWasDeath()) {
                event.getOriginal().getCapability(BlazeAddictionProvider.BLAZE_ADDICTION).ifPresent(oldStore -> {
                    event.getEntity().getCapability(BlazeAddictionProvider.BLAZE_ADDICTION).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });
            }
        }

        @SubscribeEvent
        public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
            event.register(BlazeAddiction.class);
        }

        @SubscribeEvent
        public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
            if (event.side == LogicalSide.SERVER) {
                event.player.getCapability(BlazeAddictionProvider.BLAZE_ADDICTION).ifPresent(addiction -> {
                    if (addiction.getAddiction() > 0) {
                        addiction.subAddiction(1);
                        if (addiction.getAddiction() >= 72000 && !event.player.hasEffect(AllEffects.BLAZE.get()) && !event.player.hasEffect(AllEffects.BLAZE_WITHDRAWL.get())) {
                            event.player.addEffect(new MobEffectInstance(AllEffects.BLAZE_WITHDRAWL.get(), 100, 0));
                        }

                    }
                });
            }
        }


        @SubscribeEvent
        public static void changeItem(LivingEquipmentChangeEvent event) { // possible duplication risks here
            if (event.getEntity() instanceof Player player) {
                if (event.getTo().is(Allitems.BOLT_GUN.asItem()) && !player.getOffhandItem().isEmpty()) {
                    player.drop(event.getTo().copyAndClear(),true);
                    Component comp =  Component.literal("the rifle must be held in the main hand with nothing held in the offhand").withStyle(ChatFormatting.RED);
                    player.displayClientMessage(comp, true);
                }
                if (event.getSlot().getIndex() == 1 && player.getMainHandItem().is(Allitems.BOLT_GUN.asItem())) {
                    player.drop(player.getMainHandItem().copyAndClear(),true);
                    Component comp =  Component.literal("the rifle must be held in the main hand with nothing held in the offhand").withStyle(ChatFormatting.RED);
                    player.displayClientMessage(comp, true);
                }
            }
            if (event.getEntity() instanceof Player player) {
                if (event.getTo().is(Allitems.FLAME_THROWER.asItem()) && !player.getOffhandItem().isEmpty()) {
                    player.drop(event.getTo().copyAndClear(),true);
                    Component comp =  Component.literal("the flame thrower must be held in the main hand with nothing held in the offhand").withStyle(ChatFormatting.RED);
                    player.displayClientMessage(comp, true);
                }
                if (event.getSlot().getIndex() == 1 && player.getMainHandItem().is(Allitems.FLAME_THROWER.asItem())) {
                    player.drop(player.getMainHandItem().copyAndClear(),true);
                    Component comp =  Component.literal("the flame thrower must be held in the main hand with nothing held in the offhand").withStyle(ChatFormatting.RED);
                    player.displayClientMessage(comp, true);
                }
            }
        }
    }
}

