package net.makozort.advancedages.content.blocks.block;

import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.makozort.advancedages.content.blocks.Entity.RadioBlockEntity;
import net.makozort.advancedages.reg.AllBlockEntities;
import net.makozort.advancedages.reg.AllBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import java.util.List;

public class RadioBlock extends HorizontalDirectionalBlock implements IBE<RadioBlockEntity> {




    public RadioBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public Class<RadioBlockEntity> getBlockEntityClass() {
        return RadioBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends RadioBlockEntity> getBlockEntityType() {
        return AllBlockEntities.RADIO.get();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }
}