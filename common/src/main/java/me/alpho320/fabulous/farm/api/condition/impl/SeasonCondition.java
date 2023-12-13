package me.alpho320.fabulous.farm.api.condition.impl;

import me.alpho320.fabulous.farm.FarmPlugin;
import me.alpho320.fabulous.farm.api.FarmManager;
import me.alpho320.fabulous.farm.api.condition.Condition;
import me.alpho320.fabulous.farm.api.crop.CropHolder;
import me.alpho320.fabulous.farm.api.greenhouse.GreenhouseHolder;
import me.alpho320.fabulous.farm.util.serializable.SerializableLocation;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SeasonCondition extends Condition {

    private @NotNull List<String> seasons = new ArrayList<>();

    public SeasonCondition(@NotNull FarmPlugin plugin, @NotNull ConfigurationSection section) {
        super(plugin, section);
    }

    @Override
    public boolean register() {
        try {
            if (!section.isList("value")) {
                plugin.logger().severe("SeasonCondition | Seasons value not found. Please check your section.");
                plugin.logger().severe("SeasonCondition | 'value' key not found.");
                plugin.logger().severe("SeasonCondition | Section: " + section);
                return false;
            }
            this.seasons = section.getStringList("value");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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

    @Override
    public boolean remove(@Nullable SerializableLocation location, @Nullable Object object, int amount) {
        return true;
    }

}