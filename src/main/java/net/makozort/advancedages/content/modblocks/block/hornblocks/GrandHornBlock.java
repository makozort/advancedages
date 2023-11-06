package net.makozort.advancedages.content.modblocks.block.hornblocks;

import net.makozort.advancedages.reg.AllSoundEvents;
import net.minecraft.sounds.SoundEvent;

public class GrandHornBlock extends HornBlock {
    public GrandHornBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public SoundEvent GetSoundEvent() {
        return AllSoundEvents.GRAND_HORN.get();
    }
}
