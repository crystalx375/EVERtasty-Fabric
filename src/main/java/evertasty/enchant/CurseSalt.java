package evertasty.enchant;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
// Проклятие на разрушение
public class CurseSalt extends Enchantment {
    public CurseSalt() {
        super(Rarity.VERY_RARE, EnchantmentTarget.TRIDENT, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean isCursed() {
        return true;
    }

    @Override
    public boolean isTreasure() {
        return true;
    }

    public static void updateEffects(PlayerEntity player) {
        boolean safe = player.isTouchingWater() || player.getWorld().hasRain(player.getBlockPos());
        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack stack = player.getInventory().getStack(i);

            if (EnchantmentHelper.getLevel(AllEnchants.CurseSalt, stack) > 0) {
                if (!safe) {
                    int damage = stack.getDamage() + 5;

                    if (damage >= stack.getMaxDamage()) {
                        player.getInventory().setStack(i, ItemStack.EMPTY);
                        player.playSound(SoundEvents.ENTITY_ITEM_BREAK, 1.0f, 1.0f);
                    } else {
                        stack.setDamage(damage);
                    }
                }
                if (player.hasStatusEffect(StatusEffects.GLOWING)) {
                    player.removeStatusEffect(StatusEffects.GLOWING);
                }
            }
        }
    }
}