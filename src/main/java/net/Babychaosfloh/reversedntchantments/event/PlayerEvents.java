package net.Babychaosfloh.reversedntchantments.event;

import net.Babychaosfloh.reversedntchantments.ReversedEnchantmentsMain;
import net.Babychaosfloh.reversedntchantments.config.ReversedEnchantmentsServerConfigs;
import net.Babychaosfloh.reversedntchantments.item.ModEnchantments;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
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

@Mod.EventBusSubscriber(modid = ReversedEnchantmentsMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerEvents {
    private static final UUID SPEED_MODIFIER_SOUL_SPEED_UUID = UUID.fromString("c9705ff6-1a2e-42bd-9efd-63c1c5b118e1");
    private static final UUID SPEED_MODIFIER_SWIFT_SNEAK_UUID = UUID.fromString("9a5c43e3-a5ae-4f60-aebf-287bde5339c4");
    private static boolean wasOnSoulSand;
    private static boolean wasSneaking;
    private static boolean REVERSED_SWIFT_SNEAK;
    private static boolean REVERSED_SOUL_SPEED;
    private static boolean REVERSED_VANISHING_CURSE;

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        REVERSED_SWIFT_SNEAK = ReversedEnchantmentsServerConfigs.ENCHANTMENTS.get("reversed_swift_sneak").get();
        REVERSED_SOUL_SPEED = ReversedEnchantmentsServerConfigs.ENCHANTMENTS.get("reversed_soul_speed").get();

        int reversedSwiftSneakLvl = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.REVERSED_SWIFT_SNEAK.get(), player);
        int reversedSoulSpeedLvl = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.REVERSED_SOUL_SPEED.get(), player);

        if (player.level().isClientSide) {
            return;
        }

        boolean isSneaking = player.isCrouching();

       // if (REVERSED_SWIFT_SNEAK) {
            if (isSneaking && !wasSneaking) {
                wasSneaking = true;

                if (reversedSwiftSneakLvl > 0) {
                    AttributeInstance attributeinstance = player.getAttribute(Attributes.MOVEMENT_SPEED);
                    if (attributeinstance == null) {
                        return;
                    }
                    player.sendSystemMessage(Component.literal("Adding "+ reversedSwiftSneakLvl + " " +  -(double) (0.03F + ((float) reversedSwiftSneakLvl * 0.15F))));
                    attributeinstance.removeModifier(
                            new AttributeModifier(SPEED_MODIFIER_SWIFT_SNEAK_UUID,
                                    "Reversed Swift sneak boost",
                                    -(double) (0.03F + ((float) reversedSwiftSneakLvl * 0.15F)),
                                    AttributeModifier.Operation.ADDITION));
                    attributeinstance.addTransientModifier(
                            new AttributeModifier(SPEED_MODIFIER_SWIFT_SNEAK_UUID,
                                    "Reversed Swift sneak boost",
                                    -(double) (0.03F + ((float) reversedSwiftSneakLvl * 0.15F)),
                                    AttributeModifier.Operation.ADDITION));
                }
            } else if (!isSneaking && wasSneaking) {
                wasSneaking = false;
                if (reversedSwiftSneakLvl > 0) {
                    player.sendSystemMessage(Component.literal("ID: " + SPEED_MODIFIER_SWIFT_SNEAK_UUID));
                    AttributeInstance attributeinstance = player.getAttribute(Attributes.MOVEMENT_SPEED);
                    if (attributeinstance == null) {
                        return;
                    }
                    player.sendSystemMessage(Component.literal("Removing"));
                    attributeinstance.removeModifier(
                            new AttributeModifier(SPEED_MODIFIER_SWIFT_SNEAK_UUID,
                                    "Reversed Swift sneak boost",
                                    (double) -(0.03F + ((float) reversedSoulSpeedLvl * 0.15F)),
                                    AttributeModifier.Operation.ADDITION));
                }
            }
      //  }

        if (REVERSED_SOUL_SPEED) {
            if (reversedSoulSpeedLvl > 0) {
                if (!player.getBlockStateOn().isAir()) {
                    if (onSoulSpeedBlock(player) && !wasOnSoulSand) {
                        wasOnSoulSand = true;
                        if (reversedSwiftSneakLvl > 0) {
                            AttributeInstance attributeinstance = player.getAttribute(Attributes.MOVEMENT_SPEED);
                            if (attributeinstance == null) {
                                return;
                            }

                            attributeinstance.addTransientModifier(
                                    new AttributeModifier(SPEED_MODIFIER_SOUL_SPEED_UUID,
                                            "Reversed Soul speed boost",
                                            (double) -(0.03F * (1.0F + (float) reversedSoulSpeedLvl * 0.35F)),
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
                                        "Reversed Soul speed boost",
                                        (double) -(0.03F * (1.0F + (float) reversedSoulSpeedLvl * 0.35F)), AttributeModifier.Operation.ADDITION));
                    }
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
        REVERSED_VANISHING_CURSE = ReversedEnchantmentsServerConfigs.ENCHANTMENTS.get("reversed_vanishing_curse").get();

        Collection<ItemEntity> drops = event.getDrops().stream().toList();
        Player player = (Player) event.getEntity();
        Level level = player.level();


        if (player.level().isClientSide) {
            return;
        }
        if (!REVERSED_VANISHING_CURSE) {
            return;
        }

        for (ItemEntity itemEntity : drops) {
            ItemStack itemStack = itemEntity.getItem();
            ItemEntity itemEntity1 = itemEntity.copy();
            int reversedVanishLvl = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.REVERSED_VANISHING_CURSE.get(), itemStack);

            if (reversedVanishLvl > 0) {
                removeEnchantment(itemStack, ModEnchantments.REVERSED_VANISHING_CURSE.get());

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