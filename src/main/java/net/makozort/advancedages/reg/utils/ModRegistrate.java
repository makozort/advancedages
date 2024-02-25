package net.makozort.advancedages.reg.utils;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.VirtualFluidBuilder;
import com.tterrag.registrate.builders.FluidBuilder;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.makozort.advancedages.content.fluid.VirtualGas;
import net.makozort.advancedages.reg.AllFluids;
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
        return fluid(name, AllFluids.NoColorFluidAttributes::new);
    }

    public <T extends VirtualGas> FluidBuilder<T, ModRegistrate> virtualGas(String name, ResourceLocation still, ResourceLocation flow) {
        return this.entry(name, (c) -> new VirtualFluidBuilder(this.self(), this.self(), name, c, still, flow, ModRegistrate::defaultFluidType, VirtualGas::new));
    }

    public <T extends VirtualGas> FluidBuilder<T, ModRegistrate> virtualGas(String name) {
        return virtualGas(name, asFluid(name, false), asFluid(name, false));
    }

}
