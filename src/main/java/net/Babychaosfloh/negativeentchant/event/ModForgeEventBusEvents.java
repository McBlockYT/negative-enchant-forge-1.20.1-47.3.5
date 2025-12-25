package net.Babychaosfloh.negativeentchant.event;

import net.Babychaosfloh.negativeentchant.NegativeEntchant;
import net.Babychaosfloh.negativeentchant.player.MojangLookup;
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

@Mod.EventBusSubscriber(modid = NegativeEntchant.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModForgeEventBusEvents {

    // Store skin data temporarily while waiting for async fetch
    private static final Map<Player, String> pendingSkullNames = new ConcurrentHashMap<>();

    @SubscribeEvent
    public static void onAnvilUpdate(AnvilUpdateEvent event) {
        String enteredName = event.getName();
        if (enteredName == null || enteredName.isEmpty()) return;

        ItemStack left = event.getLeft();
        if (left.getItem() != Items.PLAYER_HEAD) return;
        if (!enteredName.contains("/")) return;

        String tempName = enteredName.replace("/", "");
        ItemStack output = left.copy();

        // Set placeholder skull owner (just Name)
        CompoundTag tag = output.getOrCreateTag();
        CompoundTag skullOwner = new CompoundTag();
        skullOwner.putString("Name", tempName);
        tag.put("SkullOwner", skullOwner);
        output.setTag(tag);

        event.setOutput(output);
        event.setCost(1);
        event.setMaterialCost(1);

        // Store the player and name for async fetch
        Player player = event.getPlayer();
        if (player != null) {
            pendingSkullNames.put(player, tempName);

            // Async lookup
            new Thread(() -> {
                try {
                    MojangLookup.PlayerData data = MojangLookup.getPlayerData(tempName);
                    player.getServer().execute(() -> {
                        // Apply when player takes the item
                        pendingSkullNames.put(player, data.uuid + "|" + data.texture.value + "|" + (data.texture.signature != null ? data.texture.signature : ""));
                    });
                } catch (Exception e) {
                    System.out.println("Failed to fetch skin for " + tempName + ": " + e);
                    pendingSkullNames.put(player, tempName); // fallback to just name
                }
            }).start();
        }
    }

    @SubscribeEvent
    public static void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        ItemStack output = event.getCrafting();
        Player player = event.getEntity();

        if (output.getItem() == Items.PLAYER_HEAD && pendingSkullNames.containsKey(player)) {
            String dataString = pendingSkullNames.remove(player);

            if (dataString.contains("|")) {
                // Full UUID + texture + signature
                String[] parts = dataString.split("\\|");
                String uuid = parts[0];
                String value = parts[1];
                String signature = parts.length > 2 ? parts[2] : null;

                CompoundTag tag = output.getOrCreateTag();
                CompoundTag skullOwner = new CompoundTag();
                skullOwner.putString("Id", uuid);

                CompoundTag properties = new CompoundTag();
                ListTag propsList = new ListTag();
                CompoundTag texturesTag = new CompoundTag();
                texturesTag.putString("Value", value);
                if (signature != null) texturesTag.putString("Signature", signature);
                propsList.add(texturesTag);
                properties.put("textures", propsList);
                skullOwner.put("Properties", properties);

                tag.put("SkullOwner", skullOwner);
                output.setTag(tag);
            } else {
                // fallback: just set Name
                CompoundTag tag = output.getOrCreateTag();
                CompoundTag skullOwner = new CompoundTag();
                skullOwner.putString("Name", dataString);
                tag.put("SkullOwner", skullOwner);
                output.setTag(tag);
            }
        }
    }
}
