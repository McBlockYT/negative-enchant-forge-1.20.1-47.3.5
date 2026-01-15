package net.Babychaosfloh.negativeentchant.item;

//import net.Babychaosfloh.negativeentchant.block.ModBlocks;
import net.Babychaosfloh.negativeentchant.NegativeEntchant;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static net.minecraft.world.item.Items.TNT;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, NegativeEntchant.MOD_ID);

    public static final RegistryObject<CreativeModeTab> JV_BLOOD = CREATIVE_MODE_TABS.register("blood_items",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(TNT))
                    .title(Component.translatable("justvampires.creativetab.blood"))
                    .displayItems((pParameters, pOutput) -> {
                        //pOutput.accept(ModItems.BLOOD_SYRINGE.get());

                        pOutput.accept(Items.GOLD_INGOT);
                        pOutput.accept(Items.IRON_INGOT);
                        pOutput.accept(Items.GLASS);

                        //pOutput.accept(ModBlocks.SAPPHIRE_BLOCK.get());
                        //pOutput.accept(ModBlocks.ALTAR_BLOCK.get());
                        //pOutput.accept(ModBlocks.TESTBLOCK.get());

                    })
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}