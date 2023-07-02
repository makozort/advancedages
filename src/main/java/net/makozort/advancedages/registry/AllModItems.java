package net.makozort.advancedages.registry;

import com.tterrag.registrate.util.entry.ItemEntry;
import net.makozort.advancedages.Advancedages;
import net.makozort.advancedages.ModCreativeModeTab;
import net.makozort.advancedages.ModTags;
import net.makozort.advancedages.ModTiers;
import net.makozort.advancedages.content.item.SpearItem;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;

public class AllModItems {
    static {
        Advancedages.REGISTRATE.creativeModeTab(() -> ModCreativeModeTab.COOL_TAB);
    }
    // Raw items
    public static final ItemEntry<Item> RAW_TIN =
            taggedBasicItem("raw_tin", ModTags.forgeItemTag("raw_materials"), ModTags.forgeItemTag("raw_materials/tin"));
    // Refined materials
    public static final ItemEntry<Item>
            TIN_INGOT = taggedBasicItem("tin_ingot", ModTags.forgeItemTag("ingots/tin"), ModTags.forgeItemTag("ingots")),
            TIN_NUGGET = taggedBasicItem("tin_nugget", ModTags.forgeItemTag("nuggets/tin"), ModTags.forgeItemTag("nuggets")),
            BRONZE_INGOT = taggedBasicItem("bronze_ingot", ModTags.forgeItemTag("ingots/bronze"), ModTags.forgeItemTag("ingots")),
            BRONZE_NUGGET = taggedBasicItem("bronze_nugget", ModTags.forgeItemTag("nuggets/bronze"), ModTags.forgeItemTag("nuggets")),
            SMALL_SAND_CAST = basicItem("small_sand_cast"),
            LARGE_SAND_CAST = basicItem("large_sand_cast");
    public static final ItemEntry<Item> BRONZE_GEAR = Advancedages.REGISTRATE.item("bronze_gear",Item::new)
            //.model(p -> p.getExistingFile())
            .register();
    public static final ItemEntry<Item> LARGE_BRONZE_GEAR = Advancedages.REGISTRATE.item("large_bronze_gear",Item::new)
            .register();
    public static final ItemEntry<BucketItem> MOLTEN_BRONZE_BUCKET = Advancedages.REGISTRATE.item("molten_bronze_bucket",
                    p -> new BucketItem(ModFluids.SOURCE_MOLTEN_BRONZE,props().craftRemainder(Items.BUCKET).stacksTo(1)))
            .register();
    public static final ItemEntry<Item> BRONZE_SHEET = Advancedages.REGISTRATE.item("bronze_plate", Item::new)
            .tag(ModTags.forgeItemTag("plates")).tag(ModTags.forgeItemTag("plates/bronze"))
            .lang("Bronze Sheet")
            .register();
    // Bronze tools/uniques
    public static final ItemEntry<SpearItem> BRONZE_SPEAR = Advancedages.REGISTRATE.item("bronze_spear", SpearItem::new)
            .properties(p -> p.defaultDurability(50))
            .register();
    /*public static final EntityType<ThrownSpear> THROWN_BRONZE_SPEAR = REGISTRATE.object("thrown_bronze_spear")
            .entity(ThrownSpear::new, MobCategory.MISC)
            .loot((tb, e) -> tb.add(e, LootTable.lootTable()))
            .renderer(() -> new ThrownSpearRenderer())
            .register();*/
    public static final ItemEntry<SwordItem> BRONZE_SWORD =
            Advancedages.REGISTRATE.item("bronze_sword", p -> new SwordItem(ModTiers.BRONZE,3,-2.4f,props()))
                    .lang("Bronze Gladius")
                    .register();
    public static final ItemEntry<PickaxeItem> BRONZE_PICKAXE =
            Advancedages.REGISTRATE.item("bronze_pickaxe", p -> new PickaxeItem(ModTiers.BRONZE,1,-2.8f,props()))
                    .register();
    public static final ItemEntry<ShovelItem> BRONZE_SHOVEL =
            Advancedages.REGISTRATE.item("bronze_shovel", p -> new ShovelItem(ModTiers.BRONZE,1.5f,-3.0f,props()))
                    .register();
    public static final ItemEntry<AxeItem> BRONZE_AXE =
            Advancedages.REGISTRATE.item("bronze_axe", p -> new AxeItem(ModTiers.BRONZE, 6.0f, -3.1f,props()))
                    .register();
    public static final ItemEntry<HoeItem> BRONZE_HOE =
            Advancedages.REGISTRATE.item("bronze_hoe", p -> new HoeItem(ModTiers.BRONZE, -2,-1.0f,props()))
                    .register();
    // Mass item methods
    private static ItemEntry<Item> basicItem(String name) {
        return Advancedages.REGISTRATE.item(name, Item::new)
                .register();
    }
    private static ItemEntry<Item> taggedBasicItem(String name, TagKey<Item>... tags) {
        return Advancedages.REGISTRATE.item(name, Item::new)
                .tag(tags)
                .register();
    }
    private static Item.Properties props() {
        return new Item.Properties().tab(ModCreativeModeTab.COOL_TAB);
    }
    // Load this class
    public static void register() {}
}
