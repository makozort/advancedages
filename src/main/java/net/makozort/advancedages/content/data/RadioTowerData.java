package net.makozort.advancedages.content.data;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;

import javax.annotation.Nonnull;

public class RadioTowerData extends SavedData {

    @Nonnull
    public static RadioTowerData get(Level level) {
        return null;
    }
    public RadioTowerData() {
    }

    @Override
    public CompoundTag save(CompoundTag compoundTag) {
        return null;
    }

    public RadioTowerData(CompoundTag tag) {
        ListTag list = tag.getList("towers", Tag.TAG_COMPOUND);
        for (Tag t : list) {
            CompoundTag pollTag = (CompoundTag) t;
            Tower tower = new Tower(pollTag.getInt("pollution"));
            BlockPos pos = new BlockPos(pollTag.getInt("x"), pollTag.getInt("y"), pollTag.getInt("z"));
        }
    }

    public class Tower {
        private int height;

        private boolean hasAntenna;

        public Tower(int height) {
            this.height = height;
        }

        public Tower(boolean hasAntenna) {
            this.hasAntenna = hasAntenna;
        }

        public boolean getAntenna() {return hasAntenna;}

        public void setHasAntenna(boolean hasAntenna) {this.hasAntenna = hasAntenna;}

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }
    }

}