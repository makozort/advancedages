package net.makozort.advancedages.content.items;

import com.simibubi.create.content.equipment.armor.BaseArmorItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;


public class PollutionMaskItem extends BaseArmorItem {
    public static final EquipmentSlot SLOT = EquipmentSlot.HEAD;

    public PollutionMaskItem(ArmorMaterial material, Properties properties, ResourceLocation textureLoc) {
        super(material, SLOT, properties, textureLoc);
    }

}
