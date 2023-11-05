package me.alpho320.fabulous.farm.api.event.action.impl;

import me.alpho320.fabulous.farm.FarmPlugin;
import me.alpho320.fabulous.farm.api.event.EventType;
import me.alpho320.fabulous.farm.api.event.action.EventAction;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayEffectAction extends EventAction {

    private Effect effect;

    public PlayEffectAction(@NotNull FarmPlugin plugin, @NotNull EventType eventType, @NotNull ConfigurationSection section) {
        super(plugin, eventType, section);
    }

    @Override
    public boolean register() {
        try {
            this.effect = Effect.valueOf(section.getString("value", "null"));
            return true;
        } catch (IllegalArgumentException e) {
            plugin.logger().severe("EventAction | Effect of " + section.getString("value", "null") + " not found.");
            plugin.logger().severe("EventAction | Path: " + section.getCurrentPath());
            return false;
        }
    }

    @Override
    public void execute(@NotNull Event event, @Nullable Entity entity, @Nullable Location location, @Nullable String extraInfo) {
        Location finalLocation = null;

        if (location != null) {
            finalLocation = location.clone();
        } else if (entity != null) {
            finalLocation = entity.getLocation().clone();
        } else if (event instanceof BlockBreakEvent) {
            finalLocation = ((BlockBreakEvent) event).getBlock().getLocation().clone();
        } else if (event instanceof BlockPlaceEvent) {
            finalLocation = ((BlockPlaceEvent) event).getBlock().getLocation().clone();
        } else { // todo - add more events (ON PLANT, ON HARVEST, ON GROW, ON FILL)
            printExecuteError("Location not found for " + eventType.type() + " event.", event, entity, location, extraInfo);
            return;
        }

        finalLocation.getWorld().playEffect(finalLocation, effect, 0);
    }


}