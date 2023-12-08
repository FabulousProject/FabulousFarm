package me.alpho320.fabulous.farm.api.event.action.impl;

import me.alpho320.fabulous.farm.FarmPlugin;
import me.alpho320.fabulous.farm.api.event.EventType;
import me.alpho320.fabulous.farm.api.event.action.EventAction;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ReplantAction extends EventAction {

    private String cropId;

    public ReplantAction(@NotNull FarmPlugin plugin, @NotNull EventType eventType, @NotNull ConfigurationSection section) {
        super(plugin, eventType, section);
    }

    @Override
    public boolean register() {
        try {
            if (section.isSet("crop")) {
                this.cropId = section.getString("crop", "");
                return true;
            } else {
                plugin.logger().severe("ReplantAction | Crop id not found for " + eventType.type() + " event. Please check your section.");
                plugin.logger().severe("ReplantAction | 'crop' key not found.");
                plugin.logger().severe("ReplantAction | Section: " + section);
            }
        } catch (Exception e) {
            plugin.logger().severe("ReplantAction | An error accorded while registering. (" + e.getMessage() + ")");
            plugin.logger().severe("EventAction | Section: " + section);
            plugin.logger().severe("EventAction | EventType: " + eventType);
        }
        return false;
    }

    @Override
    public void execute(@NotNull Event event, @Nullable Entity entity, @Nullable Location location, @Nullable String extraInfo) {
        Location cropLocation = null;

        if (location != null) {
            cropLocation = location.clone();
        } else {
            Location loc = tryToGetLocation(event);
            if (loc != null) cropLocation = loc.clone();
        }

        if (cropLocation != null) {
            plugin.farmManager().cropManager().plant(cropId, cropLocation);
        }
    }

}