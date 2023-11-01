package me.alpho320.fabulous.farm.api.event;

import org.jetbrains.annotations.NotNull;

public enum EventType {

    ON_PLANT("on-plant"),
    ON_HARVEST("on-harvest"),
    ON_BREAK("on-break"),
    ON_GROW("on-grow"),
    ON_PLACE("on-place"),
    ON_FILL("on-fill");

    private final @NotNull String type;
    EventType(@NotNull String type) {
        this.type = type;
    }

    public @NotNull String type() {
        return this.type;
    }

    public static @NotNull EventType match(@NotNull String type) {
        for (EventType eventType : values()) {
            if (eventType.type().equalsIgnoreCase(type) || eventType.type().startsWith(type.toLowerCase())) {
                return eventType;
            }
        }
        throw new IllegalStateException(" | EventType " + type + " not found!");
    }

}