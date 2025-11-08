package evertasty.event;

import evertasty.enchant.CurseSalt;
import evertasty.enchant.FatiqueCurse;
import evertasty.enchant.SvetYspexa;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
// Проверка при тиках
public class TickHandler {
    private static final Map<UUID, ItemStack> lastHeldItems = new HashMap<>();

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                ItemStack current = player.getMainHandStack();
                ItemStack previous = lastHeldItems.getOrDefault(player.getUuid(), ItemStack.EMPTY);

                if (!ItemStack.areEqual(previous, current)) {
                    lastHeldItems.put(player.getUuid(), current.copy());

                    if (current.isOf(Items.TRIDENT)) {
                        SvetYspexa.checkTridentEnchant(player, current);
                    } else if (previous.isOf(Items.TRIDENT)) {
                        SvetYspexa.updateWeatherEffects(player, current);
                    }
                }
                // 10 тиков
                if (server.getTicks() % 10 == 0) {
                    if (current.isOf(Items.TRIDENT)) {
                        SvetYspexa.updateWeatherEffects(player, current);
                    }
                }
                // 20 тиков (на проклятия)
                if (server.getTicks() % 20 == 0) {
                    CurseSalt.updateEffects(player);
                    FatiqueCurse.updateEffects(player);
                }
            }
        });
    }
}