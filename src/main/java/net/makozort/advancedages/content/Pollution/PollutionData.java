package net.makozort.advancedages.content.Pollution;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import org.jetbrains.annotations.NotNull;
import javax.annotation.Nonnull;
import java.util.*;

public class PollutionData extends SavedData {

    public final Map<BlockPos, Pollution> PollutionMap = new HashMap<>();

    @Nonnull
    public static PollutionData get(Level level) {
        if (level.isClientSide) {
            throw new RuntimeException("Don't access this client-side!");
        }
        DimensionDataStorage storage = ((ServerLevel)level).getDataStorage();
        return storage.computeIfAbsent(PollutionData::new, PollutionData::new, "pollutionmanager");
    }

    @NotNull
    private Pollution getPollutionInternal(BlockPos pos) {
        BlockPos BlockPos = new BlockPos(pos);
        return PollutionMap.computeIfAbsent(BlockPos, cp -> new Pollution(0));
    }

    public Map<BlockPos, Pollution> getMap() {
        return PollutionMap;
    }


    public double getPollution(BlockPos pos) {
        Pollution pollution = getPollutionInternal(pos);
        return pollution.getPollution();
    }

    public int setPollution(BlockPos pos, double i) {
        Pollution pollution = getPollutionInternal(pos);
        double present = pollution.getPollution();
        pollution.setPollution(present + i);
            setDirty();
            return 1;
    }


    public PollutionData() {
    }


    // loaded when game boots essentially
    public PollutionData(CompoundTag tag) {
        ListTag list = tag.getList("pollution", Tag.TAG_COMPOUND);
        for (Tag t : list) {
            CompoundTag pollTag = (CompoundTag) t;
            Pollution pollution = new Pollution(pollTag.getInt("pollution"));
            BlockPos pos = new BlockPos(pollTag.getInt("x"),pollTag.getInt("y"), pollTag.getInt("z"));
            PollutionMap.put(pos, pollution);
        }
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        ListTag list = new ListTag();
        PollutionMap.forEach((BlockPos, pollution) -> {
            CompoundTag pollTag = new CompoundTag();
            pollTag.putInt("x", BlockPos.getX());
            pollTag.putInt("y", BlockPos.getY());
            pollTag.putInt("z", BlockPos.getZ());
            pollTag.putDouble("pollution", pollution.getPollution());
            list.add(pollTag);
        });
        tag.put("pollution", list);
        return tag;
    }


    public class Pollution {
        private double pollution;

        public Pollution(double pollution) {
            this.pollution = pollution;
        }

        public double getPollution() {
            return pollution;
        }

        public void setPollution(double pollution) {
            this.pollution = pollution;
        }
    }

}
