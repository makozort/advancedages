package net.makozort.advancedages.reg;

import com.tterrag.registrate.util.entry.FluidEntry;
import net.makozort.advancedages.AdvancedAges;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.ForgeFlowingFluid;


import static net.makozort.advancedages.AdvancedAges.REGISTRATE;


public class AllFluids {
    static String id = AdvancedAges.MOD_ID;
    static String fd = "fluid/";
    static String st = "_still";
    static String fl = "_flow";

    // you now have to add the fluids to the atlas for some stupid fucking reason im so mad oh my fucking god

    public static final FluidEntry<ForgeFlowingFluid.Flowing> HEAVY_OIL =
            REGISTRATE.fluid("heavy_oil",
                            new ResourceLocation(id,fd + "heavy_oil" + st),
                            new ResourceLocation(id,fd + "heavy_oil" + fl),
                            AllFluids.NoColorFluidAttributes::new)
                    .lang("Heavy Oil")
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    .source(ForgeFlowingFluid.Source::new)
                    .bucket()
                    .build()
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> CRUDE_OIL =
            REGISTRATE.fluid("crude_oil",
                            new ResourceLocation(id,fd + "crude_oil" + st),
                            new ResourceLocation(id,fd + "crude_oil" + fl),
                            AllFluids.NoColorFluidAttributes::new)
                    .lang("Crude Oil")
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    .source(ForgeFlowingFluid.Source::new)
                    .bucket()
                    .build()
                    .register();



    public static void register() {}

    private static class NoColorFluidAttributes extends com.simibubi.create.AllFluids.TintedFluidType {

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