package net.makozort.advancedages.content.blocks.Entity;

import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.fluids.PipeConnection;
import com.simibubi.create.content.fluids.tank.FluidTankBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.simpleRelays.ICogWheel;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import com.simibubi.create.foundation.item.SmartInventory;
import net.makozort.advancedages.AdvancedAges;
import net.makozort.advancedages.reg.AllFluids;import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

public class OilFilterBlockEntity extends KineticBlockEntity implements IHaveGoggleInformation {

    private static final int TANK_CAPACITY= 1000; // in milibuckets

    public SmartFluidTankBehaviour inputTank;
    protected SmartFluidTankBehaviour outputTank;



    public Map<Direction, PipeConnection> interfaces;

    public OilFilterBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
        return (side != Direction.DOWN && isFluidHandlerCap(cap)
                ? inputTank.getCapability().cast()
                : super.getCapability(cap, side));
    }


    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        behaviours.add(inputTank = SmartFluidTankBehaviour.single(this,TANK_CAPACITY)
                .forbidInsertion()
        );
        behaviours.add(outputTank = SmartFluidTankBehaviour.single(this,TANK_CAPACITY)
                .forbidInsertion()
                .allowExtraction()
        );
    }
    @Override
    public void tick() {
        if (this.level.getBlockEntity(this.getBlockPos().below()) instanceof SteelFluidTankBlockEntity tank) {
            if (!tank.tankInventory.isEmpty()) {
                if (tank.tankInventory.getFluid().getRawFluid().getFluidType() == AllFluids.CRUDE_OIL.getType()) {
                    FluidStack stack =  tank.tankInventory.drain(40, IFluidHandler.FluidAction.EXECUTE);

                }
            }
        }
    }

    //@Override
    //public void write(CompoundTag compound, boolean clientPacket) {
    //    compound.putInt("Timer", timer);
    //    compound.put("InputInventory", inputInv.serializeNBT());
    //    compound.put("OutputInventory", outputInv.serializeNBT());
    //    super.write(compound, clientPacket);
    //}
//
    //@Override
    //protected void read(CompoundTag compound, boolean clientPacket) {
    //    timer = compound.getInt("Timer");
    //    inputInv.deserializeNBT(compound.getCompound("InputInventory"));
    //    outputInv.deserializeNBT(compound.getCompound("OutputInventory"));
    //    super.read(compound, clientPacket);
    //}

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        return containedFluidTooltip(tooltip, isPlayerSneaking,
                inputTank.getCapability().cast());
    }
}

