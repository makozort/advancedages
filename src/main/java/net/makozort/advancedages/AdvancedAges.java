package net.makozort.advancedages;
// YT Tutorial Playlist:
// https://www.youtube.com/playlist?list=PLKGarocXCE1HrC60yuTNTGRoZc6hf5Uvl

import com.simibubi.create.foundation.data.CreateRegistrate;
import net.makozort.advancedages.content.commands.ClearPollutionCommand;
import net.makozort.advancedages.content.fluid.ModFluidTypes;
import net.makozort.advancedages.effect.ModEffects;
import net.makozort.advancedages.reg.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(net.makozort.advancedages.AdvancedAges.MOD_ID)
public class AdvancedAges {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "advancedages";
    // Directly reference a slf4j logger
    public static final String NAME = "Advanced Ages";
    public static final Logger LOGGER = LoggerFactory.getLogger(NAME);

    public static final CreateRegistrate REGISTRATE = ModRegistrate.REGISTRATE;

    public AdvancedAges() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();






        REGISTRATE.registerEventListeners(modEventBus);
        AllBlocks.register();
        //AllBlockEntitys.register();
        Allitems.register();
        Allfluids.register(modEventBus);
        ModFluidTypes.register(modEventBus);
        ModCreativeModeTab.register();
        ModEffects.register(modEventBus);
        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
