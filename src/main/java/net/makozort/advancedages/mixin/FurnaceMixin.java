package net.makozort.advancedages.mixin;


import net.makozort.advancedages.content.data.PollutionData;
import net.makozort.advancedages.reg.Allitems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class FurnaceMixin extends BlockEntity {


    @Shadow
    public abstract ItemStack getItem(int p_58328_);

    public FurnaceMixin(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
        super(p_155228_, p_155229_, p_155230_);
    }


    @Inject(method = "getBurnDuration", at = @At("TAIL"), remap = false)
    private void getBurnDuration(CallbackInfoReturnable<Map<Item, Integer>> cir) {
        if (this.getItem(1).is(Allitems.HEAVY_OIL_BUCKET.get())) {
            if (this.level instanceof ServerLevel) {
                PollutionData.get(this.level).changePollution(this.getBlockPos(), .25, this.level);
            }
        }

    }
}