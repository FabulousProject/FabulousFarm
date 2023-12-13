package me.alpho320.fabulous.farm.api.sprinkler.animation;

import me.alpho320.fabulous.farm.FarmPlugin;
import me.alpho320.fabulous.farm.api.sprinkler.SprinklerHolder;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

public abstract class SprinklerAnimation {

    protected final @NotNull FarmPlugin plugin;
    protected final @NotNull ConfigurationSection section;

    public SprinklerAnimation(@NotNull FarmPlugin plugin, @NotNull ConfigurationSection section) {
        this.plugin = plugin;
        this.section = section;
    }

    public abstract void register();
    public abstract void animate(@NotNull SprinklerHolder sprinkler);

    public @NotNull FarmPlugin plugin() {
        return this.plugin;
    }

    public @NotNull ConfigurationSection section() {
        return this.section;
    }

    public abstract int duration();

}