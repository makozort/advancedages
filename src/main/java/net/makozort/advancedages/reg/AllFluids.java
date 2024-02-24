package net.makozort.advancedages.reg;

import com.tterrag.registrate.util.entry.FluidEntry;
import net.makozort.advancedages.content.fluid.VirtualGas;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.ForgeFlowingFluid;


import static net.makozort.advancedages.AdvancedAges.REGISTRATE;


public class AllFluids {

    public static final FluidEntry<ForgeFlowingFluid.Flowing> HEAVY_OIL =
            REGISTRATE.fluid("heavy_oil",
                            AllFluids.NoColorFluidAttributes::new)
                    .lang("Heavy Oil")
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    .source(ForgeFlowingFluid.Source::new)
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> CRUDE_OIL =
            REGISTRATE.fluid("crude_oil",
                            AllFluids.NoColorFluidAttributes::new)
                    .lang("Crude Oil")
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    .source(ForgeFlowingFluid.Source::new)
                    .bucket().tab(AllCreativeModeTabs.BASE_CREATIVE_TAB.getKey())
                    .build()
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> LIQUID_MEAT =
            REGISTRATE.fluid("liquid_meat")
                    .lang("Liquid Meat")
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    .source(ForgeFlowingFluid.Source::new)
                    .register();

    //Gasses
    public static final FluidEntry<VirtualGas> CARBON_DIOXIDE = REGISTRATE
            .virtualGas("preheated_air").lang("Preheated Air").register();
    public static final FluidEntry<VirtualGas> AIR = REGISTRATE
            .virtualGas("preheated_air").lang("Preheated Air").register();
    public static final FluidEntry<VirtualGas> NATURAL_GAS = REGISTRATE
            .virtualGas("preheated_air").lang("Preheated Air").register();




    public static void register() {}

    public static class NoColorFluidAttributes extends com.simibubi.create.AllFluids.TintedFluidType {

        public NoColorFluidAttributes(Properties properties, ResourceLocation stillTexture,
                                      ResourceLocation flowingTexture) {
            super(properties, stillTexture, flowingTexture);
        }

        @Override
        protected int getTintColor(FluidStack stack) {
            return NO_TINT;
        }

        @Override
        public int getTintColor(FluidState state, BlockAndTintGetter world, BlockPos pos) {
            return 0x00ffffff;
        }

    }


    // look at createDD for fluid interaction stuff if needed

}