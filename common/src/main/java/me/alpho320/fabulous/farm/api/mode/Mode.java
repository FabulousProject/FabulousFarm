package me.alpho320.fabulous.farm.api.mode;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Mode {

    private final @NotNull Type type;
    private final @NotNull List<String> list;

    public Mode(@NotNull Type type, @NotNull List<String> list) {
        this.type = type;
        this.list = list;
    }

    public @NotNull Type type() {
        return this.type;
    }

    public @NotNull List<String> list() {
        return this.list;
    }

    public boolean check(@NotNull String id) {
        if (this.type == Type.WHITELIST) {
            return this.list.contains(id);
        } else {
            return !this.list.contains(id);
        }
    }

    public enum Type {

        WHITELIST("whitelist"),
        BLACKLIST("blacklist");

        private final String type;
        Type(String type) {
            this.type = type;
        }

        public @NotNull String type() {
            return this.type;
        }

        public static @NotNull Mode.Type match(@NotNull String type) {
            for (Mode.Type value : values()) {
                if (value.type().equalsIgnoreCase(type)) {
                    return value;
                }
            }

            throw new IllegalArgumentException(type + " is not valid mode type!");
        }

    }

}