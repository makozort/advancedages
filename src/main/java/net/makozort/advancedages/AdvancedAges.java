package net.makozort.advancedages;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipHelper;
import com.simibubi.create.foundation.item.TooltipModifier;
import net.makozort.advancedages.reg.ModRegistrate;
import net.makozort.advancedages.networking.ModPackets;
import net.makozort.advancedages.reg.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(net.makozort.advancedages.AdvancedAges.MOD_ID)
public class AdvancedAges {

    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "advancedages";
    // Directly reference a slf4j logger
    public static final String NAME = "Advanced Ages";
    public static final Logger LOGGER = LoggerFactory.getLogger(NAME);

    public static final ModRegistrate REGISTRATE = ModRegistrate.create(MOD_ID);

    static {
        REGISTRATE.setTooltipModifierFactory(item -> new ItemDescription.Modifier(item, TooltipHelper.Palette.STANDARD_CREATE)
                .andThen(TooltipModifier.mapNull(KineticStats.create(item))));
    }

    public AdvancedAges() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        REGISTRATE.registerEventListeners(modEventBus);
        AllBlocks.register();
        AllBlockEntities.register();
        AllCreativeModeTabs.register(modEventBus);
        Allitems.register();
        AllFluids.register();
        AllEffects.register(modEventBus);
        AllSoundEvents.register(modEventBus);
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener((this::register));
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    public static ResourceLocation asFluid(String fluid, boolean flowing) {
        return new ResourceLocation(MOD_ID, "fluid/" + fluid + (flowing ? "_flow" : "_still"));
    }

    private void register(RegisterEvent event) {
        event.register(Registries.FEATURE, new ResourceLocation(MOD_ID, "very_large_lake"), () -> AllFeatures.VERY_LARGE_LAKE);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ModPackets.register();
        });
    }


}
