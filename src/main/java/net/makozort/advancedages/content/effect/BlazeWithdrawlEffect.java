package net.makozort.advancedages.content.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class BlazeWithdrawlEffect extends MobEffect {
    public BlazeWithdrawlEffect(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
    }


    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (!pLivingEntity.hasEffect(MobEffects.WEAKNESS)) {
            pLivingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 2));
        }
        if (!pLivingEntity.hasEffect(MobEffects.DIG_SLOWDOWN)) {
            pLivingEntity.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 100, 2));
        }
    }
    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}