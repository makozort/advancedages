package net.makozort.advancedages.reg;

import com.tterrag.registrate.util.entry.FluidEntry;
import net.makozort.advancedages.foundation.gas.VirtualGas;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.ForgeFlowingFluid;


import static net.makozort.advancedages.AdvancedAges.REGISTRATE;
import static net.makozort.advancedages.AdvancedAges.asFluid;


public class AllFluids {

    // fluids among other things uses the texture atlas now

    public static final FluidEntry<ForgeFlowingFluid.Flowing> HEAVY_OIL =
            REGISTRATE.fluid("heavy_oil",
                            asFluid("heavy_oil", false),
                            asFluid("heavy_oil", true),
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
                            asFluid("crude_oil", false),
                            asFluid("crude_oil", true),
                            AllFluids.NoColorFluidAttributes::new)
                    .lang("Crude Oil")
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    .source(ForgeFlowingFluid.Source::new)
                    .bucket().tab(AllCreativeModeTabs.BASE_TAB.getKey())
                    .build()
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> LIQUID_MEAT =
            REGISTRATE.fluid("liquid_meat",
                            asFluid("liquid_meat", false),
                            asFluid("liquid_meat", true),
                            AllFluids.NoColorFluidAttributes::new)
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