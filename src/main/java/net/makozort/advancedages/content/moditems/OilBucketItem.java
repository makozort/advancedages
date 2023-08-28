package net.makozort.advancedages.content.moditems;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;


public class OilBucketItem extends BucketItem {


    public OilBucketItem(RegistryObject<FlowingFluid> p_40689_, Properties p_40690_) {
        super(p_40689_, p_40690_);
    }


    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return 32767;
    }
}
