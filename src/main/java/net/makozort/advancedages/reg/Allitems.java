package net.makozort.advancedages.reg;

import com.simibubi.create.AllTags;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import static net.makozort.advancedages.ModRegistrate.REGISTRATE;

public class Allitems {

    static {
        REGISTRATE.creativeModeTab(() -> ModCreativeModeTab.BIG_TAB);
    }
    public static final ItemEntry<BucketItem> CRUDE_OIL_BUCKET = REGISTRATE.item("crude_oil_bucket",
                    p -> new BucketItem(Allfluids.SOURCE_CRUDE_OIL,props().craftRemainder(Items.BUCKET).stacksTo(1)))
            .register();


    public static final ItemEntry<BucketItem> REFINED_OIL_BUCKET = REGISTRATE.item("refined_oil_bucket",
                    p -> new BucketItem(Allfluids.SOURCE_REFINED_OIL,props().craftRemainder(Items.BUCKET).stacksTo(1)))
            .tag(AllTags.AllItemTags.BLAZE_BURNER_FUEL_SPECIAL.tag, AllTags.AllItemTags.UPRIGHT_ON_BELT.tag)
            .register();


    private static Item.Properties props() {
        return new Item.Properties().tab(ModCreativeModeTab.BIG_TAB.hideScroll());
    }
    public static void register() {}
}


