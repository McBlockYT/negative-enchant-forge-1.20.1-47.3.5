package net.Babychaosfloh.negativeentchant.item;

import net.Babychaosfloh.negativeentchant.item.enchantment.*;
import net.Babychaosfloh.negativeentchant.NegativeEntchant;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.DamageEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(Registries.ENCHANTMENT, NegativeEntchant.MOD_ID);

    public static final RegistryObject<Enchantment> NEGATIVE_KNOCKBACK =
            ENCHANTMENTS.register("negative_knockback", () -> new NegativeKnockbackEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> NEGATIVE_MULTISHOT =
            ENCHANTMENTS.register("negative_multishot", () -> new NegativeMultishotEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> NEGATIVE_INFINITY =
            ENCHANTMENTS.register("negative_infinity", () -> new NegativeInfinityEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> NEGATIVE_SHARPNESS =
            ENCHANTMENTS.register("negative_sharpness", () -> new NegativeDamageEnchantment(Enchantment.Rarity.COMMON,0, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment>  NEGATIVE_SMITE = ENCHANTMENTS.register("negative_smite", () -> new  NegativeDamageEnchantment(Enchantment.Rarity.UNCOMMON, 1, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment>  NEGATIVE_BANE_OF_ARTHROPODS = ENCHANTMENTS.register("negative_bane_of_arthropods", () -> new  NegativeDamageEnchantment(Enchantment.Rarity.UNCOMMON, 2, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment>  NEGATIVE_SWIFT_SNEAK = ENCHANTMENTS.register("negative_swift_sneak", () -> new NegativeSwitftSneakEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.LEGS));
    public static final RegistryObject<Enchantment>  NEGATIVE_SOUL_SPEED = ENCHANTMENTS.register("negative_soul_speed", () -> new NegativeSoulSpeedEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.FEET));
    public static final RegistryObject<Enchantment>  NEGATIVE_VANISHING_CURSE = ENCHANTMENTS.register("negative_vanishing_curse", () -> new NegativeVanishingCurseEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.values()));
}
