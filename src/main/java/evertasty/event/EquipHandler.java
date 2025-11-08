package evertasty.event;

import evertasty.enchant.AllEnchants;
import evertasty.enchant.FatiqueCurse;
import evertasty.enchant.ForsakenFlame;
import evertasty.enchant.SvetYspexa;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.enchantment.EnchantmentHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
// Проверка при взятии
public class EquipHandler {
    public static final Map<UUID, ItemStack> lastMainHandItems = new HashMap<>();

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                ItemStack current = player.getMainHandStack();
                UUID playerId = player.getUuid();
                ItemStack last = lastMainHandItems.getOrDefault(playerId, ItemStack.EMPTY);
                if (!ItemStack.areEqual(current, last)) {
                    // чистка от говна(прошлых эффектов)
                    if (last.isOf(Items.TRIDENT)) {
                        if (EnchantmentHelper.getLevel(AllEnchants.SvetYspexa, last) > 0) {
                            player.removeStatusEffect(StatusEffects.DOLPHINS_GRACE);
                            player.removeStatusEffect(StatusEffects.HASTE);
                        }
                        if (EnchantmentHelper.getLevel(AllEnchants.ForsakenFlame, last) > 0) {
                            player.removeStatusEffect(StatusEffects.FIRE_RESISTANCE);
                        }
                    }
                    // проверка зачар
                    if (current.isOf(Items.TRIDENT)) {
                        SvetYspexa.checkTridentEnchant(player, current);
                        ForsakenFlame.checkTridentEnchant(player, current);
                        FatiqueCurse.checkTridentEnchant(player, current);
                    }
                    lastMainHandItems.put(playerId, current.copy());
                }
            }
        });
    }
}