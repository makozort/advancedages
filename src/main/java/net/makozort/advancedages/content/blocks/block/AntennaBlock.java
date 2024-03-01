package net.makozort.advancedages.content.blocks.block;

import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import net.makozort.advancedages.content.blocks.Entity.AntennaBlockEntity;
import net.makozort.advancedages.content.blocks.Entity.RadioBlockEntity;
import net.makozort.advancedages.reg.AllBlockEntities;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
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
