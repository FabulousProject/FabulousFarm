package me.alpho320.fabulous.farm.api.event.action;

import me.alpho320.fabulous.farm.FarmPlugin;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class EventAction {

    protected final @NotNull FarmPlugin plugin;
    protected final @NotNull ConfigurationSection section;

    private final double chance;
    private boolean registered = false;

    public EventAction(@NotNull FarmPlugin plugin, @NotNull ConfigurationSection section) {
        this.plugin = plugin;
        this.section = section;
        this.chance = section.getDouble("chance", 100);
    }

    public @NotNull FarmPlugin plugin() {
        return this.plugin;
    }

    public double chance() {
        return this.chance;
    }

    public @NotNull ConfigurationSection section() {
        return this.section;
    }

    public abstract boolean register();
    public abstract void execute(@NotNull Location location, @Nullable Event event, @Nullable Entity entity, @Nullable String extraInfo);

    public void execute(@NotNull Location location, @Nullable Event event, @Nullable Entity entity) {
        execute(location, event, entity, null);
    }

    public void execute(@NotNull Location location, @Nullable Event event) {
        execute(location, event, null, null);
    }

    public void execute(@NotNull Location location) {
        execute(location, null, null, null);
    }

    public void printExecuteError(@NotNull String reason, @NotNull Event event, @Nullable Entity entity, @Nullable Location location, @Nullable String extraInfo) {
        plugin.logger().warning("EventAction | Reason: " + reason);
        plugin.logger().warning("EventAction | Path: " + section.getCurrentPath());
        plugin.logger().warning("EventAction | Event: " + event.getClass().getSimpleName());
        plugin.logger().warning("EventAction | ExtraInfo: " + extraInfo);
    }

    public boolean isRegistered() {
        return this.registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    public @Nullable Location tryToGetLocation(@Nullable Event event) {
        if (event == null) return null;
        if (event instanceof BlockBreakEvent) {
            return ((BlockBreakEvent) event).getBlock().getLocation();
        } else if (event instanceof BlockPlaceEvent) {
            return ((BlockPlaceEvent) event).getBlock().getLocation();
        } // todo - add more events (ON PLANT, ON HARVEST, ON GROW, ON FILL)
        return null;
    }

}