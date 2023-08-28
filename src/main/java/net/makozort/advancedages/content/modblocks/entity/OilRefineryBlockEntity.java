package net.makozort.advancedages.content.modblocks.entity;

import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import com.simibubi.create.foundation.fluid.CombinedTankWrapper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.IFluidHandler;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class OilRefineryBlockEntity extends SmartBlockEntity implements IHaveGoggleInformation {
    private static final int TANK_CAPACITY = 80000;
    SmartFluidTankBehaviour inputtank;
    SmartFluidTankBehaviour outputtank;
    protected LazyOptional<IFluidHandler> fluidCapability;
    //protected LazyOptional<IItemHandler> itemCapabiliy;
    public OilRefineryBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        behaviours.add(outputtank = SmartFluidTankBehaviour.single(this,1000000)
                .allowExtraction()
                .forbidInsertion()
        );
        behaviours.add(inputtank = SmartFluidTankBehaviour.single(this,TANK_CAPACITY)
                .allowInsertion()
                .forbidExtraction()
        );
        fluidCapability = LazyOptional.of(() -> {
          LazyOptional<? extends  IFluidHandler> inCap = inputtank.getCapability();
            LazyOptional<? extends  IFluidHandler> outCap = outputtank.getCapability();
            return new CombinedTankWrapper(outCap.orElse(null), inCap.orElse(null));
        });
    }

    @Override
    public void onLoad() {
        super.onLoad();
    }
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        //if (cap == ForgeCapabilities.ITEM_HANDLER)
        //    return itemCapabiliy.cast();
        if (cap == ForgeCapabilities.FLUID_HANDLER)
            return fluidCapability.cast();
        return super.getCapability(cap,side);
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        return containedFluidTooltip(tooltip, isPlayerSneaking, fluidCapability.cast());
    }

}
