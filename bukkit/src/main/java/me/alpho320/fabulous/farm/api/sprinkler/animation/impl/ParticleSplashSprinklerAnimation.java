package me.alpho320.fabulous.farm.api.sprinkler.animation.impl;

import me.alpho320.fabulous.farm.FarmPlugin;
import me.alpho320.fabulous.farm.api.sprinkler.SprinklerHolder;
import me.alpho320.fabulous.farm.api.sprinkler.animation.SprinklerAnimation;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

public class ParticleSplashSprinklerAnimation extends SprinklerAnimation {

    public ParticleSplashSprinklerAnimation(@NotNull FarmPlugin plugin, @NotNull ConfigurationSection section) {
        super(plugin, section);
    }

    @Override
    public void register() {
        plugin.logger().info("  | Sprinkler animation of " + section.getName() + " registering.");
        // TODO: 12/12/2023
    }

    @Override
    public void animate(@NotNull SprinklerHolder sprinkler) {

    }

}