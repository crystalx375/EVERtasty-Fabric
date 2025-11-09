package evertasty.entity;

import evertasty.enchant.EchoOfVoid;
import evertasty.enchant.MarkOfLight;
import evertasty.enchant.VTormoza;
import evertasty.enchant.Wither;
import evertasty.mixin.TridentEntityAccessor;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
// Написал проджектайлы ИИ с моими правками так что точно сказать не могу что делает,
// Я бы сделал без ИИ конечно бы если было желание изучить все это говно, но я не сделал так
public class CustomTridentEntity extends TridentEntity {
    public CustomTridentEntity(EntityType<? extends TridentEntity> type, World world) {
        super(type, world);
    }

    public CustomTridentEntity(World world, LivingEntity owner, ItemStack stack) {
        super(ModEntities.CUSTOM_TRIDENT, world);
        this.setOwner(owner);
        this.tridentStack = stack.copy();
        this.refreshPositionAndAngles(owner.getX(), owner.getEyeY() - 0.1, owner.getZ(), owner.getYaw(), owner.getPitch());
        int loyalty = EnchantmentHelper.getLoyalty(stack);
        if (loyalty > 0) {
            this.getDataTracker().set(TridentEntityAccessor.getLoyaltyTrackedData(), (byte) loyalty);
        }

    }


    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        if (!tridentStack.isEmpty()) {
            NbtCompound stackNbt = new NbtCompound();
            tridentStack.writeNbt(stackNbt);
            nbt.put("TridentStack", stackNbt);
        }
    }
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("TridentStack")) {
            tridentStack = ItemStack.fromNbt(nbt.getCompound("TridentStack"));
        }
    }


    @Override
    protected void onEntityHit(EntityHitResult hitResult) {
        super.onEntityHit(hitResult);
        if (getWorld().isClient) return;
        if (!(hitResult.getEntity() instanceof LivingEntity target)) return;

        ItemStack stack = getTridentStack();
        EnchantmentHelper.get(stack).forEach((enchantment, level) -> {
            if (enchantment instanceof MarkOfLight) {
                if (!(this.getOwner() instanceof LivingEntity owner)) return; // безопасно получить владельца
                long now = owner.getWorld().getTime();
                long lastUsed = MarkOfLight.getLastUsed(owner.getUuid());
                if (now - lastUsed >= MarkOfLight.COOLDOWN_TICKS) {
                    MarkOfLight.setLastUsed(owner.getUuid(), now);
                    target.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 20 * 15, 0, true, true, true));
                }
            }
            if (enchantment instanceof Wither) {
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 10 + (30 * level), level));
            }
            if (enchantment instanceof VTormoza) {
                VTormoza.applyEffect((LivingEntity) this.getOwner(), target, stack);
            }
            if (enchantment instanceof EchoOfVoid) {
                EchoOfVoid.applyEffect((LivingEntity) this.getOwner(), target, level);
            }
            if (enchantment instanceof MarkOfLight) {
                MarkOfLight.applyEffect((LivingEntity) this.getOwner(), target, stack);
            }
        });
    }
    @Override
    public void tick() {
        super.tick();
    }
    private ItemStack tridentStack = ItemStack.EMPTY;

    public ItemStack getTridentStack() {
        return tridentStack;
    }

    public ItemStack asItemStack() {
        return tridentStack.copy();
    }
}