package net.Babychaosfloh.reversedntchantments.event;

import net.Babychaosfloh.reversedntchantments.ReversedEnchantmentsMain;
import net.Babychaosfloh.reversedntchantments.config.ReversedEnchantmentsServerConfigs;
import net.Babychaosfloh.reversedntchantments.item.ModEnchantments;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid= ReversedEnchantmentsMain.MOD_ID)
public class EnchantmentEvents {
    private static int knockLvl;
    private static boolean REVERSED_KNOCKBACK;

    @SubscribeEvent
    public static void onAttack(AttackEntityEvent event) {
        if (event.getEntity().level().isClientSide()) {
            return;
        }

        REVERSED_KNOCKBACK = ReversedEnchantmentsServerConfigs.ENCHANTMENTS.get("reversed_knockback").get();

        Player player = event.getEntity();
        Entity target =  event.getTarget();

        knockLvl = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.REVERSED_KNOCKBACK.get(), player);

        if (REVERSED_KNOCKBACK) {
            if (knockLvl > 0) {
                double pStrength = (double) (knockLvl) * 0.5f;
                double pX = Mth.sin(player.getYRot() * ((float) Math.PI / 180F));
                double pZ = -Mth.cos(player.getYRot() * ((float) Math.PI / 180F));
                Vec3 vec3 = target.getDeltaMovement();
                Vec3 vec31 = (new Vec3(pX, 0.0D, pZ)).normalize().scale(pStrength);
                target.setDeltaMovement((vec3.x * -1) / 2.0D + vec31.x, target.onGround() ? Math.min(0.4D, vec3.y / 2.0D + pStrength) : vec3.y, (vec3.z * -1) / 2.0D + vec31.z);

            /*
            ((LivingEntity)target).knockback((double) (- knockLvl) * 0.5f,
                    Mth.sin(target.getYRot() * ((float)Math.PI / 180F)),
                    (-Mth.cos(target.getYRot() * ((float)Math.PI / 180F))));
            player.sendSystemMessage(Component.nullToEmpty(Component.literal("pStrength:" + ((double) (- knockLvl) * 0.5f)) + "pValue1:" + (Mth.sin(target.getYRot() * ((float)Math.PI / 180F))) + "pValue2:" + (-Mth.cos(target.getYRot() * ((float)Math.PI / 180F)))));
             */
            }
        }
    }

    @SubscribeEvent
    public static void onItemUseStop(LivingEntityUseItemEvent.Stop event) {
        if (event.getEntity().level().isClientSide()) return;
        if (!event.getEntity().level().isClientSide()) return;

        Player player = (Player) event.getEntity();
        ItemStack iStack = event.getItem();
    }
}