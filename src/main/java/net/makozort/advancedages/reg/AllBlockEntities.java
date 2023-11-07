package net.makozort.advancedages.reg;


import com.simibubi.create.Create;
import com.simibubi.create.content.fluids.tank.CreativeFluidTankBlockEntity;
import com.simibubi.create.content.fluids.tank.FluidTankBlockEntity;
import com.simibubi.create.content.fluids.tank.FluidTankRenderer;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.makozort.advancedages.content.fluid.tank.SteelCreativeFluidTankBlockEntity;
import net.makozort.advancedages.content.fluid.tank.SteelFluidTankBlockEntity;
import net.makozort.advancedages.content.fluid.tank.SteelFluidTankModel;
import net.makozort.advancedages.content.fluid.tank.SteelFluidTankRenderer;
import net.makozort.advancedages.content.modblocks.Entity.HornBlockEntity;

import static net.makozort.advancedages.ModRegistrate.REGISTRATE;

public class AllBlockEntities {

    static {
        REGISTRATE.creativeModeTab(() -> ModCreativeModeTab.BIG_TAB);
    }

    public static final BlockEntityEntry<HornBlockEntity> HORN_BLOCK = REGISTRATE
            .blockEntity("horn_block", HornBlockEntity::new)
            .validBlocks(AllBlocks.TITAN_HORN_BLOCK, AllBlocks.REAPER_HORN_BLOCK, AllBlocks.GJALLAR_HORN_BLOCK, AllBlocks.GRAND_HORN_BLOCK, AllBlocks.OMINOUS_HORN_BLOCK)
            .register();

    public static final BlockEntityEntry<SteelCreativeFluidTankBlockEntity> STEEL_CREATIVE_FLUID_TANK = Create.REGISTRATE
            .blockEntity("steel_creative_fluid_tank", SteelCreativeFluidTankBlockEntity::new)
            .validBlocks(com.simibubi.create.AllBlocks.CREATIVE_FLUID_TANK)
            .renderer(() -> SteelFluidTankRenderer::new)
            .register();

    public static final BlockEntityEntry<SteelFluidTankBlockEntity> STEEL_FLUID_TANK = Create.REGISTRATE
            .blockEntity("steel_fluid_tank", SteelFluidTankBlockEntity::new)
            .validBlocks(com.simibubi.create.AllBlocks.FLUID_TANK)
            .renderer(() -> SteelFluidTankRenderer::new)
            .register();
    public static void register() {
    }
}
