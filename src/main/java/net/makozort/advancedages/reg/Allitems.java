package net.makozort.advancedages.reg;

import com.simibubi.create.AllTags;
import com.simibubi.create.content.equipment.armor.AllArmorMaterials;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.makozort.advancedages.AdvancedAges;
import net.makozort.advancedages.content.items.*;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import static com.simibubi.create.AllTags.forgeItemTag;
import static net.makozort.advancedages.ModRegistrate.REGISTRATE;

public class Allitems {

    static {
        REGISTRATE.creativeModeTab(() -> ModCreativeModeTab.BIG_TAB);
    }

    public static final ItemEntry<BucketItem> CRUDE_OIL_BUCKET = REGISTRATE.item("crude_oil_bucket",
                    p -> new BucketItem(AllFluids.SOURCE_CRUDE_OIL, props().craftRemainder(Items.BUCKET).stacksTo(1)))
            .register();

    public static final ItemEntry<PollutionDetectorItem> POLLUTION_DETECTOR_ITEM = REGISTRATE.item("pollution_detector",
                    p -> new PollutionDetectorItem(props().stacksTo(1).durability(6)))
            .register();


    public static final ItemEntry<OilScannerItem> OIL_SCANNER_ITEM = REGISTRATE.item("oil_scanner",
                    p -> new OilScannerItem(props().stacksTo(1).durability(6)))
            .register();
    public static final ItemEntry<PollutionSpongeItem> POLLUTION_SPONGE = REGISTRATE.item("pollution_sponge",
                    p -> new PollutionSpongeItem(props().stacksTo(1).durability(1)))
            .register();

    public static final ItemEntry<OilBucketItem> HEAVY_OIL_BUCKET = REGISTRATE.item("heavy_oil_bucket",
                    p -> new OilBucketItem(AllFluids.SOURCE_HEAVY_OIL, props().craftRemainder(Items.BUCKET).stacksTo(1)))
            .tag(AllTags.AllItemTags.BLAZE_BURNER_FUEL_SPECIAL.tag)
            .register();

    public static final ItemEntry<? extends PollutionMaskItem> POLLUTION_MASK = REGISTRATE.item("pollution_mask",
                    p -> new PollutionMaskItem(AllArmorMaterials.COPPER, p, AdvancedAges.asResource("pollution_mask")))
            .tag(forgeItemTag("armors/helmets"))
            .register();


    public static final ItemEntry<Item> MYSTERY_MEAT = REGISTRATE.item("meat", Item::new)
            .properties(p -> p.food(new FoodProperties.Builder().nutrition(10)
                    .meat()
                    .saturationMod(1)
                    .build()))
            .register();

    public static final ItemEntry<Item> MYSTERY_MEAT_SANDWICH = REGISTRATE.item("meat_sandwich", Item::new)
            .properties(p -> p.food(new FoodProperties.Builder().nutrition(15)
                    .meat()
                    .saturationMod(5)
                    .build()))
            .register();

    private static Item.Properties props() {
        return new Item.Properties().tab(ModCreativeModeTab.BIG_TAB.hideScroll());
    }

    public static void register() {
    }
}


