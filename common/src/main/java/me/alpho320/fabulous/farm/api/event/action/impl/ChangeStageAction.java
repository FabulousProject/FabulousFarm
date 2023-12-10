package me.alpho320.fabulous.farm.api.event.action.impl;

import me.alpho320.fabulous.farm.FarmPlugin;
import me.alpho320.fabulous.farm.api.crop.CropHolder;
import me.alpho320.fabulous.farm.api.event.EventType;
import me.alpho320.fabulous.farm.api.event.action.EventAction;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ChangeStageAction extends EventAction {

    private int stage;

    public ChangeStageAction(@NotNull FarmPlugin plugin, @NotNull EventType eventType, @NotNull ConfigurationSection section) {
        super(plugin, eventType, section);
    }

    @Override
    public boolean register() {
        try {
            if (section.isSet("value")) {
                this.stage = section.getInt("value", 0);
                return true;
            } else {
                plugin.logger().severe("ChangeStageAction | Stage value not found for " + eventType.type() + " event. Please check your section.");
                plugin.logger().severe("ChangeStageAction | 'value' key not found.");
                plugin.logger().severe("ChangeStageAction | Section: " + section);
            }
        } catch (Exception e) {
            plugin.logger().severe("ChangeStageAction | An error accorded while registering. (" + e.getMessage() + ")");
            plugin.logger().severe("EventAction | Section: " + section);
            plugin.logger().severe("EventAction | EventType: " + eventType);
        }
        return false;
    }

    @Override
    public void execute(@NotNull Event event, @Nullable Entity entity, @Nullable Location location, @Nullable String extraInfo) {
        CropHolder cropHolder = null;
        if (location != null) {
            cropHolder = plugin.farmManager().cropManager().findHolder(location);
        } else {
            Location loc = tryToGetLocation(event);
            if (loc != null) cropHolder = plugin.farmManager().cropManager().findHolder(loc);
        }

        if (cropHolder != null) {
            cropHolder.setGrowStage(stage); //todo: call event, checks, etc.
        }

    }

}