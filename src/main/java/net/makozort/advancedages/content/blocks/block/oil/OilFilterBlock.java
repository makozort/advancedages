package net.makozort.advancedages.content.blocks.block.oil;

import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.foundation.block.IBE;
import net.makozort.advancedages.content.blocks.Entity.OilFilterBlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class OilFilterBlock extends FaceAttachedHorizontalDirectionalBlock implements IWrenchable, IBE<OilFilterBlockEntity> {
    public OilFilterBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public Class<OilFilterBlockEntity> getBlockEntityClass() {
        return null;
    }

    @Override
    public BlockEntityType<? extends OilFilterBlockEntity> getBlockEntityType() {
        return null;
    }

    public static Direction getFacing(BlockState sideState) {
        return getConnectedDirection(sideState);
    }
}
