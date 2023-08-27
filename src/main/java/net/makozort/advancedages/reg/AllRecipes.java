package net.makozort.advancedages.reg;

import net.makozort.advancedages.AdvancedAges;
import net.makozort.advancedages.content.Recipes.OilRefineryRecipe;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

//public class AllRecipes {
//    public static final DeferredRegister<RecipeSerializer<?>> TYPES =
//            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, AdvancedAges.MOD_ID);
//
//    static RegistryObject<RecipeSerializer<?>> LIQUID_BURNING = TYPES.register("liquid_burning", () ->
//            new OilRefineryRecipe());
//
//
//
//    public static void register(IEventBus event) {
//
//        TYPES.register(event);
//
//        CraftingHelper.register(HasFluidTagCondition.Serializer.INSTANCE);
//    }
//}