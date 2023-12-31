package me.alpho320.fabulous.farm.listener;

import me.alpho320.fabulous.core.bukkit.util.debugger.Debug;
import me.alpho320.fabulous.farm.BukkitFarmPlugin;
import me.alpho320.fabulous.farm.provider.BukkitProviderManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerQuitListener implements Listener {

    private final @NotNull BukkitFarmPlugin plugin;

    public PlayerQuitListener(@NotNull BukkitFarmPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        BukkitProviderManager.get().savePlayerData(player.getUniqueId(), true, state -> {
                if (state) Debug.debug(2, " | Data successfully saved for " + player.getName() + "-" + player.getUniqueId());
                else Debug.debug(1, " | Failed to save data for " + player.getName() + "-" + player.getUniqueId());

                BukkitProviderManager.get().map().remove(player.getUniqueId());
            }
        );

    }

}