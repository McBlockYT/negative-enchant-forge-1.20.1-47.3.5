package net.Babychaosfloh.negativeentchant.mixin;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.Babychaosfloh.negativeentchant.item.ModEnchantments;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;

@Mixin(SwordItem.class)
public class SwordItemMixin {

    /**
     * @author Babychaosfloh_HD
     * @reason Adding negative enchantments
     */
    @Overwrite
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        int negativeSharpnessLvl = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.NEGATIVE_SHARPNESS.get(), pStack);
        int negativeSmiteLvl = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.NEGATIVE_SMITE.get(), pStack);
        int negativeBaneOfAthropodsLvl = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.NEGATIVE_BANE_OF_ARTHROPODS.get(), pStack);
        if (negativeSharpnessLvl > 0) {
            float healCount = negativeSharpnessLvl + (float) pAttacker.getAttributeValue(Attributes.ATTACK_DAMAGE);
            pTarget.heal(healCount);
        }
        pAttacker.sendSystemMessage(Component.literal(""+pAttacker.getAttributeValue(Attributes.ATTACK_DAMAGE)));
        pStack.hurtAndBreak(1, pAttacker, (target) -> {
            target.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        });
        return true;
    }
}
