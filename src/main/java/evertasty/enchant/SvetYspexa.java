package evertasty.enchant;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
// зачара на эффекты спешки и грации
public class SvetYspexa extends Enchantment {
    public SvetYspexa() {
            super(Rarity.VERY_RARE, EnchantmentTarget.TRIDENT, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }
    @Override
    public int getMaxLevel() { return 1; }
    // проверка при взятии
    public static void checkTridentEnchant(PlayerEntity player, ItemStack current) {
        ItemStack stack = player.getMainHandStack();
        if (current.isOf(Items.TRIDENT) && EnchantmentHelper.getLevel(AllEnchants.SvetYspexa, stack) > 0) {
            if (player.isTouchingWater() || player.getWorld().hasRain(player.getBlockPos())) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE, -1, 0, false, true));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, -1, 3, false, true));
            }
        }
    }
    // каждые 10 тиков проверка
    public static void updateWeatherEffects(PlayerEntity player, ItemStack current) {
        // проверка есть ли чара
        if (current.isOf(Items.TRIDENT) && EnchantmentHelper.getLevel(AllEnchants.SvetYspexa, current) > 0) {
            // есть ли вода
            if (player.isTouchingWater() || player.getWorld().hasRain(player.getBlockPos())) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, -1, 3, false, true));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE, -1, 0, false, true));
            } else {
                player.removeStatusEffect(StatusEffects.DOLPHINS_GRACE);
                player.removeStatusEffect(StatusEffects.HASTE);
            }
        }
    }
}
