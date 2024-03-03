package net.makozort.advancedages.content.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

import java.util.Random;

public class PollutionEffect extends MobEffect {
    public PollutionEffect(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
    }


    // don't look at this please I beg you


    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (pAmplifier > 0) {
            switch (pAmplifier) {
                case 1:
                    if (!pLivingEntity.hasEffect(MobEffects.WEAKNESS)) {
                        pLivingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 0));
                    }
                    break;
                case 2:
                    if (!pLivingEntity.hasEffect(MobEffects.WEAKNESS)) {
                        pLivingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 0));
                    }
                    if (!pLivingEntity.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) {
                        pLivingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 0));
                    }
                    break;
                case 3:
                    if (!pLivingEntity.hasEffect(MobEffects.WEAKNESS)) {
                        pLivingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 2));
                    }
                    if (!pLivingEntity.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) {
                        pLivingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 0));
                    }
                    if (!pLivingEntity.hasEffect(MobEffects.HUNGER)) {
                        pLivingEntity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 100, 0));
                    }
                    break;
                case 4:
                    if (!pLivingEntity.hasEffect(MobEffects.WEAKNESS)) {
                        pLivingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 2));
                    }
                    if (!pLivingEntity.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) {
                        pLivingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 1));
                    }
                    if (!pLivingEntity.hasEffect(MobEffects.HUNGER)) {
                        pLivingEntity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 100, 0));
                    }
                    if (!pLivingEntity.hasEffect(MobEffects.DIG_SLOWDOWN)) {
                        pLivingEntity.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 100, 0));
                    }
                    break;
                case 5:
                    if (!pLivingEntity.hasEffect(MobEffects.WEAKNESS)) {
                        pLivingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 2));
                    }
                    if (!pLivingEntity.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) {
                        pLivingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 1));
                    }
                    if (!pLivingEntity.hasEffect(MobEffects.HUNGER)) {
                        pLivingEntity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 100, 0));
                    }
                    if (!pLivingEntity.hasEffect(MobEffects.DIG_SLOWDOWN)) {
                        pLivingEntity.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 100, 0));
                    }
                    int hit = 50;
                    Random rand = new Random();
                    int roll = rand.nextInt(80);
                    if (roll == hit) {
                        pLivingEntity.hurt(pLivingEntity.level().damageSources().generic(), 4);
                    }
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
