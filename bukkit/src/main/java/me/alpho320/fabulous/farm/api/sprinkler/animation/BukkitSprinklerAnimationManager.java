package me.alpho320.fabulous.farm.api.sprinkler.animation;

import me.alpho320.fabulous.farm.BukkitFarmPlugin;
import me.alpho320.fabulous.farm.FarmPlugin;
import me.alpho320.fabulous.farm.api.sprinkler.animation.impl.ParticleSplashSprinklerAnimation;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class BukkitSprinklerAnimationManager extends SprinklerAnimationManager {

    private final @NotNull BukkitFarmPlugin plugin;

    public BukkitSprinklerAnimationManager(@NotNull BukkitFarmPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void setup() {
        plugin.logger().info(" | Sprinkler animations loading...");

        register("particle-splash", ParticleSplashSprinklerAnimation.class);
        // TODO: 13/12/2023 call SprinklerAnimationRegisterEvent

        plugin.logger().info(" | Amount of " + map().size() + " sprinkler animations loaded. (" + Arrays.toString(map().keySet().toArray()) + ")");
    }

    @Override
    public @NotNull FarmPlugin plugin() {
        return this.plugin;
    }

}