package net.makozort.advancedages.content.blocks.Entity;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class HornBlockEntity extends BlockEntity {


    protected final ContainerData data;
    private int cooldown = 0;

    public HornBlockEntity(BlockEntityType<?> type, BlockPos p_155229_, BlockState p_155230_) {
        super(type, p_155229_, p_155230_);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return HornBlockEntity.this.cooldown;
            }

            @Override
            public void set(int i, int p_39286_) {
                HornBlockEntity.this.cooldown = i;
            }

            @Override
            public int getCount() {
                return 0;
            }
        };
    }

    public void play(Level level, BlockPos pos, SoundEvent soundEvent, int dis, int time) {
        if (this.cooldown == 0) {
            level.playSound(null, pos, soundEvent, SoundSource.RECORDS, dis, 1.0F);
            this.cooldown = time;
        }
    }

    public static void tick(Level level, BlockPos pos, BlockState state, HornBlockEntity hornBlockEntity) {
        if (hornBlockEntity.cooldown > 0) {
            hornBlockEntity.cooldown--;
        }
    }
}
