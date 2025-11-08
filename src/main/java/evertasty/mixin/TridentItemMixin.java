package evertasty.mixin;

import evertasty.entity.CustomTridentEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
// Написал проджектайлы ИИ с моими правками так что точно сказать не могу что делает,
// Я бы сделал без ИИ конечно бы если было желание изучить все это говно, но я не сделал так
@Mixin(TridentItem.class)
public class TridentItemMixin {
    @Inject(method = "onStoppedUsing", at = @At("HEAD"), cancellable = true)
    private void injectCustomThrow(ItemStack stack, World world, LivingEntity user, int remainingUseTicks, CallbackInfo ci) {
        if (world.isClient || !(user instanceof PlayerEntity player)) return;

        int riptide = EnchantmentHelper.getLevel(Enchantments.RIPTIDE, stack);

        boolean inWaterOrRain = player.isTouchingWater() || player.getWorld().hasRain(player.getBlockPos());
        if (riptide > 0 && !inWaterOrRain) {
            return;
        }

        if (riptide > 0) {
            return;
        }

        CustomTridentEntity trident = new CustomTridentEntity(world, player, stack.copy());
        trident.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 2.5F, 1.0F);

        if (player.getAbilities().creativeMode) {
            trident.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
        }

        world.spawnEntity(trident);
        world.playSound(null, trident.getX(), trident.getY(), trident.getZ(),
                SoundEvents.ITEM_TRIDENT_THROW, SoundCategory.PLAYERS, 1.0F, 1.0F);
        player.getItemCooldownManager().set(stack.getItem(), 15);
        if (!player.getAbilities().creativeMode) {
            stack.decrement(1);
        }

        ci.cancel();
    }
}