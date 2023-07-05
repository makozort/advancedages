package net.makozort.advancedages.content.fluid;

import com.mojang.math.Vector3f;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.makozort.advancedages.Advancedages;
import net.makozort.advancedages.content.fluid.BaseFluidType;

public class ModFluidTypes {
    public static final ResourceLocation WATER_STILL_RL = new ResourceLocation(Advancedages.MOD_ID, "block/crude_oil_still");
    public static final ResourceLocation WATER_FLOWING_RL = new ResourceLocation(Advancedages.MOD_ID, "block/crude_oil_flow");
    public static final ResourceLocation BRONZE_OVERLAY_RL = new ResourceLocation(Advancedages.MOD_ID, "misc/in_crude_oil");

    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, Advancedages.MOD_ID);

    public static final RegistryObject<FluidType> CRUDE_OIL_FLUID_TYPE = register("crude_oil_fluid",
            FluidType.Properties.create().lightLevel(15).density(3000).viscosity(6000)
                    .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_LAVA)
                    .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA)
                    .temperature(1500).pathType(BlockPathTypes.LAVA).adjacentPathType(null)
                    .canSwim(true).canDrown(true).fallDistanceModifier(-1)
    );

    private static RegistryObject<FluidType> register(String name, FluidType.Properties properties) {
        return FLUID_TYPES.register(name, () -> new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, BRONZE_OVERLAY_RL,
                0xffffffff, new Vector3f(151f / 255f, 94f / 255f, 37f / 255f), properties));
    }

    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
    }
}