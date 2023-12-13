package me.alpho320.fabulous.farm.api.event.action.impl;

import me.alpho320.fabulous.farm.FarmPlugin;
import me.alpho320.fabulous.farm.api.event.action.EventAction;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DropExpAction extends EventAction {

    private int amount = 0;

    public DropExpAction(@NotNull FarmPlugin plugin, @NotNull ConfigurationSection section) {
        super(plugin, section);
    }

    @Override
    public boolean register() {
        try {
            if (section.isSet("value")) {
                this.amount = section.getInt("value", 0);
                return true;
            } else {
                plugin.logger().severe("DropExpAction | Stage value not found. Please check your section.");
                plugin.logger().severe("DropExpAction | 'value' key not found.");
                plugin.logger().severe("DropExpAction | Section: " + section);
            }
        } catch (Exception e) {
            plugin.logger().severe("DropExpAction | An error accorded while registering. (" + e.getMessage() + ")");
            plugin.logger().severe("EventAction | Section: " + section);
        }
        
        return false;
    }

    @Override
    public void execute(@NotNull Location location, @Nullable Event event, @Nullable Entity entity, @Nullable String extraInfo) {
        location.clone().getWorld().spawn(location, ExperienceOrb.class).setExperience(amount);
    }

}