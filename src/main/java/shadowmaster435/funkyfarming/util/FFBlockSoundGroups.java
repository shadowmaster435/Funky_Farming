package shadowmaster435.funkyfarming.util;

import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import shadowmaster435.funkyfarming.init.FFSounds;

public class FFBlockSoundGroups {

    public final float volume;
    public final float pitch;
    private final SoundEvent breakSound;
    private final SoundEvent stepSound;
    private final SoundEvent placeSound;
    private final SoundEvent hitSound;
    private final SoundEvent fallSound;

    public float getVolume() {
        return this.volume;
    }

    public float getPitch() {
        return this.pitch;
    }

    public SoundEvent getBreakSound() {
        return this.breakSound;
    }

    public SoundEvent getStepSound() {
        return this.stepSound;
    }

    public SoundEvent getPlaceSound() {
        return this.placeSound;
    }

    public SoundEvent getHitSound() {
        return this.hitSound;
    }

    public SoundEvent getFallSound() {
        return this.fallSound;
    }

    public static final BlockSoundGroup EXOTIC_SOIL;
    public static final BlockSoundGroup PEARLSTONE;

    public static final BlockSoundGroup METAL;

    public static final BlockSoundGroup SLAGATE;



    public FFBlockSoundGroups(float volume, float pitch, SoundEvent breakSound, SoundEvent stepSound, SoundEvent placeSound, SoundEvent hitSound, SoundEvent fallSound) {
        this.volume = volume;
        this.pitch = pitch;
        this.breakSound = breakSound;
        this.stepSound = stepSound;
        this.placeSound = placeSound;
        this.hitSound = hitSound;
        this.fallSound = fallSound;
    }
    static {
        EXOTIC_SOIL = new BlockSoundGroup(1.0F, 1.0F, FFSounds.EXOTIC_SOIL_PLACE_EVENT, FFSounds.EXOTIC_SOIL_DIG_EVENT, FFSounds.EXOTIC_SOIL_PLACE_EVENT, FFSounds.EXOTIC_SOIL_DIG_EVENT, FFSounds.EXOTIC_SOIL_PLACE_EVENT);
        PEARLSTONE = new BlockSoundGroup(1.0F, 1.0F, FFSounds.PEARLSTONE_PLACE_EVENT, FFSounds.PEARLSTONE_DIG_EVENT, FFSounds.PEARLSTONE_PLACE_EVENT, FFSounds.PEARLSTONE_DIG_EVENT, FFSounds.PEARLSTONE_PLACE_EVENT);
        METAL = new BlockSoundGroup(1.0F, 1.0F, FFSounds.METAL_PLACE_EVENT, FFSounds.METAL_DIG_EVENT, FFSounds.METAL_PLACE_EVENT, FFSounds.METAL_DIG_EVENT, FFSounds.METAL_PLACE_EVENT);
        SLAGATE = new BlockSoundGroup(1.0F, 1.0F, FFSounds.SLAGATE_PLACE_EVENT, FFSounds.SLAGATE_DIG_EVENT, FFSounds.SLAGATE_PLACE_EVENT, FFSounds.SLAGATE_DIG_EVENT, FFSounds.SLAGATE_PLACE_EVENT);

    }
}
