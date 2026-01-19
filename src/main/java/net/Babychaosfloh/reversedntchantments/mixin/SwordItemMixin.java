package net.Babychaosfloh.reversedntchantments.mixin;

import net.Babychaosfloh.reversedntchantments.config.ReversedEnchantmentsServerConfigs;
import net.Babychaosfloh.reversedntchantments.item.ModEnchantments;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;

@Mixin(SwordItem.class)
public class SwordItemMixin {

    @Unique
    private static boolean REVERSED_SHARPNESS;
    private static boolean REVERSED_SMITE;
    private static boolean REVERSED_BANE_OF_ARTHROPODS;

    /**
     * @author Babychaosfloh_HD
     * @reason Adding negative enchantments
     */
    @Overwrite
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        REVERSED_SHARPNESS = ReversedEnchantmentsServerConfigs.ENCHANTMENTS.get("reversed_sharpness").get();
        REVERSED_SMITE = ReversedEnchantmentsServerConfigs.ENCHANTMENTS.get("reversed_smite").get();
        REVERSED_BANE_OF_ARTHROPODS = ReversedEnchantmentsServerConfigs.ENCHANTMENTS.get("reversed_bane_of_arthropods").get();

        int reversedSharpnessLvl = REVERSED_SHARPNESS ? EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.REVERSED_SHARPNESS.get(), pStack) : 0;
        int reversedSmiteLvl = REVERSED_SMITE ? EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.REVERSED_SMITE.get(), pStack) : 0;
        int reversedBaneOfAthropodsLvl = REVERSED_BANE_OF_ARTHROPODS ? EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.REVERSED_BANE_OF_ARTHROPODS.get(), pStack) : 0;
        if (reversedSharpnessLvl > 0) {
            float healCount = reversedSharpnessLvl + (float) pAttacker.getAttributeValue(Attributes.ATTACK_DAMAGE);
            pTarget.heal(healCount);
        }
        pAttacker.sendSystemMessage(Component.literal(""+pAttacker.getAttributeValue(Attributes.ATTACK_DAMAGE)));
        pStack.hurtAndBreak(1, pAttacker, (target) -> {
            target.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        });
        return true;
    }
}
