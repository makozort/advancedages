package net.makozort.advancedages.reg;


import com.tterrag.registrate.util.entry.BlockEntry;
import net.makozort.advancedages.content.modblocks.block.TitanHornBlock;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;;
import static net.makozort.advancedages.ModRegistrate.REGISTRATE;
public class AllBlocks {
    static {
        REGISTRATE.creativeModeTab(() -> ModCreativeModeTab.BIG_TAB);
    }
    public static final BlockEntry<LiquidBlock> CRUDE_OIL_BLOCK = REGISTRATE.block("crude_oil_block",b ->
                    new LiquidBlock(Allfluids.SOURCE_CRUDE_OIL, BlockBehaviour.Properties.copy(Blocks.LAVA)))
            .lang("Crude Oil")
            .register();
    public static final BlockEntry<TitanHornBlock> TITAN_HORN_BLOCK = REGISTRATE
            .block("titan_horn_block", TitanHornBlock::new)
            .properties(p -> p.destroyTime(1))
            .simpleItem()
            .register();
    public static final BlockEntry<LiquidBlock> REFINED_OIL_BLOCK = REGISTRATE.block("refined_oil_block",b ->
                    new LiquidBlock(Allfluids.SOURCE_REFINED_OIL, BlockBehaviour.Properties.copy(Blocks.WATER)))
            .lang("Refined Oil")
            .register();
    public static void register() {
    }
}