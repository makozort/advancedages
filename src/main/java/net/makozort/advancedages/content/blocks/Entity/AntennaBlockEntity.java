package net.makozort.advancedages.content.blocks.Entity;

import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.makozort.advancedages.AdvancedAges;
import net.makozort.advancedages.content.blocks.block.RadioBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

import static net.makozort.advancedages.content.blocks.block.AntennaBlock.CONNECTED;

public class AntennaBlockEntity extends SmartBlockEntity implements IHaveGoggleInformation {

    public RadioBlockEntity controller;

    public int height; //

    public AntennaBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {

    }


    public void findController(){
        if (this.level.getBlockEntity(this.getBlockPos().below()) instanceof RadioBlockEntity radio) {
            this.controller = radio;
        } else if (this.level.getBlockEntity(this.getBlockPos().below()) instanceof AntennaBlockEntity antenna && !(antenna.controller == null) && antenna.height < antenna.controller.getMaxTowerHeight()) {
            this.controller = antenna.controller;
        } else {
            this.controller = null;
        }
    }

    @Override
    public void lazyTick() {
        super.lazyTick();
        this.findController();
        if (!(this.controller == null)){
            this.level.setBlock(this.getBlockPos(), this.getBlockState().setValue(CONNECTED, true), 3);
        } else {
            this.level.setBlock(this.getBlockPos(), this.getBlockState().setValue(CONNECTED, false), 3);
        }
    }
}
