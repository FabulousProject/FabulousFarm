package me.alpho320.fabulous.farm.listener;

import me.alpho320.fabulous.core.bukkit.util.debugger.Debug;
import me.alpho320.fabulous.farm.FarmPlugin;
import me.alpho320.fabulous.farm.provider.ProviderManager;
import me.alpho320.fabulous.farm.util.ItemCreatorUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerJoinListener implements Listener {

    private final @NotNull FarmPlugin plugin;

    public PlayerJoinListener(@NotNull FarmPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        ProviderManager.get().loadPlayerData(player.getUniqueId(), true, state -> {
            if (state) Debug.debug(2, " | Data successfully  loaded for " + player.getName() + "-" + player.getUniqueId());
            else Debug.debug(1, " | Failed to load data for " + player.getName() + "-" + player.getUniqueId());

            ItemCreatorUtil.itemWithUuid(player.getUniqueId());
        });
    }

}