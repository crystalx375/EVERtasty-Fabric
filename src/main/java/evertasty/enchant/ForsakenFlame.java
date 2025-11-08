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
// зачара на эффект сопротивления огня
public class ForsakenFlame extends Enchantment {
    public ForsakenFlame() {
        super(Rarity.UNCOMMON, EnchantmentTarget.TRIDENT, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }
    @Override
    public int getMaxLevel() { return 1; }
    // проверка при взятии
    public static void checkTridentEnchant(PlayerEntity player, ItemStack current) {
        ItemStack stack = player.getMainHandStack();
        if (current.isOf(Items.TRIDENT) && EnchantmentHelper.getLevel(AllEnchants.ForsakenFlame, stack) > 0) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, -1, 1, false, true));
        }
    }
}
