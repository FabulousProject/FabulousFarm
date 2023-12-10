package me.alpho320.fabulous.farm.hook.impl;

import dev.lone.itemsadder.api.Events.ItemsAdderLoadDataEvent;
import me.alpho320.fabulous.farm.BukkitFarmPlugin;
import me.alpho320.fabulous.farm.Callback;
import me.alpho320.fabulous.farm.hook.Hook;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ItemsAdderHook implements Hook, Listener {

    private final @NotNull BukkitFarmPlugin plugin;

    private @Nullable Callback callback;
    private final boolean enabled;
    private boolean initialized = false;

    public ItemsAdderHook(@NotNull BukkitFarmPlugin plugin, boolean enabled) {
        this.plugin = plugin;
        this.enabled = enabled;
    }

    @Override
    public void init(@Nullable Callback callback) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.callback = callback;
    }

    @EventHandler
    public void onLoad(ItemsAdderLoadDataEvent event) {
        if (!event.getCause().equals(ItemsAdderLoadDataEvent.Cause.FIRST_LOAD)) return;

        initialized = true;
        plugin.checkCallback(callback, true);
    }

    @Override
    public @NotNull String name() {
        return "itemsadder";
    }

    @Override
    public boolean enabled() {
        return this.enabled;
    }

    @Override
    public boolean initialized() {
        return this.initialized;
    }

    @Override
    public @NotNull Hook.LoadType loadType() {
        return LoadType.SETUP_PLUGIN_AFTER;
    }

}
