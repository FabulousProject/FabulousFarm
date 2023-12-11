package me.alpho320.fabulous.farm;

import me.alpho320.fabulous.farm.api.FarmManager;
import org.jetbrains.annotations.NotNull;

public class BukkitFarmManager extends FarmManager {

    private final @NotNull BukkitFarmPlugin plugin;

    public BukkitFarmManager(@NotNull BukkitFarmPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void init(@NotNull FarmPlugin plugin) {
    }

}