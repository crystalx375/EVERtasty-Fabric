package evertasty.enchant;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
// Проклятие на голод
public class FatiqueCurse extends Enchantment {
    public FatiqueCurse() {
        super(Rarity.VERY_RARE, EnchantmentTarget.TRIDENT, new EquipmentSlot[]{EquipmentSlot.MAINHAND});

    }

    @Override
    public boolean isCursed() {
        return true;
    }
    @Override
    public boolean isTreasure() {
        return true;
    }
    public static void checkTridentEnchant(PlayerEntity player, ItemStack current) {
        boolean safe = player.isTouchingWater() || player.getWorld().hasRain(player.getBlockPos());

        ItemStack stack = player.getMainHandStack();
        if (EnchantmentHelper.getLevel(AllEnchants.FatiqueCurse, stack) > 0) {
            if (!safe) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 30, 49, true, true));
            }
            if (player.hasStatusEffect(StatusEffects.LEVITATION)) {
                player.removeStatusEffect(StatusEffects.LEVITATION);
            }
        }
    }
    public static void updateEffects(PlayerEntity player) {
        boolean safe = player.isTouchingWater() || player.getWorld().hasRain(player.getBlockPos());
        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack stack = player.getInventory().getStack(i);

            if (EnchantmentHelper.getLevel(AllEnchants.FatiqueCurse, stack) > 0) {
                if (!safe) {
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 30, 49, true, true));
                }
                if (player.hasStatusEffect(StatusEffects.LEVITATION)) {
                    player.removeStatusEffect(StatusEffects.LEVITATION);
                }
            }
        }
    }
}
