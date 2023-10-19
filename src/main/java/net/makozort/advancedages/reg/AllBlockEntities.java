package net.makozort.advancedages.reg;


import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.makozort.advancedages.content.modblocks.Entity.HornBlockEntity;


import static net.makozort.advancedages.ModRegistrate.REGISTRATE;

public class AllBlockEntities {

    static {
        REGISTRATE.creativeModeTab(() -> ModCreativeModeTab.BIG_TAB);
    }
    public static final BlockEntityEntry<HornBlockEntity> HORN_BLOCK = REGISTRATE
            .blockEntity("horn_block", HornBlockEntity::new)
            .validBlocks(AllBlocks.TITAN_HORN_BLOCK)
            .register();


    public static void register() {
    }
}
