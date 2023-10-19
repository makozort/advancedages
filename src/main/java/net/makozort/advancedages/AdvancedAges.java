package net.makozort.advancedages;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipHelper;
import com.simibubi.create.foundation.item.TooltipModifier;

import net.makozort.advancedages.reg.AllFeatures;
import net.makozort.advancedages.content.fluid.ModFluidTypes;
import net.makozort.advancedages.content.effect.ModEffects;
import net.makozort.advancedages.reg.*;
import net.minecraft.core.Registry;
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

    public static final CreateRegistrate REGISTRATE = ModRegistrate.REGISTRATE;
    static {
        REGISTRATE.setTooltipModifierFactory(item -> {
            return new ItemDescription.Modifier(item, TooltipHelper.Palette.STANDARD_CREATE)
                    .andThen(TooltipModifier.mapNull(KineticStats.create(item)));
        });
    }

    public AdvancedAges() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        REGISTRATE.registerEventListeners(modEventBus);
        AllBlocks.register();
        AllBlockEntities.register();
        Allitems.register();
        Allfluids.register(modEventBus);
        ModFluidTypes.register(modEventBus);
        ModCreativeModeTab.register();
        ModEffects.register(modEventBus);
        AllSoundEvents.register(modEventBus);
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener((this::register));
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void register(RegisterEvent event) {
        event.register(Registry.FEATURE_REGISTRY,new ResourceLocation(MOD_ID,"very_large_lake"),() -> AllFeatures.VERY_LARGE_LAKE);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }
    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
