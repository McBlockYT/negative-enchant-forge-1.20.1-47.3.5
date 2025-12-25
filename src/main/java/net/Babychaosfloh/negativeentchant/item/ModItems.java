package net.Babychaosfloh.negativeentchant.item;

import net.Babychaosfloh.negativeentchant.NegativeEntchant;
import net.Babychaosfloh.negativeentchant.item.Custom.BloodSyringeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, NegativeEntchant.MOD_ID);

    public static final RegistryObject<Item> BLOOD_SYRINGE = ITEMS.register("blood_syringe",
            () -> new BloodSyringeItem(new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.UNCOMMON)
            ));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}