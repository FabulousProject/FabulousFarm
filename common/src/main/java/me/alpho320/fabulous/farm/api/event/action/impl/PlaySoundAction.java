package me.alpho320.fabulous.farm.api.event.action.impl;

import me.alpho320.fabulous.farm.FarmPlugin;
import me.alpho320.fabulous.farm.api.event.action.EventAction;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlaySoundAction extends EventAction {

    private Sound sound;
    private float volume;
    private float pitch;

    public PlaySoundAction(@NotNull FarmPlugin plugin, @NotNull ConfigurationSection section) {
        super(plugin, section);
    }

    @Override
    public boolean register() {
        try {
            this.sound = Sound.valueOf(section.getString("value", "null"));
            this.volume = (float) section.getDouble("volume", 1.0);
            this.pitch = (float) section.getDouble("pitch", 1.0);

            return true;
        } catch (IllegalArgumentException exception) {
            plugin.logger().severe("EventAction | Sound of " + section.getString("value", "null") + " not found.");
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

        finalLocation.getWorld().playSound(finalLocation, sound, volume, pitch);
    }


}