package me.alpho320.fabulous.farm.task.impl;

import me.alpho320.fabulous.core.bukkit.util.debugger.Debug;
import me.alpho320.fabulous.farm.BukkitFarmAPI;
import me.alpho320.fabulous.farm.BukkitFarmPlugin;
import me.alpho320.fabulous.farm.provider.BukkitProviderManager;
import me.alpho320.fabulous.farm.task.Task;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class BackupTask extends Task {

    public BackupTask(@NotNull BukkitFarmPlugin plugin) {
        super(plugin);
    }

    @Override
    public void run() {
        Debug.debug(0, " ");
        Debug.debug(0, " | Backup starting...");

        long now = System.currentTimeMillis();
        plugin().providerManager().provider().saveAllData(true, state -> {
            if (state) {
                Debug.debug(0, " | All data successfully saved. (" + BukkitFarmAPI.took(now) + ")");
            } else {
                Debug.debug(1, " | Failed to save all data!");
            }
        });

    }
}