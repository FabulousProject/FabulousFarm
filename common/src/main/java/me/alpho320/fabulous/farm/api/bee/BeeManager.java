package me.alpho320.fabulous.farm.api.bee;

import me.alpho320.fabulous.farm.data.ParticleData;
import org.jetbrains.annotations.NotNull;

public abstract class BeeManager {

    public abstract void setup();

    public abstract boolean enabled();
    public abstract int radius();
    public abstract double chance();
    public abstract int checkInterval();

    public abstract @NotNull ParticleData particleData();
    public abstract void check();

}