package net.Babychaosfloh.negativeentchant.event;

import net.Babychaosfloh.negativeentchant.NegativeEntchant;
import net.Babychaosfloh.negativeentchant.item.ModEnchantments;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = NegativeEntchant.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerEvents {
    private static final UUID SPEED_MODIFIER_SOUL_SPEED_UUID = UUID.fromString("c9705ff6-1a2e-42bd-9efd-63c1c5b118e1");
    private static final UUID SPEED_MODIFIER_SWIFT_SNEAK_UUID = UUID.fromString("9a5c43e3-a5ae-4f60-aebf-287bde5339c4");
    private static boolean wasOnSoulSand;
    private static boolean wasSneaking;

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        int negativeSwiftSneakLvl = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.NEGATIVE_SWIFT_SNEAK.get(), player);
        int negativeSoulSpeedLvl = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.NEGATIVE_SOUL_SPEED.get(), player);

        if (player.level().isClientSide) {
            return;
        }
        boolean isSneaking = player.isCrouching();

        if (isSneaking && !wasSneaking) {
                wasSneaking = true;

                if (negativeSwiftSneakLvl > 0) {
                    AttributeInstance attributeinstance = player.getAttribute(Attributes.MOVEMENT_SPEED);
                    if (attributeinstance == null) {
                        return;
                    }
                    attributeinstance.addTransientModifier(
                            new AttributeModifier(SPEED_MODIFIER_SWIFT_SNEAK_UUID,
                                    "Negative Swift sneak boost",
                                    (double) -(0.03F + ((float) negativeSoulSpeedLvl * 0.15F)),
                                    AttributeModifier.Operation.ADDITION));
                }
            } else if (!isSneaking && wasSneaking) {
            wasSneaking = false;
            if (negativeSwiftSneakLvl > 0) {
                    AttributeInstance attributeinstance = player.getAttribute(Attributes.MOVEMENT_SPEED);
                    if (attributeinstance == null) {
                        return;
                    }
                    attributeinstance.removeModifier(
                            new AttributeModifier(SPEED_MODIFIER_SWIFT_SNEAK_UUID,
                                    "Negative Swift sneak boost",
                                    (double) -(0.03F + ((float) negativeSoulSpeedLvl * 0.15F)),
                                    AttributeModifier.Operation.ADDITION));
                }
        }

        if (negativeSoulSpeedLvl > 0) {
            if (!player.getBlockStateOn().isAir()) {
                if (onSoulSpeedBlock(player) && !wasOnSoulSand) {
                    wasOnSoulSand = true;
                    if (negativeSwiftSneakLvl > 0) {
                        AttributeInstance attributeinstance = player.getAttribute(Attributes.MOVEMENT_SPEED);
                        if (attributeinstance == null) {
                            return;
                        }

                        attributeinstance.addTransientModifier(
                                new AttributeModifier(SPEED_MODIFIER_SOUL_SPEED_UUID,
                                        "Negative Soul speed boost",
                                        (double) -(0.03F * (1.0F + (float) negativeSoulSpeedLvl * 0.35F)),
                                        AttributeModifier.Operation.ADDITION));
                        if (player.getRandom().nextFloat() < 0.04F) {
                            ItemStack itemstack = player.getItemBySlot(EquipmentSlot.FEET);
                            itemstack.hurtAndBreak(1, player, (p_21301_) -> {
                                p_21301_.broadcastBreakEvent(EquipmentSlot.FEET);
                            });
                        }
                    }
                } else if ((!onSoulSpeedBlock(player) || player.getBlockStateOn().isAir()) && wasOnSoulSand) {
                    wasOnSoulSand = false;
                    AttributeInstance attributeinstance = player.getAttribute(Attributes.MOVEMENT_SPEED);
                    if (attributeinstance == null) {
                        return;
                    }
                    attributeinstance.removeModifier
                            (new AttributeModifier(SPEED_MODIFIER_SOUL_SPEED_UUID,
                                    "Negative Soul speed boost",
                                    (double) -(0.03F * (1.0F + (float) negativeSoulSpeedLvl * 0.35F)), AttributeModifier.Operation.ADDITION));
                }
            }
        }
    }

    private static boolean onSoulSpeedBlock(Player player) {
        BlockPos posBelow = new BlockPos(
                Mth.floor(player.getX()),
                Mth.floor(player.getY() - 0.2D),
                Mth.floor(player.getZ())
        );

        return player.level()
                .getBlockState(posBelow)
                .is(BlockTags.SOUL_SPEED_BLOCKS);
    }
    
    
    @SubscribeEvent
    public static void LivingDropsEvent(LivingDropsEvent event) {
        Collection<ItemEntity> drops = event.getDrops().stream().toList();
        Player player = (Player) event.getEntity();
        Level level = player.level();

        if (player.level().isClientSide) {
            return;
        }

        for (ItemEntity itemEntity : drops) {
            ItemStack itemStack = itemEntity.getItem();
            ItemEntity itemEntity1 = itemEntity.copy();
            int NegativeVanishLvl = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.NEGATIVE_VANISHING_CURSE.get(), itemStack);

            if (NegativeVanishLvl > 0) {
                removeEnchantment(itemStack, ModEnchantments.NEGATIVE_VANISHING_CURSE.get());

                itemEntity1.setItem(itemStack);

                level.addFreshEntity(itemEntity1);
            }
        }
    }


    public static void removeEnchantment(ItemStack stack, Enchantment enchantment) {
        Map<Enchantment, Integer> enchants = EnchantmentHelper.getEnchantments(stack);
        enchants.remove(enchantment);
        EnchantmentHelper.setEnchantments(enchants, stack);
    }

    }