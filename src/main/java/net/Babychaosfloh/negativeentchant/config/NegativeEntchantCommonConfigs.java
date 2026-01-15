package net.Babychaosfloh.negativeentchant.config;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NegativeEntchantCommonConfigs {

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final Map<String, ForgeConfigSpec.BooleanValue> ENCHANTMENTS = new HashMap<>();


    static {
        BUILDER.push("Enchantments")
                .comment("Whether a reversed enchantment should be registered or not.");

        // Example entries
        ENCHANTMENTS.put("valuename", BUILDER
                .comment("Enable valuename enchantment")
                .define("valuename", true));

        ENCHANTMENTS.put("valuenextname", BUILDER
                .comment("Enable valuenextname enchantment")
                .define("valuenextname", false));

        ENCHANTMENTS.put("anotherone", BUILDER
                .comment("Enable anotherone enchantment")
                .define("anotherone", true));

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}