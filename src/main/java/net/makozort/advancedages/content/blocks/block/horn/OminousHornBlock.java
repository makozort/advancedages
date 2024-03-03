package net.makozort.advancedages.content.blocks.block.horn;

import net.makozort.advancedages.reg.AllSoundEvents;
import net.minecraft.sounds.SoundEvent;

public class OminousHornBlock extends HornBlock {
    public OminousHornBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public SoundEvent getSoundEvent() {
        return AllSoundEvents.OMINOUS_HORN.get();
    }


    @Override
    public int getScreenShakeDuration() {
        return 300;
    }
}
