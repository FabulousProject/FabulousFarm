package me.alpho320.fabulous.farm.task.impl;

import me.alpho320.fabulous.core.bukkit.util.debugger.Debug;
import me.alpho320.fabulous.farm.BukkitFarmAPI;
import me.alpho320.fabulous.farm.BukkitFarmPlugin;
import me.alpho320.fabulous.farm.log.LogHandler;
import me.alpho320.fabulous.farm.task.Task;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class LogSaveTask extends Task {

    public LogSaveTask(@NotNull BukkitFarmPlugin plugin) {
        super(plugin);
    }

    @Override
    public void run() {
        Debug.debug(0, " ");
        Debug.debug(0, " | Logs saving...");

        long now = System.currentTimeMillis();
        LogHandler.save();
        Debug.debug(0, " | Logs saving took " + BukkitFarmAPI.took(now) + "ms");

    }

}