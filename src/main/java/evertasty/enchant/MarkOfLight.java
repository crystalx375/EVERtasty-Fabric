package evertasty.enchant;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
// зачара на glow
public class MarkOfLight extends Enchantment {
    private static final Map<UUID, Long> cooldowns = new HashMap<>();
    public static final int COOLDOWN_TICKS = 1200;

    public MarkOfLight() {
        super(Rarity.RARE, EnchantmentTarget.TRIDENT, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }
    public static long getLastUsed(UUID uuid) {
        return cooldowns.getOrDefault(uuid, 0L);
    }

    public static void setLastUsed(UUID uuid, long time) {
        cooldowns.put(uuid, time);
    }
    @Override
    public int getMaxLevel() { return 1; }
    public static void applyEffect(LivingEntity user, LivingEntity target, ItemStack stack) {
        World world = user.getWorld();
        if (world.isClient) {
            for (int i = 0; i < 20; i++) {
                double dx = target.getX() + (world.random.nextDouble() - 0.5) * 1.2;
                double dy = target.getY() + target.getHeight() * 0.5 + (world.random.nextDouble() - 0.5) * 0.6;
                double dz = target.getZ() + (world.random.nextDouble() - 0.5) * 1.2;
                world.addParticle(ParticleTypes.WAX_OFF, dx, dy, dz, 0, 0.02, 0);
            }
            return;
        }
        long now = user.getWorld().getTime();
        long lastUsed = cooldowns.getOrDefault(user.getUuid(), 0L);

        if (now - lastUsed < COOLDOWN_TICKS) return;
        target.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 300, 0, true, true, true));

        if (user.getWorld() instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(ParticleTypes.WAX_OFF,
                    target.getX(), target.getY() + target.getHeight() / 2, target.getZ(),
                    20, 0.7, 0.7, 0.7, 0.2
            );
        }
        user.getWorld().playSound(
                null, target.getX(), target.getY(), target.getZ(),
                SoundEvents.BLOCK_RESPAWN_ANCHOR_CHARGE, SoundCategory.PLAYERS, 1f, 0.4f
        );
        cooldowns.put(user.getUuid(), now);
    }
    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if (!(target instanceof LivingEntity living)) return;
        applyEffect(user, living, user.getMainHandStack());
        super.onTargetDamaged(user, target, level);
    }
}