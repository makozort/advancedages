package net.makozort.advancedages.content.items;

import net.makozort.advancedages.content.capabilities.blaze.BlazeAddictionProvider;
import net.makozort.advancedages.reg.AllEffects;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BlazeItem extends Item {
    public BlazeItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (!world.isClientSide) {
            itemStack.shrink(1);
            player.getCapability(BlazeAddictionProvider.BLAZE_ADDICTION).ifPresent(addiction -> {
                addiction.addAddiction(36000);
                int add = addiction.getAddiction();
                int time = 18000;
                if (add >= 400000) {
                    time = 6000;
                } else if (add >= 180000) {
                    time = 12000;
                }
                player.addEffect(new MobEffectInstance(AllEffects.BLAZE.get(), time, 0));
            });

            player.addEffect(new MobEffectInstance(AllEffects.BLAZE_HIGH.get(), 100, 0));
            return InteractionResultHolder.success(itemStack);
        }

        return InteractionResultHolder.pass(itemStack);
    }
}

