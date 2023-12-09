package me.alpho320.fabulous.farm.workload.impl;

import me.alpho320.fabulous.core.bukkit.util.debugger.Debug;
import me.alpho320.fabulous.farm.BukkitFarmPlugin;
import me.alpho320.fabulous.farm.provider.BukkitProviderManager;
import me.alpho320.fabulous.farm.workload.Workload;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class LoadFarmWorkload implements Workload {

    private final @NotNull UUID id;
    private final @NotNull BukkitFarmPlugin plugin;

    public LoadFarmWorkload(@NotNull BukkitFarmPlugin plugin, @NotNull UUID id) {
        this.id = id;
        this.plugin = plugin;
    }

    @Override
    public void compute() {
        BukkitProviderManager.get().loadFarmData(id, false, state -> {
            if (state)
                Debug.debug(2, " | Farm " + id + " successfully loaded.");
            else
                Debug.debug(1, " | Farm " + id + " couldn't loaded." );
        });
    }

}