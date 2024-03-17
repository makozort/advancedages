package net.makozort.advancedages.content.vfx;

import net.makozort.advancedages.event.ClientEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import team.lodestar.lodestone.registry.common.particle.LodestoneParticleRegistry;
import team.lodestar.lodestone.registry.common.particle.LodestoneScreenParticleRegistry;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions;
import team.lodestar.lodestone.systems.particle.builder.ScreenParticleBuilder;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;

import java.awt.*;
import java.util.Random;


public class BombEffectEngine {


    public static Color centre = new Color(255, 255, 255);
    public static Color outer = new Color(180, 102, 15);
    public static Color smoke = new Color(0, 0, 0);
   
    public static float MAX_EXPLOSION_SCALE = 40f;  
    
    public static float NORMAL_SPREAD = 23;

    public static float SMOKE_SCALE = 10f;

    public static float FIRE_OFFSET = 20f;

    public static float SMOKE_OFFSET = 15;
    public static void spawn(Level level, BlockPos pos,float scalar, boolean smoke, boolean flash) {
        centre(level,pos,scalar);
        if (smoke) {
            smoke(level, pos,scalar);
        }
        if (flash){
            ScreenParticleBuilder.create(LodestoneScreenParticleRegistry.WISP, ClientEvents.SCREEN_PARTICLES)
                    .setScaleData(GenericParticleData.create(999999999).setEasing(Easing.EXPO_IN_OUT).build())
                    .setTransparencyData(GenericParticleData.create(.8f).build())
                    .setColorData(ColorParticleData.create(outer, outer).build())
                    .setLifetime(80)
                    .spawn(pos.getX(), (pos.getZ()));
        }
    }

    public static void centre(Level level, BlockPos pos,float scalar) {
        for (int i = 0; i < 400; i++) {
            Random random = new Random();
            WorldParticleBuilder.create(LodestoneParticleRegistry.WISP_PARTICLE)
                    .setScaleData(GenericParticleData.create((MAX_EXPLOSION_SCALE*scalar)/2).setEasing(Easing.EXPO_IN_OUT).build())
                    .setTransparencyData(GenericParticleData.create(.5f,0).build())
                    .setColorData(ColorParticleData.create(centre, centre).build())
                    .enableForcedSpawn()
                    .setLifetime(80)
                    .enableNoClip()
                    .spawn(level, getRandomBlockPosInRange(pos,(int) (NORMAL_SPREAD*scalar),random).getX(), pos.getY(), getRandomBlockPosInRange(pos,(int) (NORMAL_SPREAD*scalar),random).getZ()); // initial inner white


            WorldParticleBuilder.create(LodestoneParticleRegistry.WISP_PARTICLE)
                    .setScaleData(GenericParticleData.create(MAX_EXPLOSION_SCALE*scalar).setEasing(Easing.BOUNCE_IN).setEasing(Easing.BACK_OUT).build())
                    .setTransparencyData(GenericParticleData.create(.5f,0).build())
                    .enableForcedSpawn()
                    .setColorData(ColorParticleData.create(outer, smoke).setEasing(Easing.BOUNCE_IN).build())
                    .setLifetime(80)
                    .enableNoClip()
                    .spawn(level, getRandomBlockPosInRange(pos, (int) (NORMAL_SPREAD*scalar),random).getX(), pos.getY(), getRandomBlockPosInRange(pos,(int) (NORMAL_SPREAD*scalar),random).getZ()); // outer orange

        }
        WorldParticleBuilder.create(LodestoneParticleRegistry.WISP_PARTICLE)
                .setScaleData(GenericParticleData.create((MAX_EXPLOSION_SCALE*scalar)*2).setEasing(Easing.BOUNCE_IN).setEasing(Easing.BACK_OUT).build())
                .setTransparencyData(GenericParticleData.create(.8f).build())
                .setColorData(ColorParticleData.create(outer, outer).setCoefficient(1.4f).setEasing(Easing.BOUNCE_IN).build())
                .setLifetime(20)
                .setSpinData(SpinParticleData.create(0.2f, 0.4f).setSpinOffset((level.getGameTime() * 0.2f) % 6.28f).setEasing(Easing.QUARTIC_IN).build())
                .enableNoClip()
                .spawn(level, pos.getX(), pos.getY() + 5, pos.getZ());
    }

