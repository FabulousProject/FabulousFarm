package me.alpho320.fabulous.farm.api.fertilizer;

import org.jetbrains.annotations.NotNull;

public enum FertilizerType {

    PRODUCE_MULTIPLIER("produce_multiplier"),
    QUALITY("quality"),
    SPEED_GROW("speed_grow");


    private final @NotNull String type;
    FertilizerType(@NotNull String type) {
        this.type = type;
    }

    public @NotNull String type() {
        return this.type;
    }

    public static @NotNull FertilizerType match(@NotNull String type) {
        for (FertilizerType value : values()) {
            if (value.type().equalsIgnoreCase(type)) {
                return value;
            }
        }

        throw new IllegalArgumentException(type + " is not valid fertilizer type!");
    }

}