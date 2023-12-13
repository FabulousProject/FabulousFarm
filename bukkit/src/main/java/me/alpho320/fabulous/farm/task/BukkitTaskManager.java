package me.alpho320.fabulous.farm.task;

import me.alpho320.fabulous.farm.BukkitFarmPlugin;
import me.alpho320.fabulous.farm.task.impl.BackupTask;
import me.alpho320.fabulous.farm.task.impl.BeeCheckTask;
import me.alpho320.fabulous.farm.task.impl.LogSaveTask;
import me.alpho320.fabulous.farm.task.impl.SchedulerCheckTask;
import org.jetbrains.annotations.NotNull;

public class BukkitTaskManager extends TaskManager {

    private final @NotNull BukkitFarmPlugin plugin;

    public BukkitTaskManager(@NotNull BukkitFarmPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void startTasks() {
        plugin.logger().info(" | Tasks Starting...");
        setupBackupTask();
        setupLogSaveTask();
        if (plugin.schedulerManager().enabled()) setupSchedulerCheckTask();
    }

    private void setupBackupTask() {
        long time = plugin.getConfig().getInt("Main.backup-interval", 1800) * 20L;
        new BackupTask(plugin).runTaskTimerAsynchronously(plugin, time, time);
    }

    private void setupLogSaveTask() {
        long time = plugin.getConfig().getInt("Main.log-save-interval", 120) * 20L;
        new LogSaveTask(plugin).runTaskTimerAsynchronously(plugin, time, time);
    }

    private void setupSchedulerCheckTask() {
        new SchedulerCheckTask(plugin).runTaskTimerAsynchronously(plugin, 35, 35);
    }

}