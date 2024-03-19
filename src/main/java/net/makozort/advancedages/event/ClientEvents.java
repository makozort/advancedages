package net.makozort.advancedages.event;

import net.makozort.advancedages.AdvancedAges;
import net.makozort.advancedages.client.MaskHudOverlay;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import team.lodestar.lodestone.handlers.screenparticle.ScreenParticleHandler;
import team.lodestar.lodestone.systems.particle.screen.ScreenParticleHolder;

@Mod.EventBusSubscriber(modid = AdvancedAges.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEvents {
    public static final ScreenParticleHolder SCREEN_PARTICLES = new ScreenParticleHolder();

    @SubscribeEvent
    public static void registerOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("mask", MaskHudOverlay.HUD_MASK);
        event.registerAbove(VanillaGuiOverlay.PLAYER_LIST.id(), "particles", (gui, poseStack, partialTick, width, height) ->
                renderParticles(poseStack));
    }

    public static void renderParticles(GuiGraphics guiGraphics) {
        if (ScreenParticleHandler.canSpawnParticles) {
            SCREEN_PARTICLES.tick();
        }
        ScreenParticleHandler.renderParticles(SCREEN_PARTICLES);
    }
}