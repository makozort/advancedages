package net.makozort.advancedages.reg;


import com.simibubi.create.content.equipment.armor.AllArmorMaterials;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.makozort.advancedages.AdvancedAges;
import net.makozort.advancedages.content.items.Co2DetectorItem;
import net.makozort.advancedages.content.items.IV.EmptyIVBagItem;
import net.makozort.advancedages.content.items.IV.IVBagItem;
import net.makozort.advancedages.content.items.NaturalGasSpongeItem;
import net.makozort.advancedages.content.items.OilScannerItem;
import net.makozort.advancedages.content.items.PollutionMaskItem;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SimpleFoiledItem;

import static com.simibubi.create.AllTags.forgeItemTag;
import static net.makozort.advancedages.AdvancedAges.REGISTRATE;

public class Allitems {

    public static final ItemEntry<BucketItem> HEAVY_OIL_BUCKET = REGISTRATE.item("heavy_oil_bucket",
                    p -> new BucketItem(AllFluids.HEAVY_OIL, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)))
            .register();
    public static final ItemEntry<Co2DetectorItem> POLLUTION_DETECTOR_ITEM = REGISTRATE.item("pollution_detector",
                    p -> new Co2DetectorItem(new Item.Properties().stacksTo(1).durability(6)))
            .register();
    public static final ItemEntry<OilScannerItem> OIL_SCANNER_ITEM = REGISTRATE.item("oil_scanner",
                    p -> new OilScannerItem(new Item.Properties().stacksTo(1).durability(6)))
            .register();
    public static final ItemEntry<NaturalGasSpongeItem> POLLUTION_SPONGE = REGISTRATE.item("pollution_sponge",
                    p -> new NaturalGasSpongeItem(new Item.Properties().stacksTo(1).durability(1)))
            .register();
    public static final ItemEntry<IVBagItem> IV_BAG = REGISTRATE.item("iv_bag",
                    p -> new IVBagItem(new Item.Properties().stacksTo(1).durability(600)))
            .register();
    public static final ItemEntry<IVBagItem> NUTRITIOUS_BAG = REGISTRATE.item("nutritious_iv_bag",
                    p -> new IVBagItem(new Item.Properties().stacksTo(1).durability(1800)))
            .register();
    public static final ItemEntry<EmptyIVBagItem> EMPTY_IV_BAG = REGISTRATE.item("empty_iv_bag",
                    p -> new EmptyIVBagItem(new Item.Properties().stacksTo(64)))
            .register();
    public static final ItemEntry<? extends PollutionMaskItem> POLLUTION_MASK = REGISTRATE.item("pollution_mask",
                    p -> new PollutionMaskItem(AllArmorMaterials.COPPER, p, AdvancedAges.asResource("pollution_mask")))
            .tag(forgeItemTag("armors/helmets"))
            .register();
    public static final ItemEntry<Item>
            MYSTERY_MEAT = REGISTRATE.item("meat", Item::new)
            .properties(p -> p.food(new FoodProperties.Builder().nutrition(10)
                    .meat()
                    .saturationMod(1)
                    .build()))
            .register(),
            MYSTERY_MEAT_SANDWICH = REGISTRATE.item("meat_sandwich", Item::new)
                    .properties(p -> p.food(new FoodProperties.Builder().nutrition(15)
                            .meat()
                            .saturationMod(5)
                            .build()))
                    .register();
    public static final ItemEntry<SimpleFoiledItem> CATALYZED_NETHERWART = REGISTRATE.item("catalyzed_netherwart",
                    p -> new SimpleFoiledItem(new Item.Properties().stacksTo(64)))
            .register();

    static {
        REGISTRATE.setCreativeTab(AllCreativeModeTabs.BASE_TAB);
    }

    public static void register() {
    }
}


