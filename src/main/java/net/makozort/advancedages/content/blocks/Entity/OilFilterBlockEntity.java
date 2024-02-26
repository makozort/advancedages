package net.makozort.advancedages.content.blocks.Entity;

import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.fluids.PipeConnection;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;


import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

public class OilFilterBlockEntity extends KineticBlockEntity implements IHaveGoggleInformation {

    private static final int TANK_CAPACITY= 1000; // in milibuckets

    public SmartFluidTankBehaviour inputTank;
    protected SmartFluidTankBehaviour outputTank;
    public RefiningRecipe recipe;



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
        if (this.level.getBlockEntity(this.getBlockPos().below()) instanceof SteelFluidTankBlockEntity tankBelow && this.level.getBlockEntity(this.getBlockPos().above()) instanceof SteelFluidTankBlockEntity tankAbove) {
            assert tankAbove == (tankAbove.isController() ? tankAbove : (SteelFluidTankBlockEntity) level.getBlockEntity(tankAbove.controller));
            assert tankBelow == (tankBelow.isController() ? tankBelow : (SteelFluidTankBlockEntity) level.getBlockEntity(tankBelow.controller));
            for(FluidIngredient ingredients : recipe.getFluidIngredients())  {
                if((!tankBelow.tankInventory1.isEmpty() || !tankBelow.tankInventory2.isEmpty()) && (tankBelow.tankInventory1.getFluidAmount() == ingredients.getRequiredAmount() || tankBelow.tankInventory2.getFluidAmount() == ingredients.getRequiredAmount())) {
                    for (FluidStack ingredient : ingredients.matchingFluidStacks) {
                        if (tankBelow.tankInventory1.isFluidValid(ingredient)) {
                            tankBelow.tankInventory1.drain(ingredients.getRequiredAmount(), )
                        } else if (tankBelow.tankInventory2.isFluidValid(ingredient)) {

                        }
                    }
                }
            }
        }
    }

//    @Override
//    public void write(CompoundTag compound, boolean clientPacket) {
//        compound.putInt("Timer", timer);
//        compound.put("InputInventory", inputInv.serializeNBT());
//        compound.put("OutputInventory", outputInv.serializeNBT());
//        super.write(compound, clientPacket);
//    }
//
//    @Override
//    protected void read(CompoundTag compound, boolean clientPacket) {
//        inputTank.deserializeNBT(compound.getCompound("InputInventory"));
//        outputTank.deserializeNBT(compound.getCompound("OutputInventory"));
//        super.read(compound, clientPacket);
//    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        return containedFluidTooltip(tooltip, isPlayerSneaking,
                inputTank.getCapability().cast());
    }
}

