package evertasty.enchant;

import evertasty.sound.ModSound;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
// Это имба на сплеш урон при смерти
public class EchoOfVoid extends Enchantment {
    public EchoOfVoid() {
        super(Rarity.VERY_RARE, EnchantmentTarget.TRIDENT, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if (!(target instanceof LivingEntity living) || target.getWorld().isClient) return;

        if (living.getHealth() <= 0) {
            applyEffect(user, living, level);
        }

        super.onTargetDamaged(user, target, level);
    }
    // урон бездны, радиуса и спавна партиклов, звуков
    public static void applyEffect(LivingEntity user, LivingEntity deadEntity, int level) {
        if (deadEntity.getHealth() <= 0) {
            if (!(user.getWorld() instanceof ServerWorld world)) return;

            Vec3d pos = deadEntity.getPos();
            float radius = level * 2;
            Box box = new Box(pos.add(-radius, -radius, -radius), pos.add(radius, radius, radius));

            for (LivingEntity entity : world.getEntitiesByClass(LivingEntity.class, box, entit ->
                    entit != user && entit != deadEntity && entit.isAlive() && !entit.isInvulnerable()
            )) {
                entity.damage(world.getDamageSources().outOfWorld(), 1.5f + 1.25f * level);
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 20, 0, false, false));
            }

            world.spawnParticles(ParticleTypes.SOUL, pos.x, pos.y + 1, pos.z,
                    35 * level, 0.4 + 0.25 * level, 0.4 + 0.25 * level, 0.4 + 0.25 * level, 0.15f
            );
            user.getWorld().playSound(null, user.getX(), user.getY(), user.getZ(),
                    ModSound.WHISPER, user.getSoundCategory(), 0.05f, 1f
            );
        }
    }
}