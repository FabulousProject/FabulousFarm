package me.alpho320.fabulous.farm.task;

import me.alpho320.fabulous.farm.FarmPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public abstract class Task extends BukkitRunnable {

    private final @NotNull FarmPlugin plugin;
    private final @NotNull UUID id;

    public Task(@NotNull FarmPlugin plugin, @NotNull UUID id) {
        this.plugin = plugin;
        this.id = id;
    }

    public @NotNull UUID id() {
        return this.id;
    }

    public @NotNull FarmPlugin plugin() {
        return this.plugin;
    }

}