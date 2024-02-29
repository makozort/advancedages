package net.makozort.advancedages.foundation.registrate;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.VirtualFluidBuilder;
import com.tterrag.registrate.builders.FluidBuilder;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import net.makozort.advancedages.foundation.fluid.NoColorFluidAttributes;
import net.makozort.advancedages.foundation.gas.VirtualGas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.RegistryObject;

import java.util.IdentityHashMap;
import java.util.Map;

import static net.makozort.advancedages.AdvancedAges.asFluid;

public class ModRegistrate extends CreateRegistrate {
    private static final Map<RegistryEntry<?>, RegistryObject<CreativeModeTab>> TAB_LOOKUP = new IdentityHashMap<>();

    protected ModRegistrate(String modid) {
        super(modid);
    }

    public static ModRegistrate create(String modid) {
        return new ModRegistrate(modid);
    }


    public static boolean isInCreativeTab(RegistryEntry<?> entry, RegistryObject<CreativeModeTab> tab) {
        return TAB_LOOKUP.get(entry) == tab;
    }

    public FluidBuilder<ForgeFlowingFluid.Flowing, CreateRegistrate> fluid(String name, FluidBuilder.FluidTypeFactory typeFactory) {
        return this.entry(name, (callback) -> FluidBuilder.create(this, this, name, callback, asFluid(name, false), asFluid(name, false), typeFactory));
    }

    public FluidBuilder<ForgeFlowingFluid.Flowing, CreateRegistrate> fluid(String name) {
        return fluid(name, NoColorFluidAttributes::new);
    }

    public <T extends VirtualGas> FluidBuilder<T, ModRegistrate> virtualGas(String name, ResourceLocation still, ResourceLocation flow, NonNullFunction<ForgeFlowingFluid.Properties, T> factory) {
        return this.entry(name, (c) -> new VirtualFluidBuilder<>(this, this, name, c, still, flow, ModRegistrate::defaultFluidType, factory));
    }

    public <T extends VirtualGas> FluidBuilder<T, ModRegistrate> virtualGas(String name, NonNullFunction<ForgeFlowingFluid.Properties, T> factory) {
        return virtualGas(name, asFluid(name, false), asFluid(name, false), factory);
    }
}
