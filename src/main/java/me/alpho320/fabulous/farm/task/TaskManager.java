package me.alpho320.fabulous.farm.task;

import me.alpho320.fabulous.farm.FarmPlugin;
import me.alpho320.fabulous.farm.task.impl.BackupTask;
import me.alpho320.fabulous.farm.task.impl.LogSaveTask;
import org.jetbrains.annotations.NotNull;

public class TaskManager {

    public static void startTasks(@NotNull FarmPlugin plugin) {
        plugin.getLogger().info(" | Tasks Starting...");
        setupBackupTask(plugin);
        setupLogSaveTask(plugin);
    }

    private static void setupBackupTask(@NotNull FarmPlugin plugin) {
        long time = plugin.getConfig().getInt("Main.backup-interval", 1800) * 20L;
        new BackupTask(plugin).runTaskTimerAsynchronously(plugin, time, time);
    }

    private static void setupLogSaveTask(@NotNull FarmPlugin plugin) {
        long time = plugin.getConfig().getInt("Main.log-save-interval", 120) * 20L;
        new LogSaveTask(plugin).runTaskTimerAsynchronously(plugin, time, time);
    }

}