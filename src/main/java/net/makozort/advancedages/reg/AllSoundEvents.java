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

    public static final RegistryObject<SoundEvent> GJALLAR_HORN =
            registerSoundEvent("gjallar_horn");
    public static final RegistryObject<SoundEvent> GRAND_HORN =
            registerSoundEvent("grand_horn");

    public static final RegistryObject<SoundEvent> OMINOUS_HORN =
            registerSoundEvent("ominous_horn");
    public static final RegistryObject<SoundEvent> REAPER_HORN =
            registerSoundEvent("reaper_horn");

    public static final RegistryObject<SoundEvent> HELL_BOMB =
            registerSoundEvent("hellbomb");

    public static final RegistryObject<SoundEvent> FLAME_AIR =
            registerSoundEvent("flame_air");

    public static final RegistryObject<SoundEvent> FLAME_EX =
            registerSoundEvent("flame_ex");

    public static final RegistryObject<SoundEvent> FLAME_LOOP =
            registerSoundEvent("flame_loop");



    public static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent((new ResourceLocation(AdvancedAges.MOD_ID, name))));

    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }

}
