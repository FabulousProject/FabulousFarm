package me.alpho320.fabulous.farm.hook;

import me.alpho320.fabulous.farm.FarmPlugin;
import me.alpho320.fabulous.farm.manager.AbstractDataManager;
import org.jetbrains.annotations.NotNull;

public abstract class HookManager extends AbstractDataManager<String, Hook> {

    public HookManager(@NotNull FarmPlugin plugin) {
        super(plugin);
    }

}