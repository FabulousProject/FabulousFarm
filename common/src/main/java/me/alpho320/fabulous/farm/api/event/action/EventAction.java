package me.alpho320.fabulous.farm.api.event.action;

import me.alpho320.fabulous.farm.FarmPlugin;
import me.alpho320.fabulous.farm.api.event.EventType;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class EventAction {

    protected final @NotNull FarmPlugin plugin;
    protected final @NotNull EventType eventType;

    protected final @NotNull ConfigurationSection section;
    private boolean registered = false;

    public EventAction(@NotNull FarmPlugin plugin, @NotNull EventType eventType, @NotNull ConfigurationSection section) {
        this.plugin = plugin;
        this.eventType = eventType;
        this.section = section;
    }

    public @NotNull FarmPlugin plugin() {
        return this.plugin;
    }

    public @NotNull EventType eventType() {
        return this.eventType;
    }

    public @NotNull ConfigurationSection section() {
        return this.section;
    }

    public abstract boolean register();
    public abstract void execute(@NotNull Event event, @Nullable Entity entity, @Nullable Location location, @Nullable String extraInfo);

    public void execute(@NotNull Event event, @Nullable Entity entity, @Nullable Location location) {
        execute(event, entity, location, null);
    }

    public void execute(@NotNull Event event, @Nullable Entity entity) {
        execute(event, entity, null, null);
    }

    public void execute(@NotNull Event event) {
        execute(event, null, null, null);
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

}