package net.makozort.advancedages.content.blocks.Entity;

import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.makozort.advancedages.AdvancedAges;
import net.makozort.advancedages.reg.AllBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SandBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;

import java.util.ArrayList;
import java.util.List;

import static net.makozort.advancedages.content.blocks.Entity.HornBlockEntity.LIT;

public class RadioBlockEntity extends SmartBlockEntity implements IHaveGoggleInformation {

    private static int MAX_TOWER_HEIGHT = 10;
    private static int MAX_RANGE = 250; // in blocks
    List<AntennaBlockEntity> antennas = new ArrayList<AntennaBlockEntity>();
    private int height;

    public RadioBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {

    }


    public int getMaxTowerHeight(){
        return MAX_TOWER_HEIGHT;
    }

    @Override
    public void lazyTick() {
        super.lazyTick();
        List<AntennaBlockEntity> newAntennas = new ArrayList<AntennaBlockEntity>();
        for (int i = 1; i <= MAX_TOWER_HEIGHT; i++) {
            if (this.level.getBlockEntity(this.getBlockPos().above(i)) instanceof AntennaBlockEntity antenna) {
                newAntennas.add(antenna);
                antenna.height = i;
            } else if (this.level.getBlockState(this.getBlockPos().above(i)).getBlock() instanceof AirBlock) {
                break;
            }
        }
        this.antennas = newAntennas;
        this.height = this.antennas.size();
    }

}
