package me.alpho320.fabulous.farm.api.event.action.impl;

import me.alpho320.fabulous.farm.FarmPlugin;
import me.alpho320.fabulous.farm.api.event.action.EventAction;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ReplantAction extends EventAction {

    private String cropId;

    public ReplantAction(@NotNull FarmPlugin plugin, @NotNull ConfigurationSection section) {
        super(plugin, section);
    }

    @Override
    public boolean register() {
        try {
            if (section.isSet("crop")) {
                this.cropId = section.getString("crop", "");
                return true;
            } else {
                plugin.logger().severe("ReplantAction | Crop id not found. Please check your section.");
                plugin.logger().severe("ReplantAction | 'crop' key not found.");
                plugin.logger().severe("ReplantAction | Section: " + section);
            }
        } catch (Exception e) {
            plugin.logger().severe("ReplantAction | An error accorded while registering. (" + e.getMessage() + ")");
            plugin.logger().severe("EventAction | Section: " + section);
        }
        return false;
    }

    @Override
    public void execute(@NotNull Location location, @Nullable Event event, @Nullable Entity entity, @Nullable String extraInfo) {
        Location finalLocation = location.clone();

        if (entity != null) {
            finalLocation = entity.getLocation().clone();
        } else if (tryToGetLocation(event) != null) {
            finalLocation = tryToGetLocation(event).clone();
        }

        plugin.farmManager().cropManager().plant(cropId, finalLocation);
    }

}