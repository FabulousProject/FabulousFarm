package me.alpho320.fabulous.farm.api.vermin;

import me.alpho320.fabulous.core.bukkit.util.BukkitConfiguration;
import me.alpho320.fabulous.farm.BukkitFarmAPI;
import me.alpho320.fabulous.farm.BukkitFarmPlugin;
import me.alpho320.fabulous.farm.FarmPlugin;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Arrays;

public class BukkitVerminManager extends VerminManager {

    private final @NotNull BukkitFarmPlugin plugin;

    public BukkitVerminManager(@NotNull BukkitFarmPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void setup() {
        BukkitConfiguration config = plugin.getConfig();
        if (config.getBoolean("Main.vermin.enabled", false)) {
            plugin.logger().warning(" | Vermins are disabled.");
            return;
        }
        BukkitFarmAPI.extractDefaultFolderFilesFromJarIfNoExist(plugin, "/vermins/");

        File[] folder = new File(plugin.getDataFolder(), "/vermins/").listFiles();
        if (folder != null) {
            for (File file : folder) {
                if (file.getName().endsWith(".yml")) {
                    final String id = file.getName().replace(".yml", "");
                    plugin.logger().info("  | Vermin of " + id + " loading...");

                    BukkitConfiguration configuration = new BukkitConfiguration("vermins/" + id, plugin);
                    if (!configuration.isConfigurationSection("Vermin")) {
                        plugin.logger().warning("  | Vermin of " + id + " has no 'Vermin' section, please check your file.");
                        continue;
                    }

                    Vermin vermin = fromSection(id, configuration.getConfigurationSection("Vermin"));
                    if (vermin == null) continue;

                    register(id, vermin);
                    plugin.logger().info(" | Vermin of " + id + " loaded.");
                }
            }
            plugin.logger().info(" | Amount of " + map().size() + " vermins loaded. (" + Arrays.toString(map().keySet().toArray()) + ")");
        }

    }

    @Override
    public @Nullable Vermin fromSection(String id, ConfigurationSection section) {
        if (!BukkitFarmAPI.validateConfigurationKeyIsSet(plugin, section, "name", "  | ")) return null;
        String name = section.getString("name", "");

        if (!BukkitFarmAPI.validateConfigurationKeyIsSet(plugin, section, "entity", "  | ")) return null;
        String entity = section.getString("entity", "");

        return new Vermin(
                id,
                name,
                entity,
                section.getDouble("chance", 100.0)
        );
    }

    @Override
    public @NotNull FarmPlugin plugin() {
        return this.plugin;
    }

}