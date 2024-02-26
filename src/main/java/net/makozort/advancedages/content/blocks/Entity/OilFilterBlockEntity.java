package net.makozort.advancedages.content.blocks.Entity;

import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.makozort.advancedages.content.blocks.Entity.RefineLogic.RefiningRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class    OilFilterBlockEntity extends KineticBlockEntity implements IHaveGoggleInformation {
    public OilFilterBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    public int getTransferRate() {
        float rpm = (Math.abs(this.getSpeed()));
        return Math.round(400 * (rpm/256));
    }


    @Override
    public void tick() {
        if (this.level.getBlockEntity(this.getBlockPos().below()) instanceof SteelFluidTankBlockEntity tankBelow && this.level.getBlockEntity(this.getBlockPos().above()) instanceof SteelFluidTankBlockEntity tankAbove) {
            tankAbove = (tankAbove.isController() ? tankAbove : (SteelFluidTankBlockEntity) level.getBlockEntity(tankAbove.controller));
            tankBelow = (tankBelow.isController() ? tankBelow : (SteelFluidTankBlockEntity) level.getBlockEntity(tankBelow.controller));
            FluidTank aboveInv = tankAbove.getControllerBE().tankInventory1;
            FluidTank belowInv = tankBelow.getControllerBE().tankInventory1;

            //TODO: add heat and gasses
            if (RefiningRecipes.getRefiningRecipes().containsKey(belowInv.getFluid().getRawFluid().getFluidType())) {
                Fluid result = RefiningRecipes.getRefiningRecipes().get(tankBelow.getControllerBE().tankInventory1.getFluid().getRawFluid().getFluidType());
                    for (int i = this.getTransferRate(); i >= 0; i--) {
                        if (aboveInv.getSpace() >= i && belowInv.getFluidAmount() >= i) {
                            belowInv.drain(i, IFluidHandler.FluidAction.EXECUTE);
                            FluidStack stack = new FluidStack(result,i);
                            aboveInv.fill(stack, IFluidHandler.FluidAction.EXECUTE);
                            break;
                    }
                }
            }
        }
    }

}

