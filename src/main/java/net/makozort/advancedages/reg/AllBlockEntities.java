package net.makozort.advancedages.reg;


import com.simibubi.create.Create;
import com.simibubi.create.content.kinetics.millstone.MillstoneBlockEntity;
import com.simibubi.create.content.kinetics.millstone.MillstoneCogInstance;
import com.simibubi.create.content.kinetics.millstone.MillstoneRenderer;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.makozort.advancedages.AdvancedAges;
import net.makozort.advancedages.content.blocks.Entity.*;
import net.makozort.advancedages.content.fluid.tank.SteelFluidTankRenderer;

import static net.makozort.advancedages.AdvancedAges.REGISTRATE;

public class AllBlockEntities {

    static {
        REGISTRATE.setCreativeTab(AllCreativeModeTabs.BASE_TAB);
    }
    public static final BlockEntityEntry<HornBlockEntity> HORN_BLOCK = REGISTRATE
            .blockEntity("horn_block", HornBlockEntity::new)
            .validBlocks(AllBlocks.TITAN_HORN_BLOCK, AllBlocks.REAPER_HORN_BLOCK, AllBlocks.GJALLAR_HORN_BLOCK, AllBlocks.GRAND_HORN_BLOCK, AllBlocks.OMINOUS_HORN_BLOCK)
            .register();
    public static final BlockEntityEntry<SteelFluidTankBlockEntity> STEEL_FLUID_TANK = REGISTRATE
            .blockEntity("steel_fluid_tank", SteelFluidTankBlockEntity::new)
            .validBlocks(AllBlocks.STEEL_FLUID_TANK)
            .renderer(() -> SteelFluidTankRenderer::new)
            .register();
    public static final BlockEntityEntry<OilFilterBlockEntity> OIL_FILTER = REGISTRATE
            .blockEntity("oil_filter", OilFilterBlockEntity::new)
            .instance(() -> OilFilterCogInstance::new, false)
            .validBlocks(AllBlocks.OIL_FILTER)
            .renderer(() -> OilFilterRenderer::new)
            .register();
    public static void register() {
    }
}
