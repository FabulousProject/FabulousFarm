package me.alpho320.fabulous.farm.api.condition;

import me.alpho320.fabulous.farm.FarmPlugin;
import me.alpho320.fabulous.farm.util.serializable.SerializableLocation;
import org.jetbrains.annotations.NotNull;

public abstract class Condition {

    protected final @NotNull FarmPlugin plugin;

    public Condition(@NotNull FarmPlugin plugin) {
        this.plugin = plugin;
    }

    public abstract boolean check(@NotNull SerializableLocation location);

}