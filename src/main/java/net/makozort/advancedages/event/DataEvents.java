package net.makozort.advancedages.event;

import net.makozort.advancedages.AdvancedAges;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AdvancedAges.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataEvents {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void gatherData(GatherDataEvent event) {
        AdvancedAges.REGISTRATE.addRawLang("item.advancedages.pollution_mask.tooltip.summary","provides a certain amount of protection from _pollution_ to the user");
        AdvancedAges.REGISTRATE.addRawLang("item.advancedages.crude_oil_bucket.tooltip.summary","A useless fluid that can be _refined_...");
        AdvancedAges.REGISTRATE.addRawLang("item.advancedages.pollution_sponge.tooltip.summary","resets the _pollution_ level in a 30 block radius");
        AdvancedAges.REGISTRATE.addRawLang("item.advancedages.oil_scanner.tooltip.summary","searches directly under user for _crude _oil");
        AdvancedAges.REGISTRATE.addRawLang("item.advancedages.pollution_detector.tooltip.summary","finds all the _pollution_ sources currently effecting you and gets your total _pollution_ level");
    }
}