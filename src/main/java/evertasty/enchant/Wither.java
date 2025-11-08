package evertasty.enchant;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
// зачара на иссушение
public class Wither extends Enchantment {
    public Wither() {
        super(Rarity.RARE, EnchantmentTarget.TRIDENT, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }
    @Override
    public int getMaxLevel() { return 3; }
    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if (user instanceof PlayerEntity player && target instanceof LivingEntity living) {
            // проверка при взятии
            if (player.getMainHandStack().getItem() == Items.TRIDENT) {
                if (level == 1) {
                    living.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 120, 1));
                } else {
                    living.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 10 + (34 * level), level));
                }
            }
        }
        super.onTargetDamaged(user, target, level);
    }
}
