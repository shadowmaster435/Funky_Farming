package shadowmaster435.funkyfarming.init;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

public class FFSounds {
    public static final Identifier VORACE_PICK = new Identifier("funkyfarming:voracepick");
    public static SoundEvent VORACE_PICK_EVENT = SoundEvent.of(VORACE_PICK);
    public static final Identifier EXOTIC_SOIL_DIG = new Identifier("funkyfarming:exotic_soil_dig");
    public static SoundEvent EXOTIC_SOIL_DIG_EVENT = SoundEvent.of(EXOTIC_SOIL_DIG);
    public static final Identifier EXOTIC_SOIL_PLACE = new Identifier("funkyfarming:exotic_soil_place");
    public static SoundEvent EXOTIC_SOIL_PLACE_EVENT = SoundEvent.of(EXOTIC_SOIL_PLACE);
    public static final Identifier PEARLSTONE_DIG = new Identifier("funkyfarming:pearlstone_dig");
    public static SoundEvent PEARLSTONE_DIG_EVENT = SoundEvent.of(PEARLSTONE_DIG);
    public static final Identifier PEARLSTONE_PLACE = new Identifier("funkyfarming:pearlstone_place");
    public static SoundEvent PEARLSTONE_PLACE_EVENT = SoundEvent.of(PEARLSTONE_PLACE);

    public static void registerSounds() {
        Registry.register(Registries.SOUND_EVENT, VORACE_PICK, VORACE_PICK_EVENT);
        Registry.register(Registries.SOUND_EVENT, EXOTIC_SOIL_DIG, EXOTIC_SOIL_DIG_EVENT);
        Registry.register(Registries.SOUND_EVENT, EXOTIC_SOIL_PLACE, EXOTIC_SOIL_PLACE_EVENT);
        Registry.register(Registries.SOUND_EVENT, PEARLSTONE_DIG, PEARLSTONE_DIG_EVENT);
        Registry.register(Registries.SOUND_EVENT, PEARLSTONE_PLACE, PEARLSTONE_PLACE_EVENT);
    }
}
