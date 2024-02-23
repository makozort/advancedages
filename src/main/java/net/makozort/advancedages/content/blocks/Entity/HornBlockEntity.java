package net.makozort.advancedages.content.blocks.Entity;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraftforge.network.PacketDistributor;
import team.lodestar.lodestone.helpers.BlockHelper;
import team.lodestar.lodestone.network.screenshake.PositionedScreenshakePacket;
import team.lodestar.lodestone.registry.common.LodestonePacketRegistry;
import team.lodestar.lodestone.systems.easing.Easing;

public class HornBlockEntity extends KineticBlockEntity {


    protected final ContainerData data;
    private int cooldown = 0;

    public static final BooleanProperty LIT = BooleanProperty.create("lit");

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




    public void play(Level level, BlockPos pos, SoundEvent soundEvent, float dis, int time, int duration, BlockState state) {
        if (this.cooldown == 0) {
            level.playSound(null, pos, soundEvent, SoundSource.RECORDS, dis, 1.0F);
            level.setBlock(pos, state.setValue(LIT, true), 3);
            LodestonePacketRegistry.LODESTONE_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(pos)), new PositionedScreenshakePacket(duration, BlockHelper.fromBlockPos(pos), (dis*16), 200f, Easing.EXPO_OUT).setIntensity(2f, 0));
            this.cooldown = time;
        }
    }

    @Override
    public void tick() {
        if (this.cooldown > 0) {
            this.cooldown--;
        }
    }
}

