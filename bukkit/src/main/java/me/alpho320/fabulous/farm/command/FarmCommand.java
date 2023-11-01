package me.alpho320.fabulous.farm.command;

import me.alpho320.fabulous.farm.FarmPlugin;
import org.jetbrains.annotations.NotNull;

public class FarmCommand {

    private final @NotNull FarmPlugin plugin;

    public FarmCommand(@NotNull FarmPlugin plugin) {
        this.plugin = plugin;
    }

    public @NotNull FarmCommand setup() {
        return this;
    }

}