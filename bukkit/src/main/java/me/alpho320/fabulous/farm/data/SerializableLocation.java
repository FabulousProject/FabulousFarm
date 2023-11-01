package me.alpho320.fabulous.farm.data;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class SerializableLocation implements Serializable {

    private final String world;
    private final double x;
    private final double y;
    private final double z;
    private final float pitch;
    private final float yaw;


    public SerializableLocation(@NotNull Location location) {
        this.world = location.getWorld().getName();
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.pitch = location.getPitch();
        this.yaw = location.getYaw();
    }

    public SerializableLocation(World world, double x, double y, double z) {
        this(new Location(world, x, y, z));
    }

    @Override
    public SerializableLocation clone() {
        World w = Bukkit.getWorld(world);
        if (w == null) {
            w = Bukkit.getWorld("world");
        }
        return new SerializableLocation(w, x, y, z);
    }

    public Location loc() {
        World w = Bukkit.getWorld(world);
        if (w == null) {
            w = Bukkit.getWorld("world");
        }
        return new Location(w, x, y, z, yaw, pitch);
    }

    public String world() {
        return this.world;
    }

    public double x() {
        return this.x;
    }

    public double y() {
        return this.y;
    }

    public double z() {
        return this.z;
    }

    public float pitch() {
        return this.pitch;
    }

    public float yaw() {
        return this.yaw;
    }
}