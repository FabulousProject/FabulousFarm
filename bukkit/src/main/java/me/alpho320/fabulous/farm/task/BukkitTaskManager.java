package me.alpho320.fabulous.farm.task;

import me.alpho320.fabulous.farm.BukkitFarmPlugin;
import me.alpho320.fabulous.farm.FarmPlugin;
import me.alpho320.fabulous.farm.task.impl.BackupTask;
import me.alpho320.fabulous.farm.task.impl.LogSaveTask;
import org.jetbrains.annotations.NotNull;

public class BukkitTaskManager extends TaskManager {

    public BukkitTaskManager(@NotNull FarmPlugin plugin) {
        super(plugin);
    }

    @Override
    public void startTasks() {
        plugin.logger().info(" | Tasks Starting...");
        setupBackupTask((BukkitFarmPlugin) plugin);
        setupLogSaveTask((BukkitFarmPlugin) plugin);
    }

    private void setupBackupTask(@NotNull BukkitFarmPlugin plugin) {
        long time = plugin.getConfig().getInt("Main.backup-interval", 1800) * 20L;
        new BackupTask(plugin).runTaskTimerAsynchronously(plugin, time, time);
    }

    private void setupLogSaveTask(@NotNull BukkitFarmPlugin plugin) {
        long time = plugin.getConfig().getInt("Main.log-save-interval", 120) * 20L;
        new LogSaveTask(plugin).runTaskTimerAsynchronously(plugin, time, time);
    }

}