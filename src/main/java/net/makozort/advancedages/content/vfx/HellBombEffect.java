package net.makozort.advancedages.content.vfx;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import team.lodestar.lodestone.registry.common.particle.LodestoneParticleRegistry;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;

import java.awt.*;
import java.util.Random;


public class HellBombEffect {


    public static Color centre = new Color(255, 255, 255);
    public static Color outer = new Color(180, 102, 15);
    public static Color smoke = new Color(0, 0, 0);
    public static BlockPos getRandomBlockPosInRange(BlockPos original, int range, Random random) {
        int x = original.getX() + random.nextInt(range * 2 + 1) - range;
        int y = original.getY() + random.nextInt(range * 2 + 1) - range;
        int z = original.getZ() + random.nextInt(range * 2 + 1) - range;
        return new BlockPos(x, y, z);
    }

    public static void spawn(Level level, BlockPos pos) {
        centre(level,pos);
        smoke(level, pos);
    }

    public static void centre(Level level, BlockPos pos) {
        for (int i = 0; i < 400; i++) {
            Random random = new Random();
            WorldParticleBuilder.create(LodestoneParticleRegistry.WISP_PARTICLE)
                    .setScaleData(GenericParticleData.create(20f).setEasing(Easing.EXPO_IN_OUT).build())
                    .setTransparencyData(GenericParticleData.create(.5f,0).build())
                    .setColorData(ColorParticleData.create(centre, centre).build())
                    .enableForcedSpawn()
                    .setLifetime(80)
                    .enableNoClip()
                    .spawn(level, getRandomBlockPosInRange(pos,23,random).getX(), pos.getY(), getRandomBlockPosInRange(pos,23,random).getZ()); // initial inner white


            WorldParticleBuilder.create(LodestoneParticleRegistry.WISP_PARTICLE)
                    .setScaleData(GenericParticleData.create(40f).setEasing(Easing.BOUNCE_IN).setEasing(Easing.BACK_OUT).build())
                    .setTransparencyData(GenericParticleData.create(.5f,0).build())
                    .enableForcedSpawn()
                    .setColorData(ColorParticleData.create(outer, smoke).setEasing(Easing.BOUNCE_IN).build())
                    .setLifetime(80)
                    .enableNoClip()
                    .spawn(level, getRandomBlockPosInRange(pos,23,random).getX(), pos.getY(), getRandomBlockPosInRange(pos,23,random).getZ()); // outer orange

        }
        WorldParticleBuilder.create(LodestoneParticleRegistry.WISP_PARTICLE)
                .setScaleData(GenericParticleData.create(40f).setEasing(Easing.BOUNCE_IN).setEasing(Easing.BACK_OUT).build())
                .setTransparencyData(GenericParticleData.create(.5f).build())
                .setColorData(ColorParticleData.create(outer, outer).setCoefficient(1.4f).setEasing(Easing.BOUNCE_IN).build())
                .setLifetime(20)
                .setSpinData(SpinParticleData.create(0.2f, 0.4f).setSpinOffset((level.getGameTime() * 0.2f) % 6.28f).setEasing(Easing.QUARTIC_IN).build())
                .enableNoClip()
                .spawn(level, pos.getX(), pos.getY() + 5, pos.getZ());
    }

    public static void smoke(Level level, BlockPos pos) {
        for (int i = 0; i < 400; i++) {
            Random random = new Random();
            WorldParticleBuilder.create(LodestoneParticleRegistry.SMOKE_PARTICLE)
                    .setScaleData(GenericParticleData.create(12.5f).build())
                    .setTransparencyData(GenericParticleData.create(.2f,0).build())
                    .enableForcedSpawn()
                    .setColorData(ColorParticleData.create(outer, smoke).setEasing(Easing.BOUNCE_OUT).build())
                    .setLifetime(2000)
                    .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                    .setRandomMotion(.2,0,.2)
                    .addMotion(0, 0.2f, 0)
                    .enableNoClip()
                    .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                    .spawn(level, getRandomBlockPosInRange(pos,23,random).getX(), pos.getY(), getRandomBlockPosInRange(pos,23,random).getZ()); //mushroom cloud

            WorldParticleBuilder.create(LodestoneParticleRegistry.WISP_PARTICLE)
                    .setScaleData(GenericParticleData.create(10f).build())
                    .setTransparencyData(GenericParticleData.create(.2f,0).build())
                    .enableForcedSpawn()
                    .setColorData(ColorParticleData.create(smoke, outer).setEasing(Easing.BOUNCE_OUT).build())
                    .setLifetime(2000)
                    .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                    .setRandomMotion(.3*((double) i /2),0,.3*((double) i /2))
                    .addMotion(0, 0.3*((double) i /2), 0)
                    .enableNoClip()
                    .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                    .spawn(level, getRandomBlockPosInRange(pos,23,random).getX(), pos.getY(), getRandomBlockPosInRange(pos,23,random).getZ()); // smoke explosion

            WorldParticleBuilder.create(LodestoneParticleRegistry.WISP_PARTICLE)
                    .setScaleData(GenericParticleData.create(10f).build())
                    .setTransparencyData(GenericParticleData.create(.2f,0).build())
                    .enableForcedSpawn()
                    .setColorData(ColorParticleData.create(smoke, smoke).setEasing(Easing.BOUNCE_OUT).build())
                    .setLifetime(2000)
                    .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                    .setRandomMotion(.2,0,.2)
                    .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                    .addMotion(0, 0.3/(i), 0)
                    .enableNoClip()
                    .spawn(level, getRandomBlockPosInRange(pos,23,random).getX(), pos.getY() - 10, getRandomBlockPosInRange(pos,23,random).getZ()); // smoking spread

                WorldParticleBuilder.create(LodestoneParticleRegistry.SMOKE_PARTICLE)
                        .setScaleData(GenericParticleData.create(10f).build())
                        .setTransparencyData(GenericParticleData.create(.2f,0).build())
                        .enableForcedSpawn()
                        .setColorData(ColorParticleData.create(outer, smoke).setEasing(Easing.BOUNCE_OUT).build())
                        .setLifetime(2000)
                        .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                        .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                        .addMotion(0, (double) 1 /(i), 0)
                        .enableNoClip()
                        .spawn(level, getRandomBlockPosInRange(pos,15,random).getX(), pos.getY()-15, getRandomBlockPosInRange(pos,15,random).getZ()); // smoke collum rise

                WorldParticleBuilder.create(LodestoneParticleRegistry.WISP_PARTICLE)
                        .setScaleData(GenericParticleData.create(10f).build())
                        .setTransparencyData(GenericParticleData.create(.2f,0).build())
                        .enableForcedSpawn()
                        .setColorData(ColorParticleData.create(outer, outer).build())
                        .setLifetime(2000)
                        .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                        //.setRandomMotion(.3/(i),0,.3/(i))
                        .enableNoClip()
                        .spawn(level, getRandomBlockPosInRange(pos,23,random).getX(), pos.getY()-20, getRandomBlockPosInRange(pos,23,random).getZ()); // FIRE
        }
    }

}
