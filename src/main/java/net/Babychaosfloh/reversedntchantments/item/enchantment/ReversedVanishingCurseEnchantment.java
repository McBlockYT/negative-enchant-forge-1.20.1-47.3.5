package net.Babychaosfloh.reversedntchantments.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class ReversedVanishingCurseEnchantment extends Enchantment {
    public ReversedVanishingCurseEnchantment(Rarity pRarity, EquipmentSlot[] pApplicableSlots) {
        super(pRarity, EnchantmentCategory.VANISHABLE, pApplicableSlots);
    }

    /**
     * Returns the minimal value of enchantability needed on the enchantment level passed.
     */
    public int getMinCost(int pEnchantmentLevel) {
        return 25;
    }

    public int getMaxCost(int pEnchantmentLevel) {
        return 50;
    }

    /**
     * Checks if the enchantment should be considered a treasure enchantment. These enchantments can not be obtained
     * using the enchantment table. The mending enchantment is an example of a treasure enchantment.
     * @return Whether the enchantment is a treasure enchantment.
     */
    public boolean isTreasureOnly() {
        return true;
    }

    /**
     * Checks if the enchantment is considered a curse. These enchantments are treated as debuffs and can not be removed
     * from items under normal circumstances.
     * @return Whether the enchantment is a curse.
     */
    public boolean isCurse() {
        return true;
    }
}
