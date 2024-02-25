package net.makozort.advancedages.reg;



import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.content.redstone.displayLink.source.BoilerDisplaySource;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.makozort.advancedages.content.blocks.block.horn.*;
import net.makozort.advancedages.content.blocks.block.oil.SteelFluidTankBlock;
import net.makozort.advancedages.content.fluid.tank.SteelFluidTankGenerator;
import net.makozort.advancedages.content.fluid.tank.SteelFluidTankModel;
import net.makozort.advancedages.content.items.SteelFluidTankItem;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;

import static com.simibubi.create.content.redstone.displayLink.AllDisplayBehaviours.assignDataBehaviour;
import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;
import static net.makozort.advancedages.AdvancedAges.REGISTRATE;
public class AllBlocks {

    public static final RegistryEntry<TitanHornBlock> TITAN_HORN_BLOCK = REGISTRATE
            .block("titan_horn_block", TitanHornBlock::new)
            .simpleItem()
            .transform(BlockStressDefaults.setImpact(.5))
            .blockstate((ctx, prov) -> {
                // Define the models for lit and unlit states
                ModelFile.ExistingModelFile modelLit = prov.models().getExistingFile(prov.modLoc("block/titan_horn_block_on"));

                ModelFile.ExistingModelFile modelOff = prov.models().getExistingFile(prov.modLoc("block/titan_horn_block_off"));

                // Define the blockstate variants
                prov.getVariantBuilder(ctx.get()).forAllStates(state -> {
                    boolean lit = state.getValue(TitanHornBlock.LIT);
                    Direction facing = state.getValue(TitanHornBlock.FACING);
                    int yRotation = 0;
                    if (facing == Direction.EAST) yRotation = 90;
                    else if (facing == Direction.SOUTH) yRotation = 180;
                    else if (facing == Direction.WEST) yRotation = 270;

                    return ConfiguredModel.builder()
                            .modelFile(lit ? modelLit : modelOff)
                            .rotationY(yRotation)
                            .build();
                });
            })
            .register();
    public static final RegistryEntry<GjallarHornBlock> GJALLAR_HORN_BLOCK = REGISTRATE
            .block("gjallar_horn_block", GjallarHornBlock::new)
            .simpleItem()
            .transform(BlockStressDefaults.setImpact(.5))
            .blockstate((ctx, prov) -> {
                // Define the models for lit and unlit states
                ModelFile.ExistingModelFile modelLit = prov.models().getExistingFile(prov.modLoc("block/gjallar_horn_block_on"));

                ModelFile.ExistingModelFile modelOff = prov.models().getExistingFile(prov.modLoc("block/gjallar_horn_block_off"));

                // Define the blockstate variants
                prov.getVariantBuilder(ctx.get()).forAllStates(state -> {
                    boolean lit = state.getValue(GjallarHornBlock.LIT);
                    Direction facing = state.getValue(GjallarHornBlock.FACING);
                    int yRotation = 0;
                    if (facing == Direction.EAST) yRotation = 90;
                    else if (facing == Direction.SOUTH) yRotation = 180;
                    else if (facing == Direction.WEST) yRotation = 270;

                    return ConfiguredModel.builder()
                            .modelFile(lit ? modelLit : modelOff)
                            .rotationY(yRotation)
                            .build();
                });
            })
            .register();
    public static final RegistryEntry<GrandHornBlock> GRAND_HORN_BLOCK = REGISTRATE
            .block("grand_horn_block", GrandHornBlock::new)
            .simpleItem()
            .transform(BlockStressDefaults.setImpact(.5))
            .blockstate((ctx, prov) -> {
                // Define the models for lit and unlit states
                ModelFile.ExistingModelFile modelLit = prov.models().getExistingFile(prov.modLoc("block/grand_horn_block_on"));

                ModelFile.ExistingModelFile modelOff = prov.models().getExistingFile(prov.modLoc("block/grand_horn_block_off"));

                // Define the blockstate variants
                prov.getVariantBuilder(ctx.get()).forAllStates(state -> {
                    boolean lit = state.getValue(GrandHornBlock.LIT);
                    Direction facing = state.getValue(GrandHornBlock.FACING);
                    int yRotation = 0;
                    if (facing == Direction.EAST) yRotation = 90;
                    else if (facing == Direction.SOUTH) yRotation = 180;
                    else if (facing == Direction.WEST) yRotation = 270;

                    return ConfiguredModel.builder()
                            .modelFile(lit ? modelLit : modelOff)
                            .rotationY(yRotation)
                            .build();
                });
            })
            .register();
    public static final RegistryEntry<OminousHornBlock> OMINOUS_HORN_BLOCK = REGISTRATE
            .block("ominous_horn_block", OminousHornBlock::new)
            .simpleItem()
            .transform(BlockStressDefaults.setImpact(.5))
            .blockstate((ctx, prov) -> {
                // Define the models for lit and unlit states
                ModelFile.ExistingModelFile modelLit = prov.models().getExistingFile(prov.modLoc("block/ominous_horn_block_on"));

                ModelFile.ExistingModelFile modelOff = prov.models().getExistingFile(prov.modLoc("block/ominous_horn_block_off"));

                // Define the blockstate variants
                prov.getVariantBuilder(ctx.get()).forAllStates(state -> {
                    boolean lit = state.getValue(OminousHornBlock.LIT);
                    Direction facing = state.getValue(OminousHornBlock.FACING);
                    int yRotation = 0;
                    if (facing == Direction.EAST) yRotation = 90;
                    else if (facing == Direction.SOUTH) yRotation = 180;
                    else if (facing == Direction.WEST) yRotation = 270;

                    return ConfiguredModel.builder()
                            .modelFile(lit ? modelLit : modelOff)
                            .rotationY(yRotation)
                            .build();
                });
            })
            .register();
    public static final RegistryEntry<ReaperHornBlock> REAPER_HORN_BLOCK = REGISTRATE
            .block("reaper_horn_block", ReaperHornBlock::new)
            .simpleItem()
            .transform(BlockStressDefaults.setImpact(.5))
            .blockstate((ctx, prov) -> {
                // Define the models for lit and unlit states
                ModelFile.ExistingModelFile modelLit = prov.models().getExistingFile(prov.modLoc("block/reaper_horn_block_on"));

                ModelFile.ExistingModelFile modelOff = prov.models().getExistingFile(prov.modLoc("block/reaper_horn_block_off"));

                // Define the blockstate variants
                prov.getVariantBuilder(ctx.get()).forAllStates(state -> {
                    boolean lit = state.getValue(ReaperHornBlock.LIT);
                    Direction facing = state.getValue(ReaperHornBlock.FACING);
                    int yRotation = 0;
                    if (facing == Direction.EAST) yRotation = 90;
                    else if (facing == Direction.SOUTH) yRotation = 180;
                    else if (facing == Direction.WEST) yRotation = 270;

                    return ConfiguredModel.builder()
                            .modelFile(lit ? modelLit : modelOff)
                            .rotationY(yRotation)
                            .build();
                });
            })
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