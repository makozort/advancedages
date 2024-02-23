package net.makozort.advancedages.content.items.IV;

import net.makozort.advancedages.reg.Allitems;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class IVBagItem extends Item {
    public IVBagItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean isSelected) {
        if (!world.isClientSide && entity instanceof Player player) {
            if (world.getGameTime() % 20 == 0) { // Every second
                ItemStack selectedIVBag = getIVBagToUse(player);
                if (selectedIVBag != null) {
                    applyEffects(player, selectedIVBag);
                }
            }
        }
    }

    private ItemStack getIVBagToUse(Player player) {
        ItemStack selectedBag = null;
        int lowestDurability = Integer.MAX_VALUE;

        for (ItemStack itemStack : player.getInventory().items) {
            if (itemStack.getItem() instanceof IVBagItem) {
                int durability = itemStack.getMaxDamage() - itemStack.getDamageValue();
                if (durability < lowestDurability) {
                    selectedBag = itemStack;
                    lowestDurability = durability;
                }
            }
        }

        return selectedBag;
    }

    @Override
    public boolean isEnchantable(ItemStack pStack) {
        return false;
    }

    private void applyEffects(Player player, ItemStack stack) {
        player.addEffect(new MobEffectInstance(MobEffects.SATURATION, 30, 1, false, false));
        stack.hurt(1, player.getRandom(), player instanceof ServerPlayer ? (ServerPlayer) player : null);

        if (stack.getDamageValue() > stack.getMaxDamage()) {
            ItemStack emptyIVBag = new ItemStack(Allitems.EMPTY_IV_BAG.get());
            int slot = player.getInventory().findSlotMatchingItem(stack);
            player.getInventory().setItem(slot, emptyIVBag);
        }
    }
}
