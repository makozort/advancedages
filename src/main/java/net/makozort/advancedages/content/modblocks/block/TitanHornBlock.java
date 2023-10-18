package net.makozort.advancedages.content.modblocks.block;

import net.makozort.advancedages.reg.AllSoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

public class TitanHornBlock extends HornBlock {
    public TitanHornBlock(Properties p_49795_) {
        super(p_49795_);
    }


    @Override
    public SoundEvent GetSoundEvent(){
        return AllSoundEvents.TITAN_HORN.get();
    }

}
