package me.alpho320.fabulous.farm.api.world;

import me.alpho320.fabulous.farm.BukkitFarmPlugin;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;
import java.util.Map;

public class BukkitFarmWorldManager extends FarmWorldManager {

    private final @NotNull BukkitFarmPlugin plugin;

    public BukkitFarmWorldManager(@NotNull BukkitFarmPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void setup() {
        plugin.logger().info(" | World Manager Starting...");
        for (World world : plugin.getServer().getWorlds()) {
            plugin.logger().info("  | World of " + world.getName() + " registering.");
            register(world.getName(), new FarmWorld(plugin, world.getName(), new WeakReference<>(world), true));
        }
        plugin.logger().info(" | Amount of " + map().size() + " worlds registered.");
    }

    @Override
    public void checkAllWorlds() {
        for (Map.Entry<String, FarmWorld> entry : map().entrySet()) {
            FarmWorld world = entry.getValue();
            if (world.world() == null && !validateWorld(world)) continue;

            world.checks();
        }
    }

    public boolean validateWorld(FarmWorld farmWorld) {
        if (farmWorld.world() != null) return true;
        plugin.logger().info("  | World of " + farmWorld.id() + " is deleted? Trying to find again.");

        World world = plugin.getServer().getWorld(farmWorld.id());
        if (world != null) {
            plugin.logger().info("  | World of " + farmWorld.id() + " found again.");
            farmWorld.setWorld(new WeakReference<>(world));
            return true;
        } else {
            plugin.logger().info("  | World of " + farmWorld.id() + " not found again. Removing from list.");
            unregister(farmWorld.id());
            return false;
        }

    }

}