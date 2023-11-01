package me.alpho320.fabulous.farm.listener;

import me.alpho320.fabulous.core.bukkit.util.debugger.Debug;
import me.alpho320.fabulous.farm.FarmPlugin;
import me.alpho320.fabulous.farm.provider.ProviderManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerQuitListener implements Listener {

    private final @NotNull FarmPlugin plugin;

    public PlayerQuitListener(@NotNull FarmPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        ProviderManager.get().savePlayerData(player.getUniqueId(), true, state -> {
                if (state) Debug.debug(2, " | Data successfully saved for " + player.getName() + "-" + player.getUniqueId());
                else Debug.debug(1, " | Failed to save data for " + player.getName() + "-" + player.getUniqueId());

                ProviderManager.get().map().remove(player.getUniqueId());
            }
        );

    }

}