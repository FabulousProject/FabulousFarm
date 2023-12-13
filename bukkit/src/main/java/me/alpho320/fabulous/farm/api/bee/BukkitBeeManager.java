package me.alpho320.fabulous.farm.api.bee;

import me.alpho320.fabulous.farm.BukkitFarmPlugin;
import me.alpho320.fabulous.farm.data.ParticleData;
import org.jetbrains.annotations.NotNull;

public class BukkitBeeManager extends BeeManager {

    private final @NotNull BukkitFarmPlugin plugin;

    private boolean enabled;
    private int radius;
    private double chance;
    private int checkInterval;

    private ParticleData particleData;

    public BukkitBeeManager(@NotNull BukkitFarmPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void setup() {
        this.enabled = plugin.getConfig().getBoolean("Main.bees.enabled", false);
        if (!enabled) {
            plugin.logger().info(" | Bee system is disabled.");
            return;
        }
        plugin.logger().info(" | Loading bee system...");

        this.radius = plugin.getConfig().getInt("Main.bees.radius", 5);
        this.chance = plugin.getConfig().getDouble("Main.bees.chance", 0.5);
        this.checkInterval = plugin.getConfig().getInt("Main.bees.check-interval", 20);
        this.particleData = ParticleData.fromSection(plugin.getConfig().getConfigurationSection("Main.bees.particle"));
    }

    @Override
    public boolean enabled() {
        return this.enabled;
    }

    @Override
    public int radius() {
        return this.radius;
    }

    @Override
    public double chance() {
        return this.chance;
    }

    @Override
    public int checkInterval() {
        return this.checkInterval;
    }

    @Override
    public @NotNull ParticleData particleData() {
        return this.particleData;
    }

    @Override
    public void check() {
        // TODO: 13/12/2023
    }

}