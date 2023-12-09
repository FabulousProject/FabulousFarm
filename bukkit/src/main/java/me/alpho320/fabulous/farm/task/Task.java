package me.alpho320.fabulous.farm.task;

import me.alpho320.fabulous.farm.BukkitFarmPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public abstract class Task extends BukkitRunnable {

    private final @NotNull BukkitFarmPlugin plugin;
    private final @NotNull UUID id;

    public Task(@NotNull BukkitFarmPlugin plugin, @NotNull UUID id) {
        this.plugin = plugin;
        this.id = id;
    }

    public @NotNull UUID id() {
        return this.id;
    }

    public @NotNull BukkitFarmPlugin plugin() {
        return this.plugin;
    }

}