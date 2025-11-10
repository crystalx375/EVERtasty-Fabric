package evertasty.enchant;

import evertasty.sound.ModSound;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.*;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.entity.vehicle.MinecartEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.List;
import java.util.function.Predicate;

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
        if (deadEntity.getHealth() > 0) return;
        if (!(user.getWorld() instanceof ServerWorld world)) return;

        Vec3d pos = deadEntity.getPos();
        float radius = level * 2f;
        Box box = new Box(pos.add(-radius, -radius, -radius), pos.add(radius, radius, radius));

        // исключения энтити
        Predicate<Entity> validTargets = entity ->
                !(entity instanceof ExperienceOrbEntity
                        || entity instanceof ItemEntity
                        || entity instanceof ProjectileEntity
                        || entity instanceof AreaEffectCloudEntity
                        || entity instanceof ArmorStandEntity
                        || entity instanceof FireworkRocketEntity
                        || entity instanceof MinecartEntity
                        || entity instanceof TntEntity
                        || entity instanceof ItemFrameEntity
                        || entity instanceof FallingBlockEntity
                        || entity instanceof TridentEntity
                        || entity instanceof LightningEntity
                        || entity instanceof EndCrystalEntity
                        || entity == deadEntity
                        || entity == user);

        List<Entity> nearbyEntities = world.getOtherEntities(user, box, validTargets);

        for (Entity e : nearbyEntities) {
            if (e instanceof LivingEntity living && living.isAlive() && !living.isInvulnerable()) {
                living.damage(world.getDamageSources().outOfWorld(), 6);
                living.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 20, 0, false, false));
            }
        }

        // эффекты частиц и звуков
        world.spawnParticles(
                ParticleTypes.SOUL, pos.x, pos.y + 1, pos.z,
                35 * level, 0.4 + 0.25 * level, 0.4 + 0.25 * level, 0.4 + 0.25 * level, 0.1f
        );

        user.getWorld().playSound(null, user.getX(), user.getY(), user.getZ(),
                ModSound.WHISPER, user.getSoundCategory(), 0.05f, 1f
        );
    }
}