//package net.makozort.advancedages.content.data;
//
//import net.minecraft.core.BlockPos;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.nbt.ListTag;
//import net.minecraft.nbt.Tag;
//import net.minecraft.server.level.ServerLevel;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.saveddata.SavedData;
//import net.minecraft.world.level.storage.DimensionDataStorage;
//import org.jetbrains.annotations.NotNull;
//
//import javax.annotation.Nonnull;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class RadioTowerData extends SavedData {
//
//    public final Map<BlockPos, Tower> PollutionMap = new HashMap<>();
//    List<BlockPos> clearList = new ArrayList<BlockPos>();
//
//    @Nonnull
//    public static RadioTowerData get(Level level) {
//        if (level.isClientSide) {
//            throw new RuntimeException("Don't access this client-side!");
//        }
//        DimensionDataStorage storage = ((ServerLevel) level).getDataStorage();
//        return storage.computeIfAbsent(RadioTowerData::new, RadioTowerData::new, "pollutionmanager");
//    }
//
//    @NotNull
//    private RadioTowerData.Tower getPollutionInternal(BlockPos pos) {
//        BlockPos BlockPos = new BlockPos(pos);
//        return PollutionMap.computeIfAbsent(BlockPos, cp -> new Tower(0));
//    }
//
//    public Map<BlockPos, Tower> getMap() {
//        return PollutionMap;
//    }
//
//
//    public double getPollution(BlockPos pos) {
//        Tower tower = getPollutionInternal(pos);
//        return tower.getPollution();
//    }
//
//    public int changePollution(BlockPos pos, double i, Level level) {
//        if (i >= 0) {
//            if (!level.isClientSide) {
//                ServerLevel serverLevel = (ServerLevel) level;
//                //serverLevel.sendParticles(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5, 5000, 0.3, 100, 0.3, 0);
//            }
//        }
//        Tower tower = getPollutionInternal(pos);
//        double present = tower.getPollution();
//        double result = (present + i);
//        if (result > 20) {
//            tower.setPollution(20);
//        } else if (result <= 0) {
//            tower.setPollution(0);
//            clearList.add(pos);
//        } else {
//            tower.setPollution(result);
//        }
//        setDirty();
//        return 1;
//    }
//
//    //public int clearOldPollution() {
//    //    clearList.forEach((BlockPos) -> {
//    //        PollutionMap.remove(BlockPos);
//    //    });
//    //    clearList.removeAll(clearList);
//    //    setDirty();
//    //    return 1;
//    //}
//
//    // idk figure it out
//    public RadioTowerData() {
//    }
//
//
//    // loaded when world boots essentially
//    public RadioTowerData(CompoundTag tag) {
//        ListTag list = tag.getList("towers", Tag.TAG_COMPOUND);
//        for (Tag t : list) {
//            CompoundTag pollTag = (CompoundTag) t;
//            Tower tower = new Tower(pollTag.getInt("pollution"));
//            BlockPos pos = new BlockPos(pollTag.getInt("x"), pollTag.getInt("y"), pollTag.getInt("z"));
//            PollutionMap.put(pos, tower);
//        }
//    }
//
//    @Override
//    public CompoundTag save(CompoundTag tag) {
//        ListTag list = new ListTag();
//        PollutionMap.forEach((BlockPos, tower) -> {
//            CompoundTag pollTag = new CompoundTag();
//            pollTag.putInt("x", BlockPos.getX());
//            pollTag.putInt("y", BlockPos.getY());
//            pollTag.putInt("z", BlockPos.getZ());
//            pollTag.putInt("height", tower.getHeight());
//            pollTag.putBoolean("hasAntenna", tower.getAntenna());
//            list.add(pollTag);
//        });
//        tag.put("towers", list);
//        return tag;
//    }
//
//    public class Tower {
//        private int height;
//
//        private boolean hasAntenna;
//
//        public Tower(int height) {
//            this.height = height;
//        }
//
//        public Tower(boolean hasAntenna) {
//            this.hasAntenna = hasAntenna;
//        }
//
//        public boolean getAntenna() {return hasAntenna;}
//
//        public void setHasAntenna(boolean hasAntenna) {this.hasAntenna = hasAntenna;}
//
//        public int getHeight() {
//            return height;
//        }
//
//        public void setHeight(int height) {
//            this.height = height;
//        }
//    }
//
//}