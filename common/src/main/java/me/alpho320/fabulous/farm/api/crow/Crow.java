package me.alpho320.fabulous.farm.api.crow;

import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public abstract class Crow {

    private final @NotNull String id;
    private final @NotNull String entity;

    private final double chance;

    public Crow(@NotNull String id, @NotNull String entity, double chance) {
        this.id = id;
        this.entity = entity;
        this.chance = chance;
    }

    public @NotNull String id() {
        return this.id;
    }

    public @NotNull String entity() {
        return this.entity;
    }

    public double chance() {
        return this.chance;
    }

    public abstract void spawn(@NotNull Pot pot, @NotNull Location location);

}