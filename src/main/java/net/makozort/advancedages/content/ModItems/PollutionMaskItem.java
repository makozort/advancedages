package net.makozort.advancedages.content.ModItems;

import com.simibubi.create.content.equipment.armor.BacktankUtil;
import com.simibubi.create.content.equipment.armor.BaseArmorItem;
import com.simibubi.create.content.equipment.armor.DivingHelmetItem;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class PollutionMaskItem extends BaseArmorItem {
        public static final EquipmentSlot SLOT = EquipmentSlot.HEAD;

        public PollutionMaskItem(ArmorMaterial material, Properties properties, ResourceLocation textureLoc) {
            super(material, SLOT, properties, textureLoc);
        }




}
