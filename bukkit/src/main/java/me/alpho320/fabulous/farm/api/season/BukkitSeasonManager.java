package me.alpho320.fabulous.farm.api.season;

import me.alpho320.fabulous.core.bukkit.util.BukkitConfiguration;
import me.alpho320.fabulous.farm.BukkitFarmAPI;
import me.alpho320.fabulous.farm.BukkitFarmPlugin;
import me.alpho320.fabulous.farm.FarmPlugin;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class BukkitSeasonManager extends SeasonManager {

    private final @NotNull BukkitFarmPlugin plugin;

    public BukkitSeasonManager(@NotNull BukkitFarmPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void setup() {
        BukkitConfiguration config = plugin.getConfig();
        if (!config.getBoolean("Main.seasons.enabled", false)) {
            plugin.logger().warning(" | Seasons are disabled.");
            return;
        }

        if (!config.isConfigurationSection("Main.seasons.list")) {
            plugin.logger().severe(" | Seasons enabled but no seasons found.");
            plugin.logger().severe(" | Please add some season or disable the feature.");
            plugin.logger().severe(" | Available seasons: " + Arrays.toString(map().keySet().toArray()));
            plugin.logger().severe(" | Disabling seasons feature...");
            setEnabled(false);
            return;
        }

        ConfigurationSection baseSection = config.getConfigurationSection("Main.seasons.list");
        for (String id : baseSection.getKeys(false)) {
            ConfigurationSection section = baseSection.getConfigurationSection(id);
            Season season = fromSection(id, section);

            if (season == null) continue;
            register(id, season);
        }

        Season seasonOne = map().values().stream().filter(season -> season.order() == 1).findFirst().orElse(null);
        if (seasonOne == null) {
            plugin.logger().severe(" | Seasons enabled but no season found with order 1.");
            plugin.logger().severe(" | Please add a season with order 1 or disable the feature.");
            plugin.logger().severe(" | Available seasons: " + Arrays.toString(map().keySet().toArray()));
            plugin.logger().severe(" | Disabling seasons feature...");
            setEnabled(false);
            return;
        }

        Season lastSeason = plugin.providerManager().provider().lastSeason();
        if (lastSeason != null) {
            setCurrentSeason(lastSeason);
        } else {
            setCurrentSeason(seasonOne);
            currentSeason().setActive(true);
        }

        plugin.logger().info(" | Seasons loaded. (Current: " + currentSeason().id() + ")");
    }

    @Override
    public @Nullable Season fromSection(String id, ConfigurationSection section) {
        plugin.logger().info("  | Loading season: " + id);

        if (!BukkitFarmAPI.validateConfigurationKeyIsSet(plugin, section, "order", "  | ")) return null;
        int order = section.getInt("order", 0);

        if (!BukkitFarmAPI.validateConfigurationKeyIsSet(plugin, section, "days", "  | ")) return null;
        int days = section.getInt("days", 0);

        return new Season(id, false, order, days);
    }

    @Override
    public @NotNull FarmPlugin plugin() {
        return this.plugin;
    }

}