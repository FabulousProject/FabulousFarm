package me.alpho320.fabulous.farm.api.vermin;

import me.alpho320.fabulous.farm.api.pot.Pot;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public class Vermin {

    private final @NotNull String id;
    private final @NotNull String name;
    private final @NotNull String entity;

    private final double chance;

    public Vermin(@NotNull String id, @NotNull String name, @NotNull String entity, double chance) {
        this.id = id;
        this.name = name;
        this.entity = entity;
        this.chance = chance;
    }

    public @NotNull String id() {
        return this.id;
    }

    public @NotNull String name() {
        return this.name;
    }

    public @NotNull String entity() {
        return this.entity;
    }

    public double chance() {
        return this.chance;
    }

    public void spawn(@NotNull Pot pot, @NotNull Location location) {
        // TODO: 11/12/2023 spawn vermin.
    }

}