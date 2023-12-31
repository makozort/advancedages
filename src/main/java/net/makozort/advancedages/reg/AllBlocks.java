package net.makozort.advancedages.reg;


import com.simibubi.create.content.redstone.displayLink.source.BoilerDisplaySource;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.makozort.advancedages.content.blocks.block.oil.OilFilterBlock;
import net.makozort.advancedages.content.blocks.block.oil.SteelFluidTankBlock;
import net.makozort.advancedages.content.fluid.tank.SteelFluidTankGenerator;
import net.makozort.advancedages.content.items.SteelFluidTankItem;
import net.makozort.advancedages.content.fluid.tank.SteelFluidTankModel;
import net.makozort.advancedages.content.blocks.block.hornblocks.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

import static com.simibubi.create.content.redstone.displayLink.AllDisplayBehaviours.assignDataBehaviour;
import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;
import static net.makozort.advancedages.ModRegistrate.REGISTRATE;

public class AllBlocks {
    static {
        REGISTRATE.creativeModeTab(() -> ModCreativeModeTab.BIG_TAB);
    }

    public static final BlockEntry<TitanHornBlock> TITAN_HORN_BLOCK = REGISTRATE
            .block("titan_horn_block", TitanHornBlock::new)
            .properties(p -> p.destroyTime(1))
            .simpleItem()
            .register();
    public static final BlockEntry<GjallarHornBlock> GJALLAR_HORN_BLOCK = REGISTRATE
            .block("gjallar_horn_block", GjallarHornBlock::new)
            .properties(p -> p.destroyTime(1))
            .simpleItem()
            .register();
    public static final BlockEntry<GrandHornBlock> GRAND_HORN_BLOCK = REGISTRATE
            .block("grand_horn_block", GrandHornBlock::new)
            .properties(p -> p.destroyTime(1))
            .simpleItem()
            .register();
    public static final BlockEntry<OminousHornBlock> OMINOUS_HORN_BLOCK = REGISTRATE
            .block("ominous_horn_block", OminousHornBlock::new)
            .properties(p -> p.destroyTime(1))
            .simpleItem()
            .register();
    public static final BlockEntry<ReaperHornBlock> REAPER_HORN_BLOCK = REGISTRATE
            .block("reaper_horn_block", ReaperHornBlock::new)
            .properties(p -> p.destroyTime(1))
            .simpleItem()
            .register();


    public static final BlockEntry<LiquidBlock> CRUDE_OIL_BLOCK = REGISTRATE
            .block("crude_oil_block", b ->
                    new LiquidBlock(AllFluids.SOURCE_CRUDE_OIL, BlockBehaviour.Properties.copy(Blocks.LAVA)))
            .lang("Crude Oil")
            .register();
    public static final BlockEntry<LiquidBlock> HEAVY_OIL_BLOCK = REGISTRATE
            .block("heavy_oil_block", b ->
                    new LiquidBlock(AllFluids.SOURCE_HEAVY_OIL, BlockBehaviour.Properties.copy(Blocks.WATER)))
            .lang("Heavy Oil")
            .register();
    public static final BlockEntry<SteelFluidTankBlock> STEEL_FLUID_TANK = REGISTRATE
            .block("steel_fluid_tank", SteelFluidTankBlock::regular)
            .initialProperties(SharedProperties::copperMetal)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .properties(p -> p.isRedstoneConductor((p1, p2, p3) -> true))
            .transform(pickaxeOnly())
            .blockstate(new SteelFluidTankGenerator()::generate)
            .onRegister(CreateRegistrate.blockModel(() -> SteelFluidTankModel::standard))
            .onRegister(assignDataBehaviour(new BoilerDisplaySource(), "boiler_status"))
            .addLayer(() -> RenderType::cutoutMipped)
            .item(SteelFluidTankItem::new)
            .model(AssetLookup.customBlockItemModel("_", "block_single_window"))
            .build()
            .register();
    public static final BlockEntry<OilFilterBlock> OIL_FILTER = REGISTRATE
            .block("oil_filter", OilFilterBlock::new)
            .initialProperties(SharedProperties::copperMetal)
            .transform(pickaxeOnly())
            .lang("Oil Filter")
            .simpleItem()
            .register();
    public static void register() {
    }
}