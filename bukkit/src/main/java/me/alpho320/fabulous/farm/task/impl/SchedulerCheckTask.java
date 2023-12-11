package me.alpho320.fabulous.farm.task.impl;

import me.alpho320.fabulous.farm.FarmPlugin;
import me.alpho320.fabulous.farm.task.Task;
import org.jetbrains.annotations.NotNull;

public class SchedulerCheckTask extends Task {

    public SchedulerCheckTask(@NotNull FarmPlugin plugin) {
        super(plugin);
    }

    @Override
    public void run() {
        long now = System.currentTimeMillis();
        plugin().logger().debug(" | SchedulerCheckTask is running.");
        plugin().schedulerManager().check();
        plugin().logger().debug(" | SchedulerCheckTask is finished. (took " + (System.currentTimeMillis() - now) + "ms)");
    }

}