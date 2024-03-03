package net.makozort.advancedages.content.blocks.Entity;

import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.makozort.advancedages.AdvancedAges;
import net.makozort.advancedages.content.data.DepositData;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.PacketDistributor;
import team.lodestar.lodestone.helpers.BlockHelper;
import team.lodestar.lodestone.network.screenshake.PositionedScreenshakePacket;
import team.lodestar.lodestone.registry.common.LodestonePacketRegistry;
import team.lodestar.lodestone.systems.easing.Easing;

import java.util.ArrayList;
import java.util.List;


public class ThumperBlockEntity extends KineticBlockEntity implements IHaveGoggleInformation {

    static int RESET_TIME = 6000; // time it takes for slam to recharge

    static int MAX_RANGE = 1000;
    static int MAX_LEVEL = 15; // how many slams to reach max range

    List<BlockPos> foundDeposits = new ArrayList<BlockPos>();
    private int cooldown;
    private int currentLevel;

    public ThumperBlockEntity(BlockEntityType<?> type, BlockPos p_155229_, BlockState p_155230_) {
        super(type, p_155229_, p_155230_);
    }

    public List<BlockPos> getFoundDeposits() {
        return this.foundDeposits;
    }

    public void slam() { //TODO: goto events and add functionality for saving data to a notepad
        boolean bool = true; // change this to logic around raycasting and whatever contraption stuff
        int range = (int) (MAX_RANGE * ((double) this.currentLevel / MAX_LEVEL));
        if (bool && this.currentLevel < MAX_LEVEL) {
            this.cooldown = RESET_TIME;
            if (this.level instanceof ServerLevel) {
                for (BlockPos pos : DepositData.get(this.level).oilSearch(this.getBlockPos(), range)) {
                    if (!foundDeposits.contains(pos)) {
                        foundDeposits.add(pos);
                    }
                }
                this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 2);
                if (range < (int) (MAX_RANGE * ((double) 1 / MAX_LEVEL))) {
                    int newRange = (int) (MAX_RANGE * ((double) 1 / MAX_LEVEL));
                    slamEffect(this.worldPosition, newRange);
                } else {
                    slamEffect(this.worldPosition, range);
                }
            }
            if (this.currentLevel < MAX_LEVEL) {
                this.currentLevel++;
            }
        }
    }

    public void slamEffect(BlockPos pos, int range) {
        level.playSound(null, pos, SoundEvents.LIGHTNING_BOLT_IMPACT, SoundSource.MASTER, ((float) range / 16), 1.0F);
        LodestonePacketRegistry.LODESTONE_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(this.worldPosition)),
                new PositionedScreenshakePacket(6, BlockHelper.fromBlockPos(pos), range, 200f, Easing.EXPO_OUT).setIntensity(1.2f, .4f));
    }

    @Override
    public void tick() {
        super.tick();
        if (this.cooldown == 0) {
            slam();
        } else {
            this.cooldown--;
        }
        this.setChanged();
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        boolean added = super.addToGoggleTooltip(tooltip, isPlayerSneaking);
        Component cooldownComponent = Component.literal("Slam cooldown: ").append(Component.literal(String.valueOf(this.cooldown / 20)).withStyle(ChatFormatting.AQUA));
        Component rangeComponent = Component.literal("Current Scan radius: ").append(Component.literal(String.valueOf((int) (MAX_RANGE * ((double) this.currentLevel / MAX_LEVEL)))).withStyle(ChatFormatting.AQUA));
        Component foundComponent = Component.literal("Deposits found: ").append(Component.literal(String.valueOf(this.foundDeposits.size())).withStyle(ChatFormatting.AQUA));
        tooltip.add(cooldownComponent);
        tooltip.add(rangeComponent);
        tooltip.add(foundComponent);
        return added;
    }

    //writes data out
    @Override
    protected void write(CompoundTag compound, boolean clientPacket) {
        super.write(compound, clientPacket);
        ListTag list = new ListTag();
        this.foundDeposits.forEach((BlockPos) -> {
            CompoundTag DepoTag = new CompoundTag();
            DepoTag.putInt("x", BlockPos.getX());
            DepoTag.putInt("y", BlockPos.getY());
            DepoTag.putInt("z", BlockPos.getZ());
            list.add(DepoTag);
        });
        compound.put(AdvancedAges.MOD_ID + "_Data_THUMPER_DEPO_LIST", list);
        compound.putInt(AdvancedAges.MOD_ID + "_Data_THUMPER_COOLDOWN", this.cooldown);
        compound.putInt(AdvancedAges.MOD_ID + "_Data_THUMPER_LEVEL", this.currentLevel);
    }

    // reads data in
    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        super.read(compound, clientPacket);
        ListTag list = compound.getList(AdvancedAges.MOD_ID + "_Data_THUMPER_DEPO_LIST", Tag.TAG_COMPOUND);
        for (Tag t : list) {
            CompoundTag pollTag = (CompoundTag) t;
            BlockPos pos = new BlockPos(pollTag.getInt("x"), pollTag.getInt("y"), pollTag.getInt("z"));
            this.foundDeposits.add(pos);
        }
        this.cooldown = compound.getInt(AdvancedAges.MOD_ID + "_Data_THUMPER_COOLDOWN");
        this.currentLevel = compound.getInt(AdvancedAges.MOD_ID + "_Data_THUMPER_LEVEL");
    }
}

