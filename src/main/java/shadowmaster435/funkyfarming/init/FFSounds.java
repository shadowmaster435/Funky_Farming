package shadowmaster435.funkyfarming.init;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FFSounds {
    public static final Identifier VORACE_PICK = new Identifier("funkyfarming:voracepick");
    public static SoundEvent VORACE_PICK_EVENT = new SoundEvent(VORACE_PICK);
    public static final Identifier EXOTIC_SOIL_DIG = new Identifier("funkyfarming:exotic_soil_dig");
    public static SoundEvent EXOTIC_SOIL_DIG_EVENT = new SoundEvent(EXOTIC_SOIL_DIG);
    public static final Identifier EXOTIC_SOIL_PLACE = new Identifier("funkyfarming:exotic_soil_place");
    public static SoundEvent EXOTIC_SOIL_PLACE_EVENT = new SoundEvent(EXOTIC_SOIL_PLACE);

    public static void registerSounds() {
        Registry.register(Registry.SOUND_EVENT, VORACE_PICK, VORACE_PICK_EVENT);
        Registry.register(Registry.SOUND_EVENT, EXOTIC_SOIL_DIG, EXOTIC_SOIL_DIG_EVENT);
        Registry.register(Registry.SOUND_EVENT, EXOTIC_SOIL_PLACE, EXOTIC_SOIL_PLACE_EVENT);
    }
}
