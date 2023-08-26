package net.makozort.advancedages.content.Data;

import net.makozort.advancedages.AdvancedAges;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import org.jetbrains.annotations.NotNull;
import javax.annotation.Nonnull;
import java.util.*;

public class OilGenData extends SavedData {

    public final Map<ChunkPos, OilGen> OilGenMap = new HashMap<>();
    List<ChunkPos> clearList = new ArrayList<ChunkPos>();

    @Nonnull
    public static OilGenData get(Level level) {
        if (level.isClientSide) {
            throw new RuntimeException("Don't access this client-side!");
        }
        DimensionDataStorage storage = ((ServerLevel)level).getDataStorage();
        return storage.computeIfAbsent(OilGenData::new, OilGenData::new, "oilgenmanager");
    }

    @NotNull
    private OilGen getOilGenInternal(ChunkPos pos) {
        ChunkPos chunkPos = new ChunkPos(pos.getWorldPosition());
        return OilGenMap.computeIfAbsent(chunkPos, cp -> new OilGen(0));
    }

    public Map<ChunkPos, OilGen> getMap() {
        return OilGenMap;
    }


    public double getOilgen(ChunkPos pos) {
        OilGen oilGen = getOilGenInternal(pos);
        return oilGen.getOilgen();
    }

    public int setGenned(ChunkPos pos){
        OilGen oilGen = getOilGenInternal(pos);
        oilGen.setGenned();
        setDirty();
        return 1;
    }

    public boolean isGenned(ChunkPos pos){
        OilGen oilGen = getOilGenInternal(pos);
        return oilGen.getGenned();
    }

    public int changeOilGen(ChunkPos pos, double i) {
        OilGen oilGen = getOilGenInternal(pos);
        double present = oilGen.getOilgen();
        double result = (present + i);
        if (result > 0) {
            oilGen.setOilgen(result);
        } else {
            oilGen.setOilgen(0);
        }
        oilGen.setOilgen(result);
        setDirty();
        return 1;
    }

    public OilGenData() {
    }


    // loaded when game boots essentially
    public OilGenData(CompoundTag tag) {
        ListTag list = tag.getList("oilgen", Tag.TAG_COMPOUND);
        for (Tag t : list) {
            CompoundTag oilGenTag = (CompoundTag) t;
            OilGen oilGen = new OilGen(oilGenTag.getDouble("oilgen"));
            if (oilGenTag.getBoolean("genned")) {
                oilGen.setGenned();
            }
            ChunkPos pos = new ChunkPos(oilGenTag.getInt("x"), oilGenTag.getInt("z"));
            OilGenMap.put(pos, oilGen);
        }
    }
    @Override
    public CompoundTag save(CompoundTag tag) {
        ListTag list = new ListTag();
        OilGenMap.forEach((ChunkPos, oilGen) -> {
            CompoundTag oilGenTag = new CompoundTag();
            oilGenTag.putInt("x", ChunkPos.x);
            oilGenTag.putInt("z", ChunkPos.z);
            oilGenTag.putDouble("oilgen", oilGen.getOilgen());
            oilGenTag.putBoolean("genned", oilGen.getGenned());
            list.add(oilGenTag);
        });
        tag.put("oilgen", list);
        return tag;
    }


    public class OilGen {
        private double oilgen;

        private boolean genned;

        public OilGen(double oilgen) {
            this.oilgen = oilgen;
        }

        public double getOilgen() {
            return oilgen;
        }

        public void setOilgen(double oilgen) {
            this.oilgen = oilgen;
        }

        public boolean getGenned() {
            return genned;

        }

        public void setGenned () {
            this.genned = true;
        }
    }
}