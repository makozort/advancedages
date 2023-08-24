package net.makozort.advancedages.mixin;

import com.simibubi.create.content.processing.burner.BlazeBurnerBlockEntity;
//import net.makozort.advancedages.content.Pollution.PollutionData;
import net.makozort.advancedages.content.Pollution.PollutionData;
import net.makozort.advancedages.reg.Allitems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlazeBurnerBlockEntity.class)
public class BurnerMixin extends BlockEntity {

    @Shadow protected int remainingBurnTime;

    public BurnerMixin(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
        super(p_155228_, p_155229_, p_155230_);
    }


    @Inject(at = @At("TAIL"), method = "tryUpdateFuel", remap = false)
    private void durChange(ItemStack itemStack, boolean forceOverflow, boolean simulate, CallbackInfoReturnable<Boolean> cir) {
    if (itemStack.is(Allitems.REFINED_OIL_BUCKET.get())) {
            this.remainingBurnTime = 36000;
            BlockPos pos = this.getBlockPos();
            PollutionData.get(this.level).changePollution(pos,.1,this.level);
        }
    }

}
