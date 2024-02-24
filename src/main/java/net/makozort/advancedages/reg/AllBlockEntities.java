package net.makozort.advancedages.reg;


import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.makozort.advancedages.AdvancedAges;
import net.makozort.advancedages.content.blocks.Entity.HornBlockEntity;

public class AllBlockEntities {

    static {
        AdvancedAges.REGISTRATE.setCreativeTab(AllCreativeModeTabs.BASE_CREATIVE_TAB);
    }

    public static final BlockEntityEntry<HornBlockEntity> HORN_BLOCK = REGISTRATE
            .blockEntity("horn_block", HornBlockEntity::new)
            .validBlocks(AllBlocks.TITAN_HORN_BLOCK, AllBlocks.REAPER_HORN_BLOCK, AllBlocks.GJALLAR_HORN_BLOCK, AllBlocks.GRAND_HORN_BLOCK, AllBlocks.OMINOUS_HORN_BLOCK)
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
