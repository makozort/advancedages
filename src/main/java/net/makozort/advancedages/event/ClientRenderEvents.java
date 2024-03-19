package net.makozort.advancedages.event;


import net.makozort.advancedages.AdvancedAges;
import net.makozort.advancedages.content.vfx.SphereEngine;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = AdvancedAges.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientRenderEvents {
    @SubscribeEvent
    public static void renderSpheres(RenderLevelStageEvent event) {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_WEATHER) {
            SphereEngine.RenderSpheres(event);
        }
    }
}

