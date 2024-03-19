package net.makozort.advancedages.content.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class BlazeHighEffect extends MobEffect {
    public BlazeHighEffect(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
    }


    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        // lodestone stuff ig
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
