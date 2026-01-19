package net.Babychaosfloh.reversedntchantments.event;

import net.Babychaosfloh.reversedntchantments.ReversedEnchantmentsMain;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Mod.EventBusSubscriber(modid = ReversedEnchantmentsMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModForgeEventBusEvents {

    // Store skin data temporarily while waiting for async fetch
    private static final Map<Player, String> pendingSkullNames = new ConcurrentHashMap<>();



}
