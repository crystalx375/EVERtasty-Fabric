package evertasty.effects;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
// Регистрация эффекта
public class AllEffects {
    public static final StatusEffect STUN = Registry.register(
            Registries.STATUS_EFFECT,
            new Identifier("evertasty", "stun"),
            new STUN()
    );

    public static void registerEffects() {
        System.out.println("Registering custom effects for evertasty");
    }
}