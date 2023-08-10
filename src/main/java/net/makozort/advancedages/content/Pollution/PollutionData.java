//package net.makozort.advancedages.content.Pollution;
//
//import net.minecraft.core.BlockPos;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.nbt.NbtUtils;
//import net.minecraft.server.level.ServerLevel;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.saveddata.SavedData;
//import net.minecraft.world.level.storage.DimensionDataStorage;
//import net.minecraft.world.level.storage.WorldData;
//
//import javax.annotation.Nonnull;
//import java.util.HashMap;
//import java.util.Map;
//
//public class PollutionData extends SavedData {
//
//    //public static HashMap<BlockPos,Double> pollPoints = new HashMap<BlockPos,Double>();
//    private final Map<BlockPos, Double> manaMap = new HashMap<>();
//    private int counter = 0;
//
//
//
//    @Nonnull
//    public static PollManager get(Level level) {
//        if (level.isClientSide) {
//            throw new RuntimeException("Don't access this client-side!");
//        }
//        // Get the vanilla storage manager from the level
//        DimensionDataStorage storage = ((ServerLevel)level).getDataStorage();
//        // Get the mana manager if it already exists. Otherwise create a new one. Note that both
//        // invocations of ManaManager::new actually refer to a different constructor. One without parameters
//        // and the other with a CompoundTag parameter
//        return storage.computeIfAbsent(ManaManager::new, ManaManager::new, "manamanager");
//    }
//
//
//
//    public static void updateLevel(BlockPos blockPos) {
//
//        if (!pollPoints.containsKey(blockPos)){
//            pollPoints.put(blockPos,.5d);
//        } else {
//            pollPoints.replace(blockPos,(pollPoints.get(blockPos)) + .5d);
//            }
//        }
//
//
//
//
//
//
//
//
//
//    public PollManager() {
//    }
//
//    // This constructor is called when loading the mana manager from disk
//    public PollManager(CompoundTag tag) {
//        ListTag list = tag.getList("mana", Tag.TAG_COMPOUND);
//        for (Tag t : list) {
//            CompoundTag manaTag = (CompoundTag) t;
//            Mana mana = new Mana(manaTag.getInt("mana"));
//            ChunkPos pos = new ChunkPos(manaTag.getInt("x"), manaTag.getInt("z"));
//            manaMap.put(pos, mana);
//        }
//    }
//
//
//    @Override
//    public CompoundTag save(CompoundTag nbt) {
//        CompoundTag pollTag;
//        BlockPos key;
//        for (int i = 0; i < pollPoints.size(); i++) {
//            pollTag=new CompoundTag();
//            key= (BlockPos) pollPoints.keySet().toArray()[i];
//            pollTag.put("blockpos", NbtUtils.writeBlockPos(key));
//            pollTag.putDouble("value", pollPoints.get(key));
//            nbt.put("pollpoint_"+i,pollTag);
//        }
//        return nbt;
//    }
//
//
//
//}
//
//
//
//
//