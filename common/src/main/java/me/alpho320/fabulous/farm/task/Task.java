package me.alpho320.fabulous.farm.task;

import me.alpho320.fabulous.farm.FarmPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public abstract class Task extends BukkitRunnable {

    private final @NotNull FarmPlugin plugin;

    public Task(@NotNull FarmPlugin plugin) {
        this.plugin = plugin;
    }

    public @NotNull FarmPlugin plugin() {
        return this.plugin;
    }

}