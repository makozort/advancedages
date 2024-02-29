package net.makozort.advancedages.content.data;

import net.makozort.advancedages.AdvancedAges;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepositData extends SavedData {

    List<BlockPos> OilList = new ArrayList<BlockPos>();
    List<BlockPos> clearList = new ArrayList<BlockPos>();

    @Nonnull
    public static DepositData get(Level level) {
        if (level.isClientSide) {
            throw new RuntimeException("Don't access this client-side!");
        }
        DimensionDataStorage storage = ((ServerLevel) level).getDataStorage();
        return storage.computeIfAbsent(DepositData::new, DepositData::new, AdvancedAges.MOD_ID+"_DepositManager");
    }

    public void addDeposit(BlockPos pos) {
        this.OilList.add(pos);
        setDirty();
    }

    public void removeDeposit(BlockPos pos) {
        this.OilList.remove(pos);
    }

    public List<BlockPos> getlist(){
        return this.OilList;
    }

    public DepositData() {
    }


    public List<BlockPos> oilSearch(BlockPos centerPos, int radius) {
        List<BlockPos> blocksInRadius = new ArrayList<>();
        for (BlockPos pos : this.OilList) {
            if (pos.distToCenterSqr(centerPos.getCenter()) <= radius * radius) {
                blocksInRadius.add(pos);
            }
        }
        return blocksInRadius;
    }


    // loaded when game boots essentially
    public DepositData(CompoundTag tag) {
        ListTag list = tag.getList(AdvancedAges.MOD_ID+"_Data_Oil_Deposits", Tag.TAG_COMPOUND);
        for (Tag t : list) {
            CompoundTag pollTag = (CompoundTag) t;
            BlockPos pos = new BlockPos(pollTag.getInt("x"), pollTag.getInt("y"), pollTag.getInt("z"));
            this.OilList.add(pos);
        }
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        ListTag list = new ListTag();
        this.OilList.forEach((BlockPos) -> {
            CompoundTag pollTag = new CompoundTag();
            pollTag.putInt("x", BlockPos.getX());
            pollTag.putInt("y", BlockPos.getY());
            pollTag.putInt("z", BlockPos.getZ());
            list.add(pollTag);
        });
        tag.put(AdvancedAges.MOD_ID+"_Data_Oil_Deposits", list);
        return tag;
    }
}