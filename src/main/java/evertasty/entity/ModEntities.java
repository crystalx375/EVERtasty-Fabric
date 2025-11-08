package evertasty.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
// Написал проджектайлы ИИ с моими правками так что точно сказать не могу что делает,
// Я бы сделал без ИИ конечно бы если было желание изучить все это говно, но я не сделал так
public class ModEntities {
    public static EntityType<CustomTridentEntity> CUSTOM_TRIDENT;

    public static void registerEntities() {
        CUSTOM_TRIDENT = Registry.register(
                Registries.ENTITY_TYPE,
                new Identifier("evertasty", "custom_trident"),
                EntityType.Builder.<CustomTridentEntity>create(CustomTridentEntity::new, SpawnGroup.MISC)
                        .setDimensions(0.5F, 0.5F)
                        .trackingTickInterval(2)
                        .build("custom_trident")
        );
    }
}