package net.makozort.advancedages.reg;

import com.tterrag.registrate.util.entry.FluidEntry;
import net.makozort.advancedages.foundation.fluid.NoColorFluidAttributes;
import net.makozort.advancedages.foundation.gas.VirtualGas;
import net.minecraftforge.fluids.ForgeFlowingFluid;


import static net.makozort.advancedages.AdvancedAges.REGISTRATE;
import static net.makozort.advancedages.AdvancedAges.asFluid;


public class AllFluids {

    // fluids among other things uses the texture atlas now

    public static final FluidEntry<ForgeFlowingFluid.Flowing> HEAVY_OIL =
            REGISTRATE.fluid("heavy_oil")
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
            REGISTRATE.fluid("crude_oil")
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
            .virtualGas("carbon_dioxide", VirtualGas::new).lang("Carbon Dioxide").register();

    public static final FluidEntry<VirtualGas> NATURAL_GAS = REGISTRATE
            .virtualGas("natural_gas", VirtualGas::new).lang("Natural Gas").register();

    public static final FluidEntry<VirtualGas> OXYGEN = REGISTRATE
            .virtualGas("natural_gas", VirtualGas::new).lang("Natural Gas").register();


    public static void register() {}

}