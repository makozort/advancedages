package net.makozort.advancedages.mixin;

import com.simibubi.create.content.redstone.link.IRedstoneLinkable;
import com.simibubi.create.content.redstone.link.LinkBehaviour;
import com.simibubi.create.content.redstone.link.RedstoneLinkBlockEntity;
import com.simibubi.create.content.redstone.link.RedstoneLinkNetworkHandler;
import net.makozort.advancedages.AdvancedAges;
import net.makozort.advancedages.content.blocks.entity.RadioBlockEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RedstoneLinkNetworkHandler.class)
public class LinkMixin {
    @Inject(method = "withinRange", at = @At("HEAD"), cancellable = true, remap = false)
    private static void injectCustomRange(IRedstoneLinkable from, IRedstoneLinkable to, CallbackInfoReturnable<Boolean> cir) {
        if (from instanceof LinkBehaviour behavior) {
            Level level = behavior.blockEntity.getLevel();
            AdvancedAges.LOGGER.info(String.valueOf(level));
            if (level.getBlockEntity(from.getLocation()) instanceof RedstoneLinkBlockEntity link) {
                 if (level.getBlockEntity(link.getBlockPos().above()) instanceof RadioBlockEntity radio) {
                     boolean withinCustomRange = from.getLocation().distManhattan(to.getLocation()) <= radio.getRange();
                     cir.setReturnValue(withinCustomRange);
                 }
            }
        }
    }
}