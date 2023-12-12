package me.alpho320.fabulous.farm.api.sprinkler.animation;

import me.alpho320.fabulous.farm.BukkitFarmPlugin;
import me.alpho320.fabulous.farm.FarmPlugin;
import org.jetbrains.annotations.NotNull;

public class BukkitSprinklerAnimationManager extends SprinklerAnimationManager {

    private final @NotNull BukkitFarmPlugin plugin;

    public BukkitSprinklerAnimationManager(@NotNull BukkitFarmPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void setup() {
        plugin.logger().info(" | Sprinkler animations loading...");
    }

    @Override
    public @NotNull FarmPlugin plugin() {
        return this.plugin;
    }

}