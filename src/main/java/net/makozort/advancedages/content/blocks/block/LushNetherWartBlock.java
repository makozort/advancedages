package net.makozort.advancedages.content.blocks.block;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import team.lodestar.lodestone.registry.common.particle.LodestoneParticleRegistry;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;

import java.util.Random;

public class LushNetherWartBlock extends CropBlock {

    public static final int MAX_AGE = 3;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;

    public LushNetherWartBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return Items.NETHER_WART;
    }

    @Override
    protected IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        Random r = new Random();
        for (int j = 0; j < 60; j += 5) {
            WorldParticleBuilder.create(LodestoneParticleRegistry.TWINKLE_PARTICLE)
                    .setScaleData(GenericParticleData.create(.25f, 0).build())
                    .setTransparencyData(GenericParticleData.create(100f, 0.25f).build())
                    .setLifetime(40)
                    .enableNoClip()
                    .spawn(level, pos.getX() + 0 + r.nextDouble() * (1 - 0), pos.getY() + 0 + r.nextDouble() * (1 - 0), pos.getZ() + 0 + r.nextDouble() * (1 - 0));
        }
        level.playSound(null, pos, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.MASTER, 16, 1.0F);
        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AGE);
    }
}
