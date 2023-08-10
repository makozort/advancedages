package net.makozort.advancedages.content.fluid;

import com.mojang.math.Vector3f;
import net.makozort.advancedages.AdvancedAges;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFluidTypes {

    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, net.makozort.advancedages.AdvancedAges.MOD_ID);

    public static final RegistryObject<FluidType> CRUDE_OIL_FLUID_TYPE = register("crude_oil_fluid",
            FluidType.Properties.create().lightLevel(15).density(3000).viscosity(6000)
                    .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_LAVA)
                    .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA)
                    .temperature(1500).pathType(BlockPathTypes.LAVA).adjacentPathType(null)
                    .canSwim(true).canDrown(true).fallDistanceModifier(-1),
            new ResourceLocation(AdvancedAges.MOD_ID, "block/crude_oil_still"), new ResourceLocation(net.makozort.advancedages.AdvancedAges.MOD_ID, "block/crude_oil_flow"), new ResourceLocation(net.makozort.advancedages.AdvancedAges.MOD_ID, "misc/in_crude_oil") // <- defined elsewhere, or replaced with "new ResourceLocation(...)"
            );


    public static final RegistryObject<FluidType> REFINED_OIL_FLUID_TYPE = register("refined_oil_fluid",
            FluidType.Properties.create().lightLevel(15).density(3000).viscosity(6000)
                    .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_LAVA)
                    .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA)
                    .temperature(1500).pathType(BlockPathTypes.LAVA).adjacentPathType(null)
                    .canSwim(true).canDrown(true).fallDistanceModifier(-1),
            new ResourceLocation(net.makozort.advancedages.AdvancedAges.MOD_ID, "block/refined_oil_still"), new ResourceLocation(net.makozort.advancedages.AdvancedAges.MOD_ID, "block/refined_oil_flow"), new ResourceLocation(net.makozort.advancedages.AdvancedAges.MOD_ID, "misc/in_refined_oil") // <- defined elsewhere, or replaced with "new ResourceLocation(...)"
    );



    private static RegistryObject<FluidType> register(String name, FluidType.Properties properties, ResourceLocation stillRL, ResourceLocation flowingRL, ResourceLocation overlayRL) {
        return FLUID_TYPES.register(name, () -> new BaseFluidType(stillRL, flowingRL, overlayRL,
                0xffffffff, new Vector3f(151f / 255f, 94f / 255f, 37f / 255f), properties));
    }

    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
    }
}