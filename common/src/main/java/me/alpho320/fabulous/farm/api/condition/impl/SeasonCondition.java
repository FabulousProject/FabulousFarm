package me.alpho320.fabulous.farm.api.condition.impl;

import me.alpho320.fabulous.farm.FarmPlugin;
import me.alpho320.fabulous.farm.api.FarmManager;
import me.alpho320.fabulous.farm.api.condition.Condition;
import me.alpho320.fabulous.farm.api.crop.CropHolder;
import me.alpho320.fabulous.farm.api.greenhouse.GreenhouseHolder;
import me.alpho320.fabulous.farm.util.serializable.SerializableLocation;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SeasonCondition extends Condition {

    private final @NotNull List<String> seasons;

    public SeasonCondition(@NotNull FarmPlugin plugin, @NotNull List<String> seasons) {
        super(plugin);
        this.seasons = seasons;
    }

    @Override
    public boolean check(@NotNull SerializableLocation location) {
        final FarmManager farmManager = plugin.farmManager();
        if (seasons.contains(farmManager.seasonManager().currentSeason().id())) return true;

        final CropHolder cropHolder = farmManager.cropManager().findHolder(location);
        if (cropHolder == null) return false;

        GreenhouseHolder greenhouseHolder = farmManager.greenhouseManager().findHolder(cropHolder);
        if (greenhouseHolder == null) return false;

        return farmManager.greenhouseManager().canEffect(greenhouseHolder, cropHolder);
    }

}