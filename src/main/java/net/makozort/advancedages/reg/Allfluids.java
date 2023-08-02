package net.makozort.advancedages.reg;

import com.simibubi.create.AllFluids;
import net.makozort.advancedages.content.fluid.ModFluidTypes;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.makozort.advancedages.Advancedages;

public class Allfluids {
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(ForgeRegistries.FLUIDS, Advancedages.MOD_ID);

    public static final RegistryObject<FlowingFluid> SOURCE_CRUDE_OIL = FLUIDS.register("crude_oil_fluid",
            () -> new ForgeFlowingFluid.Source(Allfluids.CRUDE_OIL_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_CRUDE_OIL = FLUIDS.register("flowing_crude_oil",
            () -> new ForgeFlowingFluid.Flowing(Allfluids.CRUDE_OIL_FLUID_PROPERTIES));

    public static final ForgeFlowingFluid.Properties CRUDE_OIL_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluidTypes.CRUDE_OIL_FLUID_TYPE, SOURCE_CRUDE_OIL, FLOWING_CRUDE_OIL)
            .slopeFindDistance(3).levelDecreasePerBlock(2).block(AllBlocks.CRUDE_OIL_BLOCK)
            .bucket(Allitems.CRUDE_OIL_BUCKET)
            .tickRate(30);



    public static final RegistryObject<FlowingFluid> SOURCE_REFINED_OIL = FLUIDS.register("refined_oil_fluid",
            () -> new ForgeFlowingFluid.Source(Allfluids.REFINED_OIL_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_REFINED_OIL = FLUIDS.register("flowing_refined_oil",
            () -> new ForgeFlowingFluid.Flowing(Allfluids.REFINED_OIL_FLUID_PROPERTIES));

    public static final ForgeFlowingFluid.Properties REFINED_OIL_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluidTypes.REFINED_OIL_FLUID_TYPE, SOURCE_REFINED_OIL, FLOWING_REFINED_OIL)
            .slopeFindDistance(3).levelDecreasePerBlock(2).block(AllBlocks.REFINED_OIL_BLOCK)
            .bucket(Allitems.REFINED_OIL_BUCKET)
            .tickRate(30);



    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}