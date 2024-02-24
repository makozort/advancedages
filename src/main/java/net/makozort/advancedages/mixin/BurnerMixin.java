package net.makozort.advancedages.mixin;

import com.simibubi.create.content.processing.burner.BlazeBurnerBlockEntity;
import net.makozort.advancedages.AdvancedAges;
import net.makozort.advancedages.content.data.PollutionData;
import net.makozort.advancedages.reg.AllFluids;
import net.makozort.advancedages.reg.Allitems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlazeBurnerBlockEntity.class)
public class BurnerMixin extends BlockEntity {

    @Shadow
    protected int remainingBurnTime;

    public BurnerMixin(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
        super(p_155228_, p_155229_, p_155230_);
    }
    @Inject(at = @At("TAIL"), method = "tryUpdateFuel", remap = false)
    private void durChange(ItemStack itemStack, boolean forceOverflow, boolean simulate, CallbackInfoReturnable<Boolean> cir) {
        if (itemStack.is(Allitems.HEAVY_OIL_BUCKET.get().asItem())) {
            this.remainingBurnTime = 72000;
            BlockPos pos = this.getBlockPos();
            if (this.level instanceof ServerLevel) {
                PollutionData.get(this.level).changePollution(pos, .25, this.level);
            }
        }
    }
}
