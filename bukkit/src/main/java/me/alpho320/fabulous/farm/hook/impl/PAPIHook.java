package me.alpho320.fabulous.farm.hook.impl;

import me.alpho320.fabulous.farm.BukkitFarmPlugin;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PAPIHook extends PlaceholderExpansion implements Hook {

    private final @NotNull BukkitFarmPlugin plugin;
    private boolean enabled;

    public PAPIHook(@NotNull BukkitFarmPlugin plugin, boolean enabled) {
        this.plugin = plugin;
        this.enabled = enabled;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "farm";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Alpho320#9202";
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String identifier) {
        if (player == null || identifier.equals("")) return "";

        return "";
    }

    @Override
    public boolean init() {
        register();
        return true;
    }

    @Override
    public @NotNull String name() {
        return "PlaceholderAPI";
    }

    @Override
    public boolean enabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}