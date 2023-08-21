package net.makozort.advancedages.reg;


import com.simibubi.create.Create;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockEntity;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockEntityInstance;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockEntityRenderer;
import com.simibubi.create.content.schematics.table.SchematicTableBlockEntity;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.makozort.advancedages.content.Modblocks.entity.OilRefineryBlockEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

import static net.makozort.advancedages.ModRegistrate.REGISTRATE;

public class AllBlockEntitys {

    static {
        REGISTRATE.creativeModeTab(() -> ModCreativeModeTab.BIG_TAB);
    }


    public static final BlockEntityEntry<OilRefineryBlockEntity> OIL_REFINERY_BLOCK_ENTITY = REGISTRATE
            .blockEntity("oil_refinery_block_entity", OilRefineryBlockEntity::new)
            .validBlocks(AllBlocks.OIL_REFINERY_BLOCK)
            .register();

    public static void register() {
    }
}
