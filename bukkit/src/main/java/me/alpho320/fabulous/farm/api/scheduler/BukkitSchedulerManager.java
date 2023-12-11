package me.alpho320.fabulous.farm.api.scheduler;

import me.alpho320.fabulous.farm.BukkitFarmPlugin;
import me.alpho320.fabulous.farm.api.crop.CropHolder;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class BukkitSchedulerManager extends SchedulerManager {

    private final @NotNull BukkitFarmPlugin plugin;

    private boolean enabled = false;
    private String time = "07:00";

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
    }

    @Override
    public @NotNull String time() {
        return this.time;
    }

    @Override
    public void check() {
        for (World world : plugin.getServer().getWorlds()) {
            for (CropHolder crop : plugin.farmManager().cropManager().cropHoldersByWorld(world)) {

            }
        }
    }

}