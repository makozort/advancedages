package net.makozort.advancedages.content.blocks.Entity.CombustionEngine;

import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.kinetics.base.GeneratingKineticBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.makozort.advancedages.AdvancedAges;
import net.makozort.advancedages.foundation.fluid.SmarterFluidTankBehaviour;
import net.makozort.advancedages.reg.AllFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class combustionEngineBlockEntity extends GeneratingKineticBlockEntity implements IHaveGoggleInformation {

    private static final int TANK_CAPACITY = 1000;

    SmarterFluidTankBehaviour tank;

    protected int fuelRemaining = 0;

    protected final int timeInTicks = 18000;

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        behaviours.add(tank  = SmarterFluidTankBehaviour.single(this, TANK_CAPACITY)
                .forbidExtraction()
                .allowInsertion()
                .fluidType(AllFluids.CRUDE_OIL.getType()));
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return (side == Direction.UP) && isFluidHandlerCap(cap)
                ? tank.getCapability().cast()
                : super.getCapability(cap,side);
    }

    public combustionEngineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }



    public boolean checkFuel() {
        if (this.fuelRemaining >= 1) {
            return true;
        } else {
            return  false;
        }
    }


    @Override
    public float getGeneratedSpeed() {
        if (this.checkFuel()) {
            return 128;
        } else {
            return 0;
        }
    }

    @Override
    public void tick() {
        super.tick();
        if(this.fuelRemaining <= timeInTicks){
            if (this.tank.getPrimaryHandler().getFluidAmount() >= (TANK_CAPACITY/4)) {
                if (!((this.fuelRemaining + (timeInTicks/4)) > timeInTicks)) {
                    this.tank.getPrimaryHandler().drain((TANK_CAPACITY/4), IFluidHandler.FluidAction.EXECUTE);
                    this.fuelRemaining = this.fuelRemaining + (timeInTicks/4);
                }
            }
        }
        if (this.fuelRemaining > 0) {
            this.fuelRemaining--;
        }
        this.getGeneratedSpeed();
        this.updateGeneratedRotation();
    }


    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        boolean added = containedFluidTooltip(tooltip, isPlayerSneaking, tank.getCapability().cast());
        Component burnTimeComponent = Component.literal("Remaining Burn Time: " + this.fuelRemaining/20 + " seconds");
        tooltip.add(burnTimeComponent);
        return added;
}

    @Override
    protected void write(CompoundTag compound, boolean clientPacket) {
        super.write(compound, clientPacket);
        compound.putInt(AdvancedAges.MOD_ID + "_Data_FuelTime", this.fuelRemaining);
    }

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        super.read(compound, clientPacket);
        this.fuelRemaining = compound.getInt(AdvancedAges.MOD_ID + "_Data_FuelTime");
    }

}

