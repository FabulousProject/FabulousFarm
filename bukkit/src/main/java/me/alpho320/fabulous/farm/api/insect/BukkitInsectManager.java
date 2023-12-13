package me.alpho320.fabulous.farm.api.insect;

import me.alpho320.fabulous.core.bukkit.util.BukkitConfiguration;
import me.alpho320.fabulous.farm.BukkitFarmAPI;
import me.alpho320.fabulous.farm.BukkitFarmPlugin;
import me.alpho320.fabulous.farm.FarmPlugin;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Arrays;

public class BukkitInsectManager extends InsectManager {

    private final @NotNull BukkitFarmPlugin plugin;

    public BukkitInsectManager(@NotNull BukkitFarmPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void setup() {
        BukkitConfiguration config = plugin.getConfig();
        if (config.getBoolean("Main.insects.enabled", false)) {
            plugin.logger().warning(" | Insects are disabled.");
            return;
        }
        BukkitFarmAPI.extractDefaultFolderFilesFromJarIfNoExist(plugin, "/insects/");

        File[] folder = new File(plugin.getDataFolder(), "/insects/").listFiles();
        if (folder == null)  {
            plugin.logger().warning(" | Insects folder is empty.");
            return;
        }

        for (File file : folder) {
            if (!file.getName().endsWith(".yml")) continue;

            final String id = file.getName().replace(".yml", "");
            plugin.logger().info("  | Insect of " + id + " loading...");

            BukkitConfiguration configuration = new BukkitConfiguration("insects/" + id, plugin);
            if (!configuration.isConfigurationSection("Insect")) {
                plugin.logger().warning("  | Insect of " + id + " has no 'Insect' section, please check your file.");
                continue;
            }

            Insect insect = fromSection(id, configuration.getConfigurationSection("Insect"));
            if (insect == null) continue;

            register(id, insect);
            plugin.logger().info(" | Insect of " + id + " loaded.");
        }
        plugin.logger().info(" | Amount of " + map().size() + " insects loaded. (" + Arrays.toString(map().keySet().toArray()) + ")");

    }

    @Override
    public @Nullable Insect fromSection(String id, ConfigurationSection section) {
        if (!BukkitFarmAPI.validateConfigurationKeyIsSet(plugin, section, "name", "  | ")) return null;
        String name = section.getString("name", "");

        if (!BukkitFarmAPI.validateConfigurationKeyIsSet(plugin, section, "entity", "  | ")) return null;
        String entity = section.getString("entity", "");

        return new Insect(
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