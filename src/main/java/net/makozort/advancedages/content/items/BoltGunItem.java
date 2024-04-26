package net.makozort.advancedages.content.items;

import com.mojang.blaze3d.vertex.PoseStack;
import net.makozort.advancedages.reg.AllSoundEvents;
import net.makozort.advancedages.reg.Allitems;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class BoltGunItem extends Item {

    public BoltGunItem(Properties pProperties) {
        super(pProperties);
    }
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {


            @Override
            public HumanoidModel.@Nullable ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
                return HumanoidModel.ArmPose.CROSSBOW_HOLD;
            }

            @Override
            public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {
                float f = -0.4F * Mth.sin(Mth.sqrt(swingProcess) * (float)Math.PI);
                float f1 = 0.2F * Mth.sin(Mth.sqrt(swingProcess) * ((float)Math.PI * 2F));
                float f2 = -0.2F * Mth.sin(swingProcess * (float)Math.PI);
                int i = arm == HumanoidArm.RIGHT ? 1 : -1;
                poseStack.translate((float)i * f, f1, f2);
                return IClientItemExtensions.super.applyForgeHandTransform(poseStack, player, arm, itemInHand, partialTick, equipProcess, swingProcess);
            }
        });
    }

    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (isLoaded(itemstack)) {
            pPlayer.getCooldowns().addCooldown(this, 20);
            setLoaded(itemstack, false);
            shoot(pPlayer,pLevel);
            return InteractionResultHolder.consume(itemstack);
        }
        if (!isLoaded(itemstack)) {
            if (tryLoad(pPlayer,pLevel)) {
                pPlayer.getCooldowns().addCooldown(this, 5);
                setLoaded(itemstack,true);
            } else {
                return InteractionResultHolder.fail(itemstack);
            }
        }
        return InteractionResultHolder.consume(itemstack);
    }


    public static boolean tryLoad(Player player, Level level) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            if (player.isCreative()){
                return true;
            }
            ItemStack itemStack = player.getInventory().getItem(i);
            if (itemStack.getItem().equals(Allitems.BOLT.asItem())) {
                itemStack.shrink(1);
                if (itemStack.isEmpty()) {
                    player.getInventory().setItem(i, ItemStack.EMPTY);
                }
                level.playSound(null, player.getOnPos(), SoundEvents.PISTON_CONTRACT, SoundSource.MASTER, 1, 1F);
                return true;
            }
        }
        return false;
    }
    public static boolean isLoaded(ItemStack pCrossbowStack) {
        CompoundTag compoundtag = pCrossbowStack.getTag();
        return compoundtag != null && compoundtag.getBoolean("Loaded");
    }

    public static void setLoaded(ItemStack rifleStack, boolean isLoaded) {
        CompoundTag compoundtag = rifleStack.getOrCreateTag();
        compoundtag.putBoolean("Loaded", isLoaded);
    }


    public static void shoot(Player player, Level level) {
        if (player.getServer() != null) {
            level.playSound(null, player.getOnPos(), SoundEvents.GENERIC_EXPLODE, SoundSource.MASTER, .25F, 1F);
            // kickback
            if (!player.isShiftKeyDown()) {
                Vec3 currentVelocity = player.getDeltaMovement();
                player.hurtMarked = true;
                Vec3 lookVector = player.getLookAngle();
                Vec3 dashVelocity = lookVector.scale(-.5);
                Vec3 newVelocity = currentVelocity.add(dashVelocity);
                player.setDeltaMovement(newVelocity);
            }


            // initial raytrace for blocks
            Vec3 start = player.getEyePosition();
            Vec3 end = start.add(player.getLookAngle().scale(80));
            BlockHitResult blockTrace = level.clip(new ClipContext(start,
                    start.add(player.getLookAngle().scale(80)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
            if (blockTrace.getType() != HitResult.Type.MISS)
                end = blockTrace.getLocation();
            EntityHitResult e = ProjectileUtil.getEntityHitResult(player, start, end, new AABB(start, end), Entity::canBeHitByProjectile, 80*80);

            // ray trace for entities
            if (e != null) {
                end = e.getLocation();
                e.getEntity().hurt(level.damageSources().generic(),15);
            }
            //firework spawn
            ItemStack fireworkStack = new ItemStack(Items.FIREWORK_ROCKET);
            CompoundTag fireworkTag = fireworkStack.getOrCreateTagElement("Fireworks");
            ListTag explosions = new ListTag();
            CompoundTag explosion = new CompoundTag();
            explosion.putIntArray("Colors", new int[]{0xFFFFFF});
            explosions.add(explosion);
            fireworkTag.putByte("Flight", (byte) -1);
            fireworkTag.put("Explosions", explosions);
            FireworkRocketEntity firework = new FireworkRocketEntity(level, end.x, end.y, end.z, fireworkStack);

            explosion.putIntArray("Colors", new int[]{0xC0C0C0});
            explosions.add(explosion);
            fireworkTag.putByte("Flight", (byte) -1);
            fireworkTag.put("Explosions", explosions);
            FireworkRocketEntity firework1 = new FireworkRocketEntity(level, end.x, end.y, end.z, fireworkStack);

            explosion.putIntArray("Colors", new int[]{0xFFFFFF});
            explosions.add(explosion);
            fireworkTag.putByte("Flight", (byte) -1);
            fireworkTag.put("Explosions", explosions);
            FireworkRocketEntity firework2 = new FireworkRocketEntity(level, end.x, end.y, end.z, fireworkStack);

            level.addFreshEntity(firework);
            level.addFreshEntity(firework1);
            level.addFreshEntity(firework2);

            // line
            double dx = end.x - start.x;
            double dy = end.y - start.y;
            double dz = end.z - start.z;
            for (int step = 0; step <= 15; step++) {
                double x = start.x + (dx * step / 30);
                double y = start.y + (dy * step / 30);
                double z = start.z + (dz * step / 30);
                ServerLevel serverLevel = (ServerLevel) level;
                serverLevel.sendParticles(ParticleTypes.SMOKE, x, y, z, 1, 0.0D, 0.0D,0.0D,0);
            }
        }
    } // if you are looking at this im sorry
}
