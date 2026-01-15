package net.Babychaosfloh.negativeentchant.mixin;


import net.Babychaosfloh.negativeentchant.item.ModEnchantments;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;


@Mixin(CrossbowItem.class)
public class CrossBowMixin {
    @Unique
    private static int negativeMultishotLvl;
    @Unique
    private static int negativeInfinityLvl;

    /**
     * @author Babychaosfloh_HD
     * @reason Adding negative enchantments
     */
    @Overwrite
    public static boolean tryLoadProjectiles(LivingEntity pShooter, ItemStack pCrossbowStack) {

        int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MULTISHOT, pCrossbowStack);
        int j = i == 0 ? 1 : 3;
        boolean flag = pShooter instanceof Player && ((Player)pShooter).getAbilities().instabuild;
        ItemStack itemstack = pShooter.getProjectile(pCrossbowStack);
        ItemStack itemstack1 = itemstack.copy();

        for(int k = 0; k < j; ++k) {
            if (k > 0) {
                itemstack = itemstack1.copy();
            }

            if (itemstack.isEmpty() && flag) {
                itemstack = new ItemStack(Items.ARROW);
                itemstack1 = itemstack.copy();
            }

            if (!loadProjectile(pShooter, pCrossbowStack, itemstack, k > 0, flag)) {
                return false;
            }
        }

        return true;
    }

    @Unique
    private static boolean loadProjectile(LivingEntity pShooter, ItemStack pCrossbowStack, ItemStack pAmmoStack, boolean pHasAmmo, boolean pIsCreative) {
        if (pAmmoStack.isEmpty()) {
            return false;
        } else {
            boolean flag = pIsCreative && pAmmoStack.getItem() instanceof ArrowItem;
            ItemStack itemstack;
            negativeMultishotLvl = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.NEGATIVE_MULTISHOT.get(), pCrossbowStack);
            negativeInfinityLvl = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.NEGATIVE_INFINITY.get(), pCrossbowStack);

            pShooter.sendSystemMessage(Component.literal("negMult "+negativeMultishotLvl));
            pShooter.sendSystemMessage(Component.literal("negInf "+negativeInfinityLvl));

            if (!flag && !pIsCreative && !pHasAmmo) {
                int splitter = 0;
                if (negativeInfinityLvl > 0) {
                    splitter = negativeInfinityLvl;
                } else if (negativeMultishotLvl > 0) {
                    splitter = negativeMultishotLvl * 3;
                } else {
                    splitter = 1;
                }

                /*if (isStackBig(pAmmoStack, splitter, (Player) pShooter) != null) {
                    ItemStack itemStack3 = isStackBig(pAmmoStack, splitter, (Player) pShooter);
                    ItemStack itemStack4 = pAmmoStack.copy();
                    itemstack = pAmmoStack.split(splitter);
                    itemstack.setCount(itemstack.getCount() + itemStack3.getCount());
                    isStackBig(pAmmoStack, splitter, (Player) pShooter).split(itemStack4.getCount());
                } else {
                }
                 */
                itemstack = pAmmoStack.split(splitter);

                if (pAmmoStack.isEmpty() && pShooter instanceof Player) {
                    ((Player)pShooter).getInventory().removeItem(pAmmoStack);
                }
                if (negativeInfinityLvl > 0) {
                    setCharged(pCrossbowStack, false);
                    removeChargedProjectile(pCrossbowStack, itemstack);
                } else {
                    itemstack = pAmmoStack.copy();
                    setCharged(pCrossbowStack, true);
                    addChargedProjectile(pCrossbowStack, itemstack);
                }
            }
            return true;
        }

    }
/*
    @Unique
    private static ItemStack isStackBig(ItemStack itemStack, int count, Player player) {
        if (itemStack.getCount() < count) {
            for (int i = 0; i < 36; i++) {
                ItemStack itemStack2 = player.getInventory().getItem(i);
                if (itemStack2.getItem() instanceof ArrowItem) {
                    return itemStack2;
                }
            }
        }
        return null;
    }

 */

    @Unique
    private static void setCharged(ItemStack pCrossbowStack, boolean pIsCharged) {
        CompoundTag compoundtag = pCrossbowStack.getOrCreateTag();
        compoundtag.putBoolean("Charged", pIsCharged);
    }

    @Unique
    private static void removeChargedProjectile(ItemStack pCrossbowStack, ItemStack pAmmoStack) {
        CompoundTag compoundtag = pCrossbowStack.getOrCreateTag();
        ListTag listtag;
        if (compoundtag.contains("ChargedProjectiles", 9)) {
            listtag = compoundtag.getList("ChargedProjectiles", 10);
        } else {
            listtag = new ListTag();
        }

        CompoundTag compoundtag1 = new CompoundTag();
        pAmmoStack.save(compoundtag1);
        listtag.remove(compoundtag1);
        compoundtag.put("ChargedProjectiles", listtag);
    }

    @Unique
    private static void addChargedProjectile(ItemStack pCrossbowStack, ItemStack pAmmoStack) {
        CompoundTag compoundtag = pCrossbowStack.getOrCreateTag();
        ListTag listtag;
        if (compoundtag.contains("ChargedProjectiles", 9)) {
            listtag = compoundtag.getList("ChargedProjectiles", 10);
        } else {
            listtag = new ListTag();
        }

        CompoundTag compoundtag1 = new CompoundTag();
        pAmmoStack.save(compoundtag1);
        listtag.add(compoundtag1);
        compoundtag.put("ChargedProjectiles", listtag);
    }
}
