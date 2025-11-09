package evertasty.enchant;

import evertasty.effects.AllEffects;
import evertasty.sound.ModSound;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.random.Random;
// зачара на "стан"
public class VTormoza extends Enchantment {
    public VTormoza() {
        super(Rarity.VERY_RARE, EnchantmentTarget.TRIDENT, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMaxLevel() { return 1; }

    public static void applyEffect(LivingEntity user, LivingEntity target, ItemStack stack) {
    if (user.isTouchingWater() || user.getWorld().hasRain(user.getBlockPos())) {
            if (user.getWorld().isClient()) return;
            if (!(user.getWorld() instanceof ServerWorld serverWorld)) return;
            if (!(stack.getItem() instanceof TridentItem)) return;

            if (!target.hasStatusEffect(StatusEffects.SLOWNESS)) {
                Random rand = target.getRandom();
                int idx = rand.nextInt(ModSound.HIT_SOUNDS.length);
                target.playSound(ModSound.HIT_SOUNDS[idx], 2f, 0.8f);

                serverWorld.spawnParticles(
                        ParticleTypes.WHITE_ASH,
                        target.getX(),
                        target.getY() + target.getHeight() / 1.5,
                        target.getZ(),
                        80, 1, 0.8, 1, 0.1
                );
                target.addStatusEffect(new StatusEffectInstance(AllEffects.STUN, 15, 0, false, false));
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 10, 0));
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 50, 4));
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 100, 0));
            }
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 400, 1));
        }
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if (!(target instanceof LivingEntity living)) return;

        ItemStack stack = user.getMainHandStack();
        applyEffect(user, living, stack);

        super.onTargetDamaged(user, target, level);
    }
}
