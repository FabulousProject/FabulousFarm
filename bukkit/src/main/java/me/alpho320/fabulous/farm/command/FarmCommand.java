package me.alpho320.fabulous.farm.command;

import me.alpho320.fabulous.farm.BukkitFarmPlugin;
import org.jetbrains.annotations.NotNull;

public class FarmCommand {

    private final @NotNull BukkitFarmPlugin plugin;

    public FarmCommand(@NotNull BukkitFarmPlugin plugin) {
        this.plugin = plugin;
    }

    public @NotNull FarmCommand setup() {
        return this;
    }

}