package net.makozort.advancedages.reg;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ModCreativeModeTab {
    public static final CreativeModeTab BIG_TAB = new CreativeModeTab("bigtab") {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(Allitems.HEAVY_OIL_BUCKET.get());
        }
    };


    public static void register() {

    }
}