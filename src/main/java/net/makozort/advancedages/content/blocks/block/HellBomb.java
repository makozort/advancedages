package net.makozort.advancedages.content.blocks.block;

import net.makozort.advancedages.networking.ModPackets;
import net.makozort.advancedages.networking.packet.BombPacket;
import net.makozort.advancedages.reg.AllFluids;
import net.makozort.advancedages.reg.AllSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.network.PacketDistributor;
import team.lodestar.lodestone.helpers.BlockHelper;
import team.lodestar.lodestone.network.screenshake.PositionedScreenshakePacket;
import team.lodestar.lodestone.registry.common.LodestonePacketRegistry;
import team.lodestar.lodestone.systems.easing.Easing;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class HellBomb extends Block {


    public static int MAX_EXPLOSION_SIZE = 60;

    public static int MAX_EXPLOSION_RANGE = 600;

    private static final HashMap<Block, Block> convertMap = new HashMap<>();

    static {
        convertMap.put(Blocks.SAND, Blocks.GLASS);
        convertMap.put(Blocks.STONE, Blocks.MAGMA_BLOCK);
        convertMap.put(Blocks.DEEPSLATE, Blocks.LAVA);
    }

    public HellBomb(Properties pProperties) {
        super(pProperties);
    }

    public static void explode(Level level, BlockPos pos, int diameter,boolean convert) {
        int radius = diameter / 2;
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        Random random = new Random();
        // Perform the initial explosion
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    if (x * x + y * y + z * z <= radius * radius) {
                        mutableBlockPos.set(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
                        if (level.getBlockState(mutableBlockPos).getDestroySpeed(level, mutableBlockPos) != -1.0F) {
                            level.setBlock(mutableBlockPos, Blocks.AIR.defaultBlockState(), 3);
                        }
                    }
                }
            }
        }
        if (convert) {
            int outerRadius = radius + 1;
            for (int x = -outerRadius; x <= outerRadius; x++) {
                for (int y = -outerRadius; y <= outerRadius; y++) {
                    for (int z = -outerRadius; z <= outerRadius; z++) {
                        if (x * x + y * y + z * z > radius * radius && x * x + y * y + z * z <= outerRadius * outerRadius) {
                            mutableBlockPos.set(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
                            if (convertMap.containsKey(level.getBlockState(mutableBlockPos).getBlock())) {
                                int chance = random.nextInt(100);
                                if (chance < 25) {
                                    level.setBlock(mutableBlockPos, convertMap.get(level.getBlockState(mutableBlockPos).getBlock()).defaultBlockState(), 3);

                                }
                            }
                        }
                    }
                }
            }
        }
        if (diameter >= 50) {
            int explosionsCount = 20;
            for (int i = 0; i < explosionsCount; i++) {
                double theta = random.nextDouble() * Math.PI * 2;
                double phi = random.nextDouble() * Math.PI - Math.PI / 2;
                int x = (int) (radius * Math.cos(theta) * Math.cos(phi));
                int y = (int) (radius * Math.sin(phi));
                int z = (int) (radius * Math.sin(theta) * Math.cos(phi));
                mutableBlockPos.set(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
                level.explode(null, mutableBlockPos.getX(), mutableBlockPos.getY(), mutableBlockPos.getZ(), 10.0F, Level.ExplosionInteraction.TNT);
            }
        }
        AABB sphereArea = new AABB(pos).inflate(radius);
        List<Entity> entitiesWithinSphere = level.getEntities(null, sphereArea);
        for (Entity entity : entitiesWithinSphere) {
            double distanceSquared = entity.position().distanceToSqr(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
            if (distanceSquared <= radius * radius) {
                entity.hurt(level.damageSources().explosion(null), 60.0F);
            } else if (distanceSquared <= (radius + 5) * (radius + 5)) {
                if (entity instanceof Player) {
                    entity.hurt(level.damageSources().explosion(null), 15.0F);
                }
            }
        }
    }

    public float getExplosionScale() {
        return 1f;
    }

    public void neighborChanged(BlockState pState, Level level, BlockPos pos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        if (level.hasNeighborSignal(pos)) {
            if (!level.isClientSide && level instanceof ServerLevel) {
                ServerLevel serverLevel = (ServerLevel) level;
                for (ServerPlayer player : serverLevel.players()) {
                    if (player.blockPosition().distSqr(pos) <= (MAX_EXPLOSION_RANGE * getExplosionScale()) * (MAX_EXPLOSION_RANGE * getExplosionScale())) {
                        if (getExplosionScale() >= .6) {
                            ModPackets.sendToPlayer(new BombPacket(pos, getExplosionScale(), true, true, true), player);
                        } else {
                            ModPackets.sendToPlayer(new BombPacket(pos, getExplosionScale(), false, false, false), player);

                        }
                    }
                }
            }
            level.playSound(null, pos, AllSoundEvents.HELL_BOMB.get(), SoundSource.MASTER, 20 * getExplosionScale(), .5F);
            LodestonePacketRegistry.LODESTONE_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(pos)),
                    new PositionedScreenshakePacket(200, BlockHelper.fromBlockPos(pos), 16, 200f * getExplosionScale(), Easing.EXPO_OUT).setIntensity(50f, .4f)); //MAKE THIS SCALE WITH SIZE
            explode(level, pos, (int) (MAX_EXPLOSION_SIZE * getExplosionScale()),getExplosionScale() > .6);
            level.removeBlock(pos, false);
        }
    }

}
