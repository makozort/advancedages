package net.makozort.advancedages.content.capabilities.blaze;

import net.minecraft.nbt.CompoundTag;

public class BlazeAddiction {

    private int addiction;
    private final int MAX_ADDICTION = 432000;
    private final int MIN_ADDICTION = 0;


    public int getAddiction(){
        return addiction;
    }

    public void addAddiction(int add) {
        this.addiction = Math.min(addiction + add, MAX_ADDICTION);
    }


    public void subAddiction(int add) {
        this.addiction = Math.max(addiction - add, MIN_ADDICTION);
    }


    public void copyFrom(BlazeAddiction source) {
        this.addiction = source.addiction;
    }
    public void saveNBTData(CompoundTag tag) {
        tag.putInt("addiction",addiction);
    }

    public void loadNBTData(CompoundTag tag) {
        addiction = tag.getInt("addiction");
    }
}