    public static void smoke(Level level, BlockPos pos,float scalar) {
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
                    .spawn(level, getRandomBlockPosInRange(pos,(int) (NORMAL_SPREAD*scalar),random).getX(), pos.getY(), getRandomBlockPosInRange(pos,(int) (NORMAL_SPREAD*scalar),random).getZ()); //mushroom cloud

            WorldParticleBuilder.create(LodestoneParticleRegistry.WISP_PARTICLE)
                    .setScaleData(GenericParticleData.create(SMOKE_SCALE*scalar).build())
                    .setTransparencyData(GenericParticleData.create(.2f,0).build())
                    .enableForcedSpawn()
                    .setColorData(ColorParticleData.create(smoke, outer).setEasing(Easing.BOUNCE_OUT).build())
                    .setLifetime(2000)
                    .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                    .setRandomMotion(.3*((double) i /2),0,.3*((double) i /2))
                    .addMotion(0, 0.3*((double) i /2), 0)
                    .enableNoClip()
                    .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                    .spawn(level, getRandomBlockPosInRange(pos,(int) (NORMAL_SPREAD*scalar),random).getX(), pos.getY(), getRandomBlockPosInRange(pos,(int) (NORMAL_SPREAD*scalar),random).getZ()); // smoke explosion

            WorldParticleBuilder.create(LodestoneParticleRegistry.WISP_PARTICLE)
                    .setScaleData(GenericParticleData.create(SMOKE_SCALE*scalar).build())
                    .setTransparencyData(GenericParticleData.create(.2f,0).build())
                    .enableForcedSpawn()
                    .setColorData(ColorParticleData.create(smoke, smoke).setEasing(Easing.BOUNCE_OUT).build())
                    .setLifetime(2000)
                    .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                    .setRandomMotion(.2,0,.2)
                    .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                    .addMotion(0, 0.3/(i), 0)
                    .enableNoClip()
                    .spawn(level, getRandomBlockPosInRange(pos,(int) (NORMAL_SPREAD*scalar),random).getX(), pos.getY() - 10, getRandomBlockPosInRange(pos,(int) (NORMAL_SPREAD*scalar),random).getZ()); // smoking spread

                WorldParticleBuilder.create(LodestoneParticleRegistry.SMOKE_PARTICLE)
                        .setScaleData(GenericParticleData.create(SMOKE_SCALE*scalar).build())
                        .setTransparencyData(GenericParticleData.create(.2f,0).build())
                        .enableForcedSpawn()
                        .setColorData(ColorParticleData.create(outer, smoke).setEasing(Easing.BOUNCE_OUT).build())
                        .setLifetime(2000)
                        .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                        .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                        .addMotion(0, (double) 1 /(i), 0)
                        .enableNoClip()
                        .spawn(level, getRandomBlockPosInRange(pos, (int) (15*scalar),random).getX(), pos.getY()-(SMOKE_OFFSET*scalar), getRandomBlockPosInRange(pos, (int) (15*scalar),random).getZ()); // smoke collum rise

                WorldParticleBuilder.create(LodestoneParticleRegistry.WISP_PARTICLE)
                        .setScaleData(GenericParticleData.create(SMOKE_SCALE*scalar).build())
                        .setTransparencyData(GenericParticleData.create(.2f,0).build())
                        .enableForcedSpawn()
                        .setColorData(ColorParticleData.create(outer, outer).build())
                        .setLifetime(2000)
                        .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                        .enableNoClip()
                        .spawn(level, getRandomBlockPosInRange(pos,(int) (NORMAL_SPREAD*scalar),random).getX(), pos.getY()-(FIRE_OFFSET*scalar), getRandomBlockPosInRange(pos,(int) (NORMAL_SPREAD*scalar),random).getZ()); // FIRE
        }
    }
    public static BlockPos getRandomBlockPosInRange(BlockPos original, int range, Random random) {
        int x = original.getX() + random.nextInt(range * 2 + 1) - range;
        int y = original.getY() + random.nextInt(range * 2 + 1) - range;
        int z = original.getZ() + random.nextInt(range * 2 + 1) - range;
        return new BlockPos(x, y, z);
    }

}
