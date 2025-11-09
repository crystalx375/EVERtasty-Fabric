package evertasty.sound;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registry;
import net.minecraft.registry.Registries;

// Регистрация звуков
public class ModSound {
    public static SoundEvent HIT1;
    public static SoundEvent HIT2;
    public static SoundEvent WHISPER;
    public static SoundEvent[] HIT_SOUNDS;

    public static void registerSounds() {
        HIT1 = register("hit1");
        HIT2 = register("hit2");

        HIT_SOUNDS = new SoundEvent[]{HIT1, HIT2};

        WHISPER = register("whisper");
    }

    private static SoundEvent register(String name) {
        Identifier id = new Identifier("evertasty", name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }
}