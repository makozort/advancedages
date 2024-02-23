package net.makozort.advancedages.reg;


import com.simibubi.create.Create;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.makozort.advancedages.AdvancedAges;
import net.makozort.advancedages.content.blocks.Entity.OilFilterBlockEntity;
import net.makozort.advancedages.content.blocks.Entity.HornBlockEntity;
import net.minecraft.world.level.block.Block;

import static net.makozort.advancedages.ModRegistrate.REGISTRATE;

public class AllBlockEntities {

    static {
        AdvancedAges.REGISTRATE.setCreativeTab(AllCreativeModeTabs.BASE_CREATIVE_TAB);
    }

    public static final BlockEntityEntry<HornBlockEntity> HORN_BLOCK = REGISTRATE
            .blockEntity("horn_block", HornBlockEntity::new)
            .validBlocks((NonNullSupplier<? extends Block>) AllBlocks.TITAN_HORN_BLOCK, (NonNullSupplier<? extends Block>) AllBlocks.REAPER_HORN_BLOCK, (NonNullSupplier<? extends Block>) AllBlocks.GJALLAR_HORN_BLOCK, (NonNullSupplier<? extends Block>) AllBlocks.GRAND_HORN_BLOCK,(NonNullSupplier<? extends Block>) AllBlocks.OMINOUS_HORN_BLOCK)
            .register();

    //public static final BlockEntityEntry<SteelFluidTankBlockEntity> STEEL_FLUID_TANK = Create.REGISTRATE
    //        .blockEntity("steel_fluid_tank", SteelFluidTankBlockEntity::new)
    //        .validBlocks(AllBlocks.STEEL_FLUID_TANK)
    //        .renderer(() -> SteelFluidTankRenderer::new)
    //        .register();

    //public static final BlockEntityEntry<OilFilterBlockEntity> OIL_FILTER = Create.REGISTRATE
    //        .blockEntity("oil_filter", OilFilterBlockEntity::new)
    //        .validBlocks(AllBlocks.OIL_FILTER)
    //        .register();
    public static void register() {
    }
}
