package me.alpho320.fabulous.farm.api.event.action.impl;

import me.alpho320.fabulous.farm.FarmPlugin;
import me.alpho320.fabulous.farm.api.event.action.EventAction;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayEffectAction extends EventAction {

    private Effect effect;

    public PlayEffectAction(@NotNull FarmPlugin plugin, @NotNull ConfigurationSection section) {
        super(plugin, section);
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
    public void execute(@NotNull Location location, @Nullable Event event, @Nullable Entity entity, @Nullable String extraInfo) {
        Location finalLocation = location.clone();

        if (entity != null) {
            finalLocation = entity.getLocation().clone();
        } else if (tryToGetLocation(event) != null) {
            finalLocation = tryToGetLocation(event).clone();
        }

        finalLocation.getWorld().playEffect(finalLocation, effect, 0);
    }

}