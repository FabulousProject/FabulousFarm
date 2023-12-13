package me.alpho320.fabulous.farm.hook.impl;

import dev.lone.itemsadder.api.CustomBlock;
import dev.lone.itemsadder.api.CustomFurniture;
import dev.lone.itemsadder.api.CustomStack;
import dev.lone.itemsadder.api.Events.ItemsAdderLoadDataEvent;
import me.alpho320.fabulous.farm.BukkitFarmPlugin;
import me.alpho320.fabulous.farm.Callback;
import me.alpho320.fabulous.farm.api.pot.PotHolder;
import me.alpho320.fabulous.farm.hook.Hook;
import me.alpho320.fabulous.farm.hook.type.CanChangePotModel;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ItemsAdderHook implements Hook, CanChangePotModel, Listener {

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

    @Override
    public boolean changePotModel(@NotNull PotHolder pot, @NotNull String model) {
        CustomBlock block = CustomBlock.getInstance(model);
        if (block != null) {
            block.place(pot.location().loc());
            return true;
        }

        if (CustomFurniture.isInRegistry(model)) {
            CustomFurniture.spawn(model, pot.location().loc().getBlock());
            return true;
        }

        return false;
    }

}
