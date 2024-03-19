package net.makozort.advancedages.content.capabilities.blaze;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlazeAddictionProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<BlazeAddiction> BLAZE_ADDICTION = CapabilityManager.get(new CapabilityToken<BlazeAddiction>() {
    });

    private BlazeAddiction addiction = null;
    public final LazyOptional<BlazeAddiction> optional = LazyOptional.of(this::createPlayerAddiction);

    private BlazeAddiction createPlayerAddiction() {
        if (this.addiction == null) {
            this.addiction = new BlazeAddiction();
        }
        return this.addiction;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == BLAZE_ADDICTION) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        createPlayerAddiction().saveNBTData(tag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        createPlayerAddiction().loadNBTData(tag);
    }
}
