package me.alpho320.fabulous.farm.task;

import me.alpho320.fabulous.farm.FarmPlugin;
import org.jetbrains.annotations.NotNull;

public abstract class TaskManager {

    protected final @NotNull FarmPlugin plugin;

    public TaskManager(@NotNull FarmPlugin plugin) {
        this.plugin = plugin;
    }

    public abstract void startTasks();

}