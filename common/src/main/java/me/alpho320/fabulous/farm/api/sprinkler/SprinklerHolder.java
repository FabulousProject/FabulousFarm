package me.alpho320.fabulous.farm.api.sprinkler;

import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public class SprinklerHolder {

    private final @NotNull Sprinkler sprinkler;

    private @NotNull Sprinkler.State state = Sprinkler.State.IDLE;
    private final @NotNull Location location;

    private int water = 0;

    public SprinklerHolder(@NotNull Sprinkler sprinkler, @NotNull Sprinkler.State state, @NotNull Location location, int water) {
        this.sprinkler = sprinkler;
        this.state = state;
        this.location = location;
        this.water = water;
    }

    public SprinklerHolder(@NotNull Sprinkler sprinkler, @NotNull Location location) {
        this.sprinkler = sprinkler;
        this.location = location;
    }

    public @NotNull Sprinkler sprinkler() {
        return this.sprinkler;
    }

    public @NotNull Sprinkler.State state() {
        return this.state;
    }

    public @NotNull Location location() {
        return this.location.clone();
    }

    public void setState(@NotNull Sprinkler.State state) {
        this.state = state;
    }

    public void setWater(int water) {
        this.water = water;
    }

    public int water() {
        return this.water;
    }

}