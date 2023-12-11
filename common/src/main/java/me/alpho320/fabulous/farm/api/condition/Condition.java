package me.alpho320.fabulous.farm.api.condition;

import me.alpho320.fabulous.farm.FarmPlugin;
import me.alpho320.fabulous.farm.util.serializable.SerializableLocation;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

public abstract class Condition {

    protected final @NotNull FarmPlugin plugin;
    protected final @NotNull ConfigurationSection section;

    public Condition(@NotNull FarmPlugin plugin, @NotNull ConfigurationSection section) {
        this.plugin = plugin;
        this.section = section;
    }

    public abstract boolean register();
    public abstract boolean check(@NotNull SerializableLocation location);

}