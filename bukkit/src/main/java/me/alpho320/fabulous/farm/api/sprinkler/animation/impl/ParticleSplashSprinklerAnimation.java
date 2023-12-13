package me.alpho320.fabulous.farm.api.sprinkler.animation.impl;

import me.alpho320.fabulous.farm.FarmPlugin;
import me.alpho320.fabulous.farm.api.sprinkler.Sprinkler;
import me.alpho320.fabulous.farm.api.sprinkler.SprinklerHolder;
import me.alpho320.fabulous.farm.api.sprinkler.animation.SprinklerAnimation;
import me.alpho320.fabulous.farm.data.ParticleData;
import me.alpho320.fabulous.farm.task.Task;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class ParticleSplashSprinklerAnimation extends SprinklerAnimation {

    private final @NotNull ParticleData particledata;
    private final int duration;

    public ParticleSplashSprinklerAnimation(@NotNull FarmPlugin plugin, @NotNull ConfigurationSection section) {
        super(plugin, section);
        this.particledata = ParticleData.fromSection(section.getConfigurationSection("particle"));
        this.duration = section.getInt("duration", 0);
    }

    @Override
    public void register() {
        plugin.logger().info("  | Sprinkler animation of " + section.getName() + " registered.");
        // TODO: 12/12/2023
    }

    @Override
    public void animate(@NotNull SprinklerHolder sprinkler) {
        new AnimationTimer(plugin, sprinkler).runTaskTimer((Plugin) plugin, 0L, 1L);
    }

    @Override
    public int duration() {
        return this.duration;
    }

    public class AnimationTimer extends Task {

        private final @NotNull SprinklerHolder sprinkler;
        private int elapsed = 0;

        public AnimationTimer(@NotNull FarmPlugin plugin, @NotNull SprinklerHolder sprinkler) {
            super(plugin);
            this.sprinkler = sprinkler;
        }

        @Override
        public void run() {
            final Location location = sprinkler.location();
            final Block block = location.getBlock();

            if (!block.getWorld().isChunkLoaded(location.getBlockX() >> 4, location.getBlockZ() >> 4)) {
                sprinkler.setState(Sprinkler.State.IDLE);
                cancel();
                return;
            }

            for (BlockFace face : BlockFace.values()) {
                if (face == BlockFace.UP || face == BlockFace.DOWN) continue;

                double x = 0;
                double z = 0;

                if (face == BlockFace.EAST) {
                    x = 0.5;
                } else if (face == BlockFace.WEST) {
                    x = -0.5;
                } else if (face == BlockFace.NORTH) {
                    z = -0.5;
                } else if (face == BlockFace.SOUTH) {
                    z = 0.5;
                }

                particledata.spawn(block.getRelative(face).getLocation().clone().add(x, 0, z));
            }

            elapsed++;
            if (elapsed >= duration()) {
                sprinkler.setState(Sprinkler.State.IDLE);
                cancel();
            }
        }
    }

}