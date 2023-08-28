package net.makozort.advancedages.reg;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.makozort.advancedages.content.modblocks.entity.OilRefineryBlockEntity;

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
