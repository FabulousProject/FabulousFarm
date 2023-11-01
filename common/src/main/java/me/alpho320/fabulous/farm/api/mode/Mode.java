package me.alpho320.fabulous.farm.api.mode;

import org.jetbrains.annotations.NotNull;

public enum Mode {

    WHITELIST("whitelist"),
    BLACKLIST("blacklist");

    private final String type;
    Mode(String type) {
        this.type = type;
    }

    public @NotNull String type() {
        return this.type;
    }

    public static @NotNull Mode match(@NotNull String type) {
        for (Mode value : values()) {
            if (value.type().equalsIgnoreCase(type)) {
                return value;
            }
        }

        throw new IllegalArgumentException(type + " is not valid mode type!");
    }

}