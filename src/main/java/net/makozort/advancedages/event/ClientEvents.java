package net.makozort.advancedages.event;

import net.makozort.advancedages.AdvancedAges;
import net.makozort.advancedages.client.MaskHudOverlay;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = AdvancedAges.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        @Mod.EventBusSubscriber(modid = AdvancedAges.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
        public static class ClientModBusEvents {
            @SubscribeEvent
            public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
                event.registerAboveAll("mask", MaskHudOverlay.HUD_MASK);
            }
        }
    }
}