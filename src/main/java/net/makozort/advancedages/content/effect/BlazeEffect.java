package net.makozort.advancedages.content.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;


public class BlazeEffect extends MobEffect {
    public BlazeEffect(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
    }





    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (!pLivingEntity.hasEffect(MobEffects.DAMAGE_BOOST)) {
                pLivingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 100, 3));
        }
        if (!pLivingEntity.hasEffect(MobEffects.DIG_SPEED)) {
            pLivingEntity.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 100, 3));
        }
    }
    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
