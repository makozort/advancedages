package net.makozort.advancedages.content.blocks.block;

import com.simibubi.create.foundation.block.IBE;
import net.makozort.advancedages.content.blocks.Entity.AntennaBlockEntity;
import net.makozort.advancedages.content.blocks.Entity.RadioBlockEntity;
import net.makozort.advancedages.reg.AllBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class AntennaBlock extends Block implements IBE<AntennaBlockEntity> {
    public static final BooleanProperty CONNECTED = BooleanProperty.create("connected");


    public AntennaBlock(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(CONNECTED, false);
    }


    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        if (pOldState.getBlock() != pState.getBlock()) {
            if (!pIsMoving) {
                BlockEntity te = pLevel.getBlockEntity(pPos);
                if (te instanceof AntennaBlockEntity abe) {
                    abe.updateConnectivity();
                }
            }
        }
    }

    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean pIsMoving) {
        if (state.hasBlockEntity() && (state.getBlock() != newState.getBlock() || !newState.hasBlockEntity())) {
            BlockEntity te = world.getBlockEntity(pos);

            world.removeBlockEntity(pos);
            if (te instanceof AntennaBlockEntity abe && abe.controllerPos != null && world.getBlockEntity(abe.controllerPos) instanceof RadioBlockEntity controller) {
                controller.connectUnitsAbove();
            }
        }
    }


    // blockstate stuff


    @Override
    public Class<AntennaBlockEntity> getBlockEntityClass() {
        return AntennaBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends AntennaBlockEntity> getBlockEntityType() {
        return AllBlockEntities.ANTENNA.get();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(CONNECTED);
    }

}
