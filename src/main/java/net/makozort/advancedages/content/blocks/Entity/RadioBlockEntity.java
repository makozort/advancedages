package net.makozort.advancedages.content.blocks.Entity;

import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.makozort.advancedages.AdvancedAges;
import net.makozort.advancedages.content.blocks.block.AntennaBlock;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;


public class RadioBlockEntity extends SmartBlockEntity implements IHaveGoggleInformation {

    private static int MAX_TOWER_HEIGHT = 10;
    private static int MAX_RANGE = 250; // in blocks
    private int stackHeight;

    private int range;


    public RadioBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {

    }


    public static int getMaxTowerHeight(){
        return MAX_TOWER_HEIGHT;
    }


    public void connectUnitsAbove() {
        int newStackHeight = 0;
        boolean exitedStack = false;
        BlockPos testPos = this.getBlockPos().above();

        AntennaBlockEntity currentAntenna = null;
        //gets antenna directly above controller, if it exists
        if (level.getBlockEntity(testPos) instanceof AntennaBlockEntity antenna)
            currentAntenna = antenna;

        //the stack is atleast one tall for the first check
        if (stackHeight == 0)
            stackHeight = 1;

        //step upwards along the stack
        for (int i = 0; i < stackHeight; i++) {


            if (currentAntenna != null) {//if an antenna exists here

                if (exitedStack) {
                    //if the stack is disconnected, unlink this antenna
                    currentAntenna.controllerPos = null;
                } else {
                    //otherwise link it to this controller
                    currentAntenna.controllerPos = this.getBlockPos();

                }
                //update the antenna
                currentAntenna.sendData();
                BlockState currentState = currentAntenna.getBlockState();

                // sync the blockstate if it was wrong, as it should be connected if the stack has not been disconnected
                if (currentState.getValue(AntennaBlock.CONNECTED) == exitedStack) {
                    level.setBlockAndUpdate(currentAntenna.getBlockPos(), currentState.
                            setValue(AntennaBlock.CONNECTED, !exitedStack));
                }
            } else if(!exitedStack){
                //if an antenna does not exist here, and this was the first empty spot,
                // set the new stack height to the current position and mark the stack as disconnected from here upwards
                exitedStack = true;
                newStackHeight = i;
            }

            AntennaBlockEntity nextAntenna = null;
            //get next antenna
            if (level.getBlockEntity(testPos.above()) instanceof AntennaBlockEntity antenna)
                nextAntenna = antenna;

            //if an antenna exists at the next position, and the loop is at the end of the stack, and the stack is not at its maximum height
            // then extend the stack taller to reach that next position
            if (nextAntenna != null && i == stackHeight - 1 && stackHeight < getMaxTowerHeight()) {
                stackHeight++;
            }

            //nudge up the text position and prepare for the next iteration
            currentAntenna = nextAntenna;
            testPos = testPos.above();
        }
        //set the stack height to the new height if the stack was cut short
        if (exitedStack)
            stackHeight = newStackHeight;
    }

    @Override
    public void lazyTick() {
        super.lazyTick();
        this.range = ((int) (MAX_RANGE * ((double) this.stackHeight / MAX_TOWER_HEIGHT)));
        this.level.sendBlockUpdated(this.getBlockPos(),this.getBlockState(),this.getBlockState(),2);
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        boolean added = IHaveGoggleInformation.super.addToGoggleTooltip(tooltip, isPlayerSneaking);
            Component rangeComponent = Component.literal("     Current range: " ).append(Component.literal(String.valueOf(this.range)))
                    .withStyle(ChatFormatting.AQUA);
            tooltip.add(rangeComponent);
        return added;
    }

    @Override
    protected void write(CompoundTag compound, boolean clientPacket) {
        super.write(compound, clientPacket);
        compound.putInt(AdvancedAges.MOD_ID + "_Data_RADIO_STACK_HEIGHT", this.stackHeight);
    }

    // reads data in
    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        super.read(compound, clientPacket);
        this.stackHeight = compound.getInt(AdvancedAges.MOD_ID + "_Data_RADIO_STACK_HEIGHT");
    }
}
