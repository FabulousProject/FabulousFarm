package me.alpho320.fabulous.farm.listener;

import dev.lone.itemsadder.api.Events.ItemsAdderLoadDataEvent;
import me.alpho320.fabulous.core.bukkit.util.debugger.Debug;
import me.alpho320.fabulous.farm.FarmAPI;
import me.alpho320.fabulous.farm.BukkitFarmPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class ItemsAdderLoadListener implements Listener {

    private final @NotNull BukkitFarmPlugin plugin;
    private final long now;

    public ItemsAdderLoadListener(@NotNull BukkitFarmPlugin plugin, long now) {
        this.plugin = plugin;
        this.now = now;
    }

    @EventHandler
    public void onLoad(ItemsAdderLoadDataEvent event) {
        if (event.getCause().equals(ItemsAdderLoadDataEvent.Cause.FIRST_LOAD)) {
            FarmAPI.init(plugin, state -> {
                if (state) {

                    Hooks.get("PlaceholderAPI").setEnabled(plugin.getConfig().getBoolean("Hooks.placeholderapi", true));
                    Hooks.get("MythicMobs").setEnabled(plugin.getConfig().getBoolean("Hooks.mythicmobs", false));

                    Hooks.initAll(plugin);

                    Debug.debug(0, "");
                    Debug.debug(0, " | Successfully loaded! (" + FarmAPI.took(now) + ")");

                    Debug.debug(0, "");
                    Debug.debug(0, "============ FabulousFarmPlugin ============");
                    Debug.debug(0, "");
                    Debug.debug(0, "FabulousFarmPlugin Active!");
                    Debug.debug(0, "Version: " + plugin.getDescription().getVersion());
                    Debug.debug(0, "Developer: Alpho320#9202");
                    Debug.debug(0, "");
                    Debug.debug(0, "============ FabulousFarmPlugin ============");
                    Debug.debug(0, "");

                    plugin.setLoaded(true);
                } else {
                    Debug.debug(1, " | Failed to load! Please check the console.");
                    plugin.getServer().getPluginManager().disablePlugin(plugin);
                }
            });

        }
    }

}