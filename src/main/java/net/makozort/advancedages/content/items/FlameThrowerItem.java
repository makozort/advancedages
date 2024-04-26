package net.makozort.advancedages.content.items;

import com.mojang.blaze3d.vertex.PoseStack;
import net.makozort.advancedages.AdvancedAges;
import net.makozort.advancedages.foundation.util.gun.ConeGun;
import net.makozort.advancedages.reg.AllSoundEvents;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleTypes;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class FlameThrowerItem extends Item {
    java.util.Random random = new java.util.Random();
    public FlameThrowerItem(Properties pProperties) {
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
        airBlast(pPlayer,pLevel);
        pPlayer.getCooldowns().addCooldown(this, 50);
        pPlayer.getItemInHand(pHand).hurtAndBreak(1, pPlayer, p -> p.broadcastBreakEvent(pHand));
        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (pEntity instanceof  Player player && player.isShiftKeyDown() && player.getMainHandItem().is(pStack.getItem())) {
            shoot(player,pLevel);
            player.startUsingItem(player.getUsedItemHand());
            player.getMainHandItem().hurtAndBreak(1, player, p -> p.broadcastBreakEvent(player.getUsedItemHand()));
        }
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }


    public void airBlast(Player player, Level level) {
        if (player.getServer() != null) {
            level.playSound(null, player.getOnPos(), AllSoundEvents.FLAME_AIR.get(), SoundSource.MASTER, .25F, 1);
            List<Entity> entities = ConeGun.coneCheck(player,10,5,level);
            for (Entity e : entities)
            {
                e.clearFire();
                level.playSound(null, e.getOnPos(), AllSoundEvents.FLAME_EX.get(), SoundSource.MASTER, .25F, 1);
            }
        }
    }

    public void shoot(Player player, Level level) {
        if (player.getServer() != null) {
            level.playSound(null, player.getOnPos(), AllSoundEvents.FLAME_LOOP.get(), SoundSource.MASTER, .25F, random.nextFloat());

            List<Entity> entities = ConeGun.coneCheck(player, 10, 4, level);
            for (Entity e : entities) {
                e.setSecondsOnFire(5);
                e.hurt(level.damageSources().generic(), 5);
            }

            // line
            ServerLevel serverLevel = (ServerLevel) level;
            for (int i = 0; i < 10; i++) {
                serverLevel.sendParticles(ParticleTypes.FLAME, player.getEyePosition().x, player.getEyePosition().y - .3, player.getEyePosition().z,
                        0, player.getLookAngle().x + random.nextFloat() * (.4f - .2f), player.getLookAngle().y + random.nextFloat() * (.5f - .2f), player.getLookAngle().z + random.nextFloat() * (.5f - .2f), .5);
            }
        }
    }
}
