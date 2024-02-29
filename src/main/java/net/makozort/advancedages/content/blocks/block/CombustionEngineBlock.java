package net.makozort.advancedages.content.blocks.block;

import com.simibubi.create.content.kinetics.base.HorizontalKineticBlock;
import com.simibubi.create.foundation.block.IBE;
import net.makozort.advancedages.content.blocks.Entity.CombustionEngine.combustionEngineBlockEntity;
import net.makozort.advancedages.reg.AllBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CombustionEngineBlock extends HorizontalKineticBlock implements IBE<combustionEngineBlockEntity> {

    public CombustionEngineBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public Class<combustionEngineBlockEntity> getBlockEntityClass() {
        return combustionEngineBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends combustionEngineBlockEntity> getBlockEntityType() {
        return AllBlockEntities.combustion_engine.get();
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return face == state.getValue(HORIZONTAL_FACING).getOpposite();
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return  state.getValue(HORIZONTAL_FACING).getAxis();
    }
}
