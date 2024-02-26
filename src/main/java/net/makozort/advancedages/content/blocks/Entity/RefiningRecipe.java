package net.makozort.advancedages.content.blocks.Entity;

import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import net.makozort.advancedages.reg.AllRecipeTypes;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class RefiningRecipe extends ProcessingRecipe<IFluidHandler.FluidAction> {
    public RefiningRecipe(IRecipeTypeInfo typeInfo, ProcessingRecipeBuilder.ProcessingRecipeParams params) {
        super(AllRecipeTypes.refining, params);
    }

    @Override
    protected int getMaxInputCount() {
        return 0;
    }

    @Override
    protected int getMaxOutputCount() {
        return 0;
    }

    @Override


    @Override
    public boolean matches(RecipeWrapper inv, Level level) {
        return !inv.isEmpty() && this.fluidIngredients.get(0).test(inv.get);
    }

    @Override
    public boolean matches(IFluidHandler.FluidAction fluidAction, Level level) {
        return this.fluidIngredients.get(0).test(inv);
    }
}
