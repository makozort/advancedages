package net.makozort.advancedages.content.blocks.entity;

import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.makozort.advancedages.AdvancedAges;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

import static net.makozort.advancedages.content.blocks.block.AntennaBlock.CONNECTED;

public class AntennaBlockEntity extends SmartBlockEntity implements IHaveGoggleInformation {

    public BlockPos controllerPos;

    public AntennaBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {

    }


    public void updateConnectivity() {
        if (level.isClientSide())
            return;
        BlockPos testPos = getBlockPos();
        for (int i = 0; i < RadioBlockEntity.getMaxTowerHeight(); i++) {
            testPos = testPos.below();
            BlockEntity beAtPos = level.getBlockEntity(testPos);
            if (beAtPos instanceof RadioBlockEntity controller) {
                controller.connectUnitsAbove();
                break;
            }
            if (!(beAtPos instanceof AntennaBlockEntity))
                break;
        }
    }

    @Override
    public void lazyTick() {
        super.lazyTick();
        if (this.level instanceof ServerLevel) {
            if (this.controllerPos != null) {
                if (!(this.level.getBlockEntity(controllerPos) instanceof RadioBlockEntity)) {
                    this.controllerPos = null;
                }
            }
            if (this.controllerPos == null) {
                this.level.setBlock(this.getBlockPos(), this.getBlockState().setValue(CONNECTED, false), 3);
            }
        }
    }


    @Override
    protected void write(CompoundTag compound, boolean clientPacket) {
        super.write(compound, clientPacket);
        if (this.controllerPos != null) {
            ListTag list = new ListTag();
            CompoundTag ControllerPosTag = new CompoundTag();
            ControllerPosTag.putInt("x", this.controllerPos.getX());
            ControllerPosTag.putInt("y", this.controllerPos.getY());
            ControllerPosTag.putInt("z", this.controllerPos.getZ());
            list.add(ControllerPosTag);
            compound.put(AdvancedAges.MOD_ID + "_Data_ANTENNA_CONTROLLER", list);
        }
    }

    // reads data in
    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        super.read(compound, clientPacket);
        ListTag list = compound.getList(AdvancedAges.MOD_ID + "_Data_ANTENNA_CONTROLLER", Tag.TAG_COMPOUND);
        if (!list.isEmpty()) {
            for (Tag t : list) {
                CompoundTag pollTag = (CompoundTag) t;
                BlockPos pos = new BlockPos(pollTag.getInt("x"), pollTag.getInt("y"), pollTag.getInt("z"));
                this.controllerPos = pos;
            }
        }
    }
}
