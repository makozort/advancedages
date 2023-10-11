package net.makozort.advancedages.reg;


import com.simibubi.create.foundation.data.BlockStateGen;
import com.tterrag.registrate.util.entry.BlockEntry;

//import net.makozort.advancedages.content.Modblocks.block.OilRefineryBlock;
import net.makozort.advancedages.content.modblocks.block.OilRefineryBlock;

import net.minecraft.world.level.block.*;

import net.minecraft.world.level.block.state.BlockBehaviour;


import static net.makozort.advancedages.ModRegistrate.REGISTRATE;


public class AllBlocks {
    static {
        REGISTRATE.creativeModeTab(() -> ModCreativeModeTab.BIG_TAB);
    }


    public static final BlockEntry<LiquidBlock> CRUDE_OIL_BLOCK = REGISTRATE.block("crude_oil_block",b ->
                    new LiquidBlock(Allfluids.SOURCE_CRUDE_OIL, BlockBehaviour.Properties.copy(Blocks.WATER)))
            .lang("Crude Oil")
            .register();

    public static final BlockEntry<OilRefineryBlock> OIL_REFINERY_BLOCK = REGISTRATE
            .block("oil_refinery", OilRefineryBlock::new)
            .blockstate((BlockStateGen.horizontalBlockProvider(false)))
            .simpleItem()
            .register();
    public static final BlockEntry<LiquidBlock> REFINED_OIL_BLOCK = REGISTRATE.block("refined_oil_block",b ->
                    new LiquidBlock(Allfluids.SOURCE_REFINED_OIL, BlockBehaviour.Properties.copy(Blocks.WATER)))
            .lang("Refined Oil")
            .register();



    public static void register() {

    }
}