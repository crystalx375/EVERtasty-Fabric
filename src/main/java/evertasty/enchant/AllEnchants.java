package evertasty.enchant;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
// регистрация зачар
public class AllEnchants {
    public static final Enchantment SvetYspexa = Registry.register(Registries.ENCHANTMENT, new Identifier("evertasty", "svet_yspexa"), new SvetYspexa());
    public static final Enchantment Wither = Registry.register(Registries.ENCHANTMENT, new Identifier("evertasty", "wither"), new Wither());
    public static final Enchantment VTormoza = Registry.register(Registries.ENCHANTMENT, new Identifier("evertasty", "vtormoza"), new VTormoza());
    public static final Enchantment ForsakenFlame = Registry.register(Registries.ENCHANTMENT, new Identifier("evertasty", "forsaken_flame"), new ForsakenFlame());
    public static final Enchantment EchoOfVoid = Registry.register(Registries.ENCHANTMENT, new Identifier("evertasty", "echo_of_void"), new EchoOfVoid());
    public static final Enchantment CurseSalt = Registry.register(Registries.ENCHANTMENT, new Identifier("evertasty", "curse_salt"), new CurseSalt());
    public static final Enchantment FatiqueCurse = Registry.register(Registries.ENCHANTMENT, new Identifier("evertasty", "fatique_curse"), new FatiqueCurse());
    public static final Enchantment MarkOfLight = Registry.register(Registries.ENCHANTMENT, new Identifier("evertasty", "mark_of_light"), new MarkOfLight());

    public static void registerEnchantments() {
        System.out.println("Registering enchantments");
    }
}
