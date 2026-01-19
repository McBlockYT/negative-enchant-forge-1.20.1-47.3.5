package net.Babychaosfloh.reversedntchantments.item;

import net.Babychaosfloh.reversedntchantments.ReversedEnchantmentsMain;
import net.Babychaosfloh.reversedntchantments.item.enchantment.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(Registries.ENCHANTMENT, ReversedEnchantmentsMain.MOD_ID);

    public static final RegistryObject<Enchantment> REVERSED_KNOCKBACK = ENCHANTMENTS.register("reversed_knockback", () -> new ReversedKnockbackEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> REVERSED_MULTISHOT = ENCHANTMENTS.register("reversed_multishot", () -> new ReversedMultishotEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> REVERSED_INFINITY = ENCHANTMENTS.register("reversed_infinity", () -> new ReversedInfinityEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> REVERSED_SHARPNESS = ENCHANTMENTS.register("reversed_sharpness", () -> new ReversedDamageEnchantment(Enchantment.Rarity.COMMON,0, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> REVERSED_SMITE = ENCHANTMENTS.register("reversed_smite", () -> new ReversedDamageEnchantment(Enchantment.Rarity.UNCOMMON, 1, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> REVERSED_BANE_OF_ARTHROPODS = ENCHANTMENTS.register("reversed_bane_of_arthropods", () -> new ReversedDamageEnchantment(Enchantment.Rarity.UNCOMMON, 2, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> REVERSED_SWIFT_SNEAK = ENCHANTMENTS.register("reversed_swift_sneak", () -> new ReversedSwitftSneakEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.LEGS));
    public static final RegistryObject<Enchantment> REVERSED_SOUL_SPEED = ENCHANTMENTS.register("reversed_soul_speed", () -> new ReversedSoulSpeedEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.FEET));
    public static final RegistryObject<Enchantment> REVERSED_VANISHING_CURSE = ENCHANTMENTS.register("reversed_vanishing_curse", () -> new ReversedVanishingCurseEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.values()));
}
