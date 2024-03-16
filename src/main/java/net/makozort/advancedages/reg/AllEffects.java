package net.makozort.advancedages.reg;

import net.makozort.advancedages.AdvancedAges;
import net.makozort.advancedages.content.effect.BlazeEffect;
import net.makozort.advancedages.content.effect.BlazeHighEffect;
import net.makozort.advancedages.content.effect.BlazeWithdrawlEffect;
import net.makozort.advancedages.content.effect.PollutionEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public abstract class AllEffects {
    public static DeferredRegister<MobEffect> MOB_EFFECTS
            = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, AdvancedAges.MOD_ID);
    public static final RegistryObject<MobEffect> POLLUTION = MOB_EFFECTS.register("pollution",
            () -> new PollutionEffect(MobEffectCategory.HARMFUL, 3124687));

    public static final RegistryObject<MobEffect> BLAZE = MOB_EFFECTS.register("blaze",
            () -> new BlazeEffect(MobEffectCategory.BENEFICIAL, 5124687));

    public static final RegistryObject<MobEffect> BLAZE_HIGH = MOB_EFFECTS.register("blaze_high",
            () -> new BlazeHighEffect(MobEffectCategory.BENEFICIAL, 5124687));

    public static final RegistryObject<MobEffect> BLAZE_WITHDRAWL = MOB_EFFECTS.register("blaze_withdrawl",
            () -> new BlazeWithdrawlEffect(MobEffectCategory.HARMFUL, 8124687));


    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
