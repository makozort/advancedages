package net.makozort.advancedages.reg;


import net.makozort.advancedages.AdvancedAges;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AllSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, AdvancedAges.MOD_ID);


    public static final RegistryObject<SoundEvent> TITAN_HORN =
            registerSoundEvent("titan_horn");

    public static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        ResourceLocation id = new ResourceLocation(AdvancedAges.MOD_ID, name);
        return SOUND_EVENTS.register(name,() -> new SoundEvent(new ResourceLocation(AdvancedAges.MOD_ID,name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }

}
