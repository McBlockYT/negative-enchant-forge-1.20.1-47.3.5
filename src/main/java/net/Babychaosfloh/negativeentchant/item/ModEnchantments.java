package net.Babychaosfloh.negativeentchant.item;

import net.Babychaosfloh.negativeentchant.item.enchantment.NegativeKnockbackEnchantment;
import net.Babychaosfloh.negativeentchant.NegativeEntchant;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(Registries.ENCHANTMENT, NegativeEntchant.MOD_ID);

    public static final RegistryObject<Enchantment> NEGATIVE_KNOCKBACK =
            ENCHANTMENTS.register("negative_knockback", () -> new NegativeKnockbackEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlot.MAINHAND));
}
