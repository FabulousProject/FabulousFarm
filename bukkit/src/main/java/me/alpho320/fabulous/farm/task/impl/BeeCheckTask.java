package me.alpho320.fabulous.farm.task.impl;

import me.alpho320.fabulous.farm.FarmPlugin;
import me.alpho320.fabulous.farm.task.Task;
import org.jetbrains.annotations.NotNull;

public class BeeCheckTask extends Task {

    public BeeCheckTask(@NotNull FarmPlugin plugin) {
        super(plugin);
    }

    @Override
    public void run() {
        long now = System.currentTimeMillis();
        plugin().logger().debug(" | Bee check task running...");

        plugin().farmManager().beeManager().check();

        plugin().logger().debug(" | Bee check task took " + (System.currentTimeMillis() - now) + "ms.");
        plugin().logger().debug(" | If that check took more than 50ms, you should check your server performance or increase 'check-interval' value more higher.");
    }

}
