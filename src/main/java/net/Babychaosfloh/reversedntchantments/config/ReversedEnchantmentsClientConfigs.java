package net.Babychaosfloh.reversedntchantments.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ReversedEnchantmentsClientConfigs {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    static {
        BUILDER.push("Client configs for JustVampires");

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}