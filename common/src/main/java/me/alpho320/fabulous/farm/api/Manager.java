package me.alpho320.fabulous.farm.api;

import me.alpho320.fabulous.farm.FarmPlugin;
import org.jetbrains.annotations.NotNull;

public abstract class Manager {

    public abstract void setup(FarmPlugin plugin);
    public abstract @NotNull FarmPlugin plugin();

}