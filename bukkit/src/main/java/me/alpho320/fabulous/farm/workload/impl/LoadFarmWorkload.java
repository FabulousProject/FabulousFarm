package me.alpho320.fabulous.farm.workload.impl;

import me.alpho320.fabulous.core.bukkit.util.debugger.Debug;
import me.alpho320.fabulous.farm.FarmPlugin;
import me.alpho320.fabulous.farm.provider.ProviderManager;
import me.alpho320.fabulous.farm.workload.Workload;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class LoadFarmWorkload implements Workload {

    private final @NotNull UUID id;
    private final @NotNull FarmPlugin plugin;

    public LoadFarmWorkload(@NotNull FarmPlugin plugin, @NotNull UUID id) {
        this.id = id;
        this.plugin = plugin;
    }

    @Override
    public void compute() {
        ProviderManager.get().loadFarmData(id, false, state -> {
            if (state)
                Debug.debug(2, " | Farm " + id + " successfully loaded.");
            else
                Debug.debug(1, " | Farm " + id + " couldn't loaded." );
        });
    }

}