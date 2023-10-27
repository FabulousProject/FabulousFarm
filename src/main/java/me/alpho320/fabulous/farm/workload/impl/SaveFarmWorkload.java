package me.alpho320.fabulous.farm.workload.impl;

import me.alpho320.fabulous.farm.FarmPlugin;
import me.alpho320.fabulous.farm.log.LogHandler;
import me.alpho320.fabulous.farm.provider.ProviderManager;
import me.alpho320.fabulous.farm.workload.Workload;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class SaveFarmWorkload implements Workload {

    private final @NotNull UUID id;
    private final @NotNull FarmPlugin plugin;

    public SaveFarmWorkload(@NotNull FarmPlugin plugin, @NotNull UUID id) {
        this.id = id;
        this.plugin = plugin;
    }

    @Override
    public void compute() {
        ProviderManager.get().saveFarmData(id, false, state -> {
            if (state)
                LogHandler.log(true, " | Farm  " + id + " successfully saved." );
            else
                LogHandler.log(true, " | Farm " + id + " couldn't saved." );
        });
    }

}