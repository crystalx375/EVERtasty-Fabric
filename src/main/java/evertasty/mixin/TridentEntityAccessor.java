package evertasty.mixin;

import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.projectile.TridentEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
// Написал проджектайлы ИИ с моими правками так что точно сказать не могу что делает,
// Я бы сделал без ИИ конечно бы если было желание изучить все это говно, но я не сделал так
@Mixin(TridentEntity.class)
public interface TridentEntityAccessor {
    @Accessor("LOYALTY")
    static TrackedData<Byte> getLoyaltyTrackedData() {
        throw new UnsupportedOperationException();
    }
}