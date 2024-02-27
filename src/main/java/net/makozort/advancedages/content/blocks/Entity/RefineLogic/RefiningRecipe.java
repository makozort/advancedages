package net.makozort.advancedages.content.blocks.Entity.RefineLogic;

import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import net.makozort.advancedages.reg.AllRecipeTypes;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class RefiningRecipe extends ProcessingRecipe<RecipeWrapper> {

    public RefiningRecipe(ProcessingRecipeBuilder.ProcessingRecipeParams params) {
        super(AllRecipeTypes.REFINING, params);
    }

    @Override
    protected int getMaxInputCount() {
        return 1;
    }

    @Override
    protected int getMaxOutputCount() {
        return 1;
    }

    @Override
    public boolean matches(RecipeWrapper recipeWrapper, Level level) {
        return this.ingredients.get(0).test(recipeWrapper.getItem(0));
    }

    //DONT REMOVE... please?
}
