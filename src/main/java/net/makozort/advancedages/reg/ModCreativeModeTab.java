package net.makozort.advancedages.reg;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.makozort.advancedages.reg.AllBlocks;
import org.jetbrains.annotations.NotNull;

public class ModCreativeModeTab {
    public static final CreativeModeTab BIG_TAB = new CreativeModeTab("bigtab") {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(AllBlocks.TIN_ORE.get());
        }
    };


    public static void register() {

    }
}