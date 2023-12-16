package me.alpho320.fabulous.farm.api.fertilizer;

import me.alpho320.fabulous.farm.FarmPlugin;
import me.alpho320.fabulous.farm.api.crop.CropHolder;
import me.alpho320.fabulous.farm.api.event.EventType;
import me.alpho320.fabulous.farm.api.event.action.EventAction;
import me.alpho320.fabulous.farm.api.mode.Mode;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Fertilizer {

    protected final @NotNull String id;

    protected final @NotNull FarmPlugin plugin;
    protected final @NotNull ConfigurationSection section;

    public Fertilizer(@NotNull String id, @NotNull FarmPlugin plugin, @NotNull ConfigurationSection section) {
        this.id = id;
        this.plugin = plugin;
        this.section = section;
    }

    public @NotNull String id() {
        return this.id;
    }

    public abstract @NotNull String name();
    public abstract @NotNull ItemStack item();
    public abstract double chance();
    public abstract @NotNull Map<EventType, List<EventAction>> events();
    public abstract @NotNull Mode potMode();

    public boolean isPotUseable(@NotNull String potId) {
        return potMode().check(potId);
    }

    public abstract boolean register();

    public abstract boolean onGrow(@NotNull FertilizerHolder fertilizer, @NotNull CropHolder crop);
    public abstract boolean onHarvest(@NotNull FertilizerHolder fertilizer, @NotNull CropHolder crop);



}