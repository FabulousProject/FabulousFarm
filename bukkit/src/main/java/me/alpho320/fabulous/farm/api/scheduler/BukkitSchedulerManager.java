package me.alpho320.fabulous.farm.api.scheduler;

import me.alpho320.fabulous.farm.BukkitFarmAPI;
import me.alpho320.fabulous.farm.BukkitFarmPlugin;
import me.alpho320.fabulous.farm.api.sprinkler.Sprinkler;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BukkitSchedulerManager extends SchedulerManager {

    private final @NotNull BukkitFarmPlugin plugin;

    private boolean enabled = false;
    private String time = "07:00";
    private long checkTime = 0L;

    public BukkitSchedulerManager(@NotNull BukkitFarmPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean enabled() {
        return this.enabled;
    }

    @Override
    public void setup() {
        plugin.logger().info(" | Scheduler Manager Starting...");
        this.enabled = plugin.getConfig().getBoolean("Main.scheduler.enabled", false);
        this.time = plugin.getConfig().getString("Main.scheduler.time", "07:00");
        this.checkTime = BukkitFarmAPI.instance().getWorldTimeFromFormattedString(this.time);
    }

    @Override
    public @NotNull String time() {
        return this.time;
    }

    @Override
    public long checkTime() {
        return this.checkTime;
    }

    @Override
    public void check() {
        plugin.farmManager().farmWorldManager().checkAllWorlds();
    }

    @Override
    public void checkSprinklers(@NotNull List<Sprinkler> sprinklers) {

    }

    @Override
    public void runTaskLater(@NotNull Runnable runnable, long delay) {
        plugin.getServer().getScheduler().runTaskLater(plugin, runnable, delay);
    }

    @Override
    public void runTaskLaterAsync(@NotNull Runnable runnable, long delay) {
        plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, runnable, delay);
    }

}