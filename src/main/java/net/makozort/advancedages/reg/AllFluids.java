package net.makozort.advancedages.reg;

import net.makozort.advancedages.AdvancedAges;
import net.makozort.advancedages.content.fluid.ModFluidTypes;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AllFluids {
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(ForgeRegistries.FLUIDS, AdvancedAges.MOD_ID);


    public static final RegistryObject<ForgeFlowingFluid.Source> SOURCE_CRUDE_OIL = FLUIDS.register("crude_oil_fluid",
            () -> new ForgeFlowingFluid.Source(AllFluids.CRUDE_OIL_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_CRUDE_OIL = FLUIDS.register("flowing_crude_oil",
            () -> new ForgeFlowingFluid.Flowing(AllFluids.CRUDE_OIL_FLUID_PROPERTIES));

    public static final ForgeFlowingFluid.Properties CRUDE_OIL_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluidTypes.CRUDE_OIL_FLUID_TYPE, SOURCE_CRUDE_OIL, FLOWING_CRUDE_OIL)
            .slopeFindDistance(3).levelDecreasePerBlock(2).block(AllBlocks.CRUDE_OIL_BLOCK)
            .bucket(Allitems.CRUDE_OIL_BUCKET)
            .tickRate(30);


    public static final RegistryObject<FlowingFluid> SOURCE_HEAVY_OIL = FLUIDS.register("heavy_oil_fluid",
            () -> new ForgeFlowingFluid.Source(AllFluids.HEAVY_OIL_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_HEAVY_OIL = FLUIDS.register("flowing_heavy_oil",
            () -> new ForgeFlowingFluid.Flowing(AllFluids.HEAVY_OIL_FLUID_PROPERTIES));

    public static final ForgeFlowingFluid.Properties HEAVY_OIL_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluidTypes.HEAVY_OIL_TYPE, SOURCE_HEAVY_OIL, FLOWING_HEAVY_OIL)
            .slopeFindDistance(3).levelDecreasePerBlock(2).block(AllBlocks.HEAVY_OIL_BLOCK)
            .bucket(Allitems.HEAVY_OIL_BUCKET)
            .tickRate(30);



    //public static final RegistryObject<FlowingFluid> SOURCE_LIQUID_MEAT = FLUIDS.register("liquid_meat_fluid",
    //        () -> new ForgeFlowingFluid.Source(AllFluids.LIQUID_MEAT_FLUID_PROPERTIES));
    //public static final RegistryObject<FlowingFluid> FLOWING_LIQUID_MEAT = FLUIDS.register("flowing_liquid_meat",
    //        () -> new ForgeFlowingFluid.Flowing(AllFluids.LIQUID_MEAT_FLUID_PROPERTIES));

    //public static final ForgeFlowingFluid.Properties LIQUID_MEAT_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
    //        ModFluidTypes.LIQUID_MEAT_TYPE, SOURCE_LIQUID_MEAT, FLOWING_LIQUID_MEAT)
    //        .slopeFindDistance(3).levelDecreasePerBlock(2).block(AllBlocks.LIQUID_MEAT_BLOCK)
    //        .bucket(Allitems.LIQUID_MEAT_BUCKET)
    //        .tickRate(30);

    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}