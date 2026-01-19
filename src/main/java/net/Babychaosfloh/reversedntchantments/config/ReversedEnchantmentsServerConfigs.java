package net.Babychaosfloh.reversedntchantments.config;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.HashMap;
import java.util.Map;

public class ReversedEnchantmentsServerConfigs {

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final Map<String, ForgeConfigSpec.BooleanValue> ENCHANTMENTS = new HashMap<>();


    static {
        BUILDER.push("Enable Enchantments")
                .comment("Whether a reversed enchantment should be enabled or not." +
                        "\n⚠️Chaning these settings might require a game restart!⚠️" +
                        "\nAdding non existing reversed enchantments won't do anything!" +
                        "\nA full reversed enchantment list is available in the wiki!" +
                        "\n\nFormat:" +
                        "\nreversed_enchantment = true [or false]");

        ENCHANTMENTS.put("reversed_knockback", BUILDER
                .define("reversed_knockback", true));

        ENCHANTMENTS.put("reversed_multishot", BUILDER
                .define("reversed_multishot", false));

        ENCHANTMENTS.put("reversed_infinity", BUILDER
                .define("reversed_infinity", true));

        ENCHANTMENTS.put("reversed_sharpness", BUILDER
                .define("reversed_sharpness", true));

        ENCHANTMENTS.put("reversed_smite", BUILDER
                .define("reversed_smite", true));

        ENCHANTMENTS.put("reversed_bane_of_arthropods", BUILDER
                .define("reversed_bane_of_arthropods", true));

        ENCHANTMENTS.put("reversed_swift_sneak", BUILDER
                .define("reversed_swift_sneak", true));

        ENCHANTMENTS.put("reversed_soul_speed", BUILDER
                .define("reversed_soul_speed", true));

        ENCHANTMENTS.put("reversed_vanishing_curse", BUILDER
                .define("reversed_vanishing_curse", true));

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}