package net.makozort.advancedages.foundation.gas.pollution;

import net.makozort.advancedages.AdvancedAges;
import net.makozort.advancedages.foundation.gas.GasStack;
import net.makozort.advancedages.foundation.gas.MixedVirtualGas;
import net.makozort.advancedages.foundation.gas.VirtualGas;
import net.makozort.advancedages.reg.AllFluids;
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

public class GasData extends SavedData {

    public final Map<BlockPos, MixedVirtualGas> GasMap = new HashMap<>();
    List<BlockPos> clearList = new ArrayList<BlockPos>();

    GasData() {
        super();
    }

    public void writemap() {
        AdvancedAges.LOGGER.info("john");
        AdvancedAges.LOGGER.info(GasMap.toString());
    }
    @Nonnull
    public static GasData get(Level level) {
        if (level.isClientSide) {
            throw new RuntimeException("Don't access this client-side!");
        }
        DimensionDataStorage storage = ((ServerLevel) level).getDataStorage();
        return storage.computeIfAbsent(GasData::new, GasData::new, "pollutionmanager");
    }

    @NotNull
    private MixedVirtualGas getGasInternal(BlockPos pPos) {
        BlockPos pos = new BlockPos(pPos);
        return GasMap.computeIfAbsent(pos, cp -> new Pollution(0));
    }

    public Map<BlockPos, MixedVirtualGas> getGasMap() {
        return GasMap;
    }


    public MixedVirtualGas getGas(BlockPos pos) {
        return GasMap.get(pos);
    }

    public double getPollution(BlockPos pos) {
        return GasMap.get(pos).getGas(AllFluids.CARBON_DIOXIDE.get()) + GasMap.get(pos).getGas(AllFluids.NATURAL_GAS.get()) *2;
    }

    public int changeGas(BlockPos pos, VirtualGas gas, int i, Level level) {
        MixedVirtualGas gasses = getGasInternal(pos);
        int present = gasses.getGas(gas);
        int r = (present + i);
        if (r > 1000) {
            changeGas(pos.above(), gas, r / 6, level);
            changeGas(pos.below(), gas, r / 6, level);
            changeGas(pos.south(), gas, r / 6, level);
            changeGas(pos.north(), gas, r / 6, level);
            changeGas(pos.west(), gas, r / 6, level);
            changeGas(pos.east(), gas, r / 6, level);
        } else if (r <= 0) {
            clearList.add(pos);
        } else {
            gasses.addGas(gas, r);
        }
        setDirty();
        return 1;
    }

    public int changeGas(BlockPos pos, GasStack<?> stack, Level level) {
       return changeGas(pos, stack.getGas(), stack.getAmount(), level);
    }

    public int clearOldPollution() {
        clearList.forEach((BlockPos) -> {
            GasMap.remove(BlockPos);
        });
        clearList.removeAll(clearList);
        setDirty();
        return 1;
    }


    // loaded when game boots essentially
    public GasData(CompoundTag tag) {
        ListTag list = tag.getList("pollution", Tag.TAG_COMPOUND);
        for (Tag t : list) {
            CompoundTag pollTag = (CompoundTag) t;
            //MixedVirtualGas pollution = new Pollution(pollTag.getDouble("co2"));
            BlockPos pos = new BlockPos(pollTag.getInt("x"), pollTag.getInt("y"), pollTag.getInt("z"));
            //GasMap.put(pos, pollution);
        }
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        ListTag list = new ListTag();
        GasMap.forEach((BlockPos, gasses) -> {
            CompoundTag pollTag = new CompoundTag();
            pollTag.putInt("co2", gasses.getGas(AllFluids.CARBON_DIOXIDE.get()));
            pollTag.putInt("x", BlockPos.getX());
            pollTag.putInt("y", BlockPos.getY());
            pollTag.putInt("z", BlockPos.getZ());
            list.add(pollTag);
        });
        tag.put("pollution", list);
        return tag;
    }
}