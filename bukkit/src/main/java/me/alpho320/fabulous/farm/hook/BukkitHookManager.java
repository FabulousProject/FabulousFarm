package me.alpho320.fabulous.farm.hook;

import me.alpho320.fabulous.core.bukkit.util.BukkitConfiguration;
import me.alpho320.fabulous.farm.BukkitFarmPlugin;
import me.alpho320.fabulous.farm.Callback;
import me.alpho320.fabulous.farm.hook.impl.ItemsAdderHook;
import me.alpho320.fabulous.farm.hook.impl.PAPIHook;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BukkitHookManager extends HookManager {

    private final @NotNull BukkitFarmPlugin plugin;

    public BukkitHookManager(@NotNull BukkitFarmPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void init(@Nullable Callback callback) {
        plugin.logger().info(" | Registering hooks...");
        BukkitConfiguration config = plugin.getConfig();

        register("placeholderapi", new PAPIHook(plugin, config.getBoolean("Hooks.placeholderapi", true)));
        register("itemsadder", new ItemsAdderHook(plugin, config.getBoolean("Hooks.itemsadder", false)));
        // todo: call HookInitEvent.

        plugin.logger().info(" | Amount of " + map().size() + " hooks registered.");

        for (Hook hook : map().values()) {
            if (!hook.enabled()) continue;
            if (hook.loadType().equals(Hook.LoadType.SETUP_PLUGIN_AFTER)) {
                hook.init(c -> {
                    if (c && isAllHooksInitialized()) {
                        plugin.checkCallback(callback, true);
                    }
                });
            } else {
                hook.init(null);
            }
        }

        if (!isAnyHookLoadTypeSetupPluginAfter())
            plugin.checkCallback(callback, true);
    }

    public boolean isAllHooksInitialized() {
        return map().values().stream().allMatch(Hook::initialized);
    }

    private boolean isAnyHookLoadTypeSetupPluginAfter() {
        return map().values().stream().anyMatch(hook -> hook.enabled() && hook.loadType().equals(Hook.LoadType.SETUP_PLUGIN_AFTER));
    }

}