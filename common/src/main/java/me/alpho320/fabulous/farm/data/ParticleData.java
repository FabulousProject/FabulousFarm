package me.alpho320.fabulous.farm.data;

import com.destroystokyo.paper.ParticleBuilder;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.jetbrains.annotations.NotNull;

public class ParticleData {

    private final @NotNull Particle particle;
    private final @NotNull Color color;

    private final int colorSize;
    private final int count;

    private final double offsetX;
    private final double offsetY;
    private final double offsetZ;

    public ParticleData(@NotNull Particle particle, @NotNull Color color, int colorSize, int count, double offsetX, double offsetY, double offsetZ) {
        this.particle = particle;
        this.color = color;
        this.colorSize = colorSize;
        this.count = count;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
    }

    public void spawn(@NotNull Location location) {
        new ParticleBuilder(particle)
                .count(count)
                .extra(colorSize)
                .color(color)
                .offset(offsetX, offsetY, offsetZ)
                .location(location)
                .allPlayers()
                .spawn();
    }

}