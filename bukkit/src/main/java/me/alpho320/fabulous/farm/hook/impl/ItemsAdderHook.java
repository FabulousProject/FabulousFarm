package me.alpho320.fabulous.farm.hook.impl;

import dev.lone.itemsadder.api.CustomBlock;
import dev.lone.itemsadder.api.CustomFurniture;
import dev.lone.itemsadder.api.CustomStack;
import dev.lone.itemsadder.api.Events.ItemsAdderLoadDataEvent;
import me.alpho320.fabulous.farm.BukkitFarmPlugin;
import me.alpho320.fabulous.farm.Callback;
import me.alpho320.fabulous.farm.api.crop.CropHolder;
import me.alpho320.fabulous.farm.api.pot.PotHolder;
import me.alpho320.fabulous.farm.hook.Hook;
import me.alpho320.fabulous.farm.hook.type.CanChangeCropModel;
import me.alpho320.fabulous.farm.hook.type.CanChangePotModel;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class ItemsAdderHook implements Hook, CanChangePotModel, CanChangeCropModel, Listener {

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
        return change(pot.location().loc(), model);
    }

    @Override
    public boolean removePotModel(@NotNull PotHolder pot) {
        return remove(pot.location().loc());
    }

    @Override
    public boolean changeCropModel(@NotNull CropHolder crop, @NotNull String model) {
        return change(crop.location().loc(), model);
    }

    @Override
    public boolean removeCropModel(@NotNull CropHolder crop) {
        return remove(crop.location().loc());
    }

    private boolean change(@NotNull Location location, String model) {
        final CustomBlock block = CustomBlock.getInstance(model);

        if (block != null) {
            remove(location);
            block.place(location);
            return true;
        }

        if (CustomFurniture.isInRegistry(model)) {
            remove(location);
            CustomFurniture.spawn(model, location.getBlock());
            return true;
        }

        return false;
    }

    private boolean remove(@NotNull Location location) {
        if (CustomBlock.remove(location)) return true;

        Collection<ItemFrame> list = location.clone().add(0.5, 0.5, 0.5).getNearbyEntitiesByType(ItemFrame.class, 0.4, 0.5, 0.4);
        if (list.isEmpty()) return false;

        list.forEach(Entity::remove);
        return true;
    }

}
