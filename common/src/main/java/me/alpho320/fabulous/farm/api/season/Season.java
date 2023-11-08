package me.alpho320.fabulous.farm.api.season;

import org.jetbrains.annotations.NotNull;

public class Season {

    private final @NotNull String id;
    private boolean active;

    private final int order;
    private final int days;

    public Season(@NotNull String id, boolean active, int order, int days) {
        this.id = id;
        this.active = active;
        this.order = order;
        this.days = days;
    }

    public @NotNull String id() {
        return this.id;
    }

    public void setActive(boolean enabled) {
        this.active = enabled;
    }

    public boolean isActive() {
        return this.active;
    }

    public int days() {
        return this.days;
    }

    public int order() {
        return this.order;
    }

}