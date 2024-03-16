package net.makozort.advancedages.content.blocks.entity.RefineLogic;

import net.makozort.advancedages.reg.AllFluids;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidType;

import java.util.HashMap;

public class RefiningRecipes {
    private static final HashMap<FluidType, Fluid> refiningRecipes = new HashMap<>();

    static {
        refiningRecipes.put(AllFluids.CRUDE_OIL.getType(), AllFluids.HEAVY_OIL.get());
    }

    public static HashMap<FluidType, Fluid> getRefiningRecipes() {
        return refiningRecipes;
    }
}