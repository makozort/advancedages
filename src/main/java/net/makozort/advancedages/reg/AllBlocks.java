package net.makozort.advancedages.reg;



import com.simibubi.create.content.redstone.displayLink.source.BoilerDisplaySource;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.makozort.advancedages.content.blocks.block.horn.HornBlock;
import net.makozort.advancedages.content.blocks.block.oil.SteelFluidTankBlock;
import net.makozort.advancedages.content.fluid.tank.SteelFluidTankGenerator;
import net.makozort.advancedages.content.fluid.tank.SteelFluidTankModel;
import net.makozort.advancedages.content.items.SteelFluidTankItem;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import static com.simibubi.create.content.redstone.displayLink.AllDisplayBehaviours.assignDataBehaviour;
import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;
import static net.makozort.advancedages.AdvancedAges.REGISTRATE;
import static net.makozort.advancedages.reg.AllSoundEvents.*;

public class AllBlocks {

    public static final RegistryEntry<HornBlock>
    TITAN_HORN_BLOCK = REGISTRATE
            .block("titan_horn_block",
                    p -> new HornBlock(p, 160, TITAN_HORN))
            .register(),
    GJALLAR_HORN_BLOCK = REGISTRATE
            .block("gjallar_horn_block",
                    p -> new HornBlock(p, 300, GJALLAR_HORN))
            .register(),
    GRAND_HORN_BLOCK = REGISTRATE
            .block("grand_horn_block",
                    p -> new HornBlock(p, 300, GRAND_HORN))
            .register(),
    OMINOUS_HORN_BLOCK = REGISTRATE
            .block("ominous_horn_block",
                    p -> new HornBlock(p, 160, OMINOUS_HORN))
            .register(),
    REAPER_HORN_BLOCK = REGISTRATE
            .block("reaper_horn_block",
                    p -> new HornBlock(p, 40, REAPER_HORN))
            .register();

    //public static final BlockEntry<LiquidBlock> LIQUID_MEAT_BLOCK = REGISTRATE
    //        .block("liquid_meat_block", b ->
    //                new LiquidBlock(AllFluids.SOURCE_LIQUID_MEAT, BlockBehaviour.Properties.copy(Blocks.WATER)))
    //        .lang("Liquid Meat")
    //        .register();

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

    //public static final BlockEntry<OilFilterBlock> OIL_FILTER = REGISTRATE
    //        .block("oil_filter", OilFilterBlock::new)
    //        .initialProperties(SharedProperties::copperMetal)
    //        .transform(pickaxeOnly())
    //        .lang("Oil Filter")
    //        .simpleItem()
    //        .register();
    public static void register() {
    }
}