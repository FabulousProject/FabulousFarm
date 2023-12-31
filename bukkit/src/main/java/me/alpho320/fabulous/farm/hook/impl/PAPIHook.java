package me.alpho320.fabulous.farm.hook.impl;

import me.alpho320.fabulous.farm.BukkitFarmPlugin;
import me.alpho320.fabulous.farm.Callback;
import me.alpho320.fabulous.farm.hook.Hook;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PAPIHook extends PlaceholderExpansion implements Hook {

    private final @NotNull BukkitFarmPlugin plugin;

    private final boolean enabled;
    private boolean initialized = false;

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
    public void init(@Nullable Callback callback) {
        register();
        initialized = true;
    }

    @Override
    public @NotNull String name() {
        return "placeholderapi";
    }

    @Override
    public @NotNull Hook.LoadType loadType() {
        return Hook.LoadType.ON_ENABLE;
    }

    @Override
    public boolean initialized() {
        return this.initialized;
    }

    @Override
    public boolean enabled() {
        return enabled;
    }

}