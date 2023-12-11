package me.alpho320.fabulous.farm.api.greenhouse;

import me.alpho320.fabulous.core.bukkit.util.BukkitConfiguration;
import me.alpho320.fabulous.farm.BukkitFarmAPI;
import me.alpho320.fabulous.farm.BukkitFarmPlugin;
import me.alpho320.fabulous.farm.FarmPlugin;
import me.alpho320.fabulous.farm.util.item.ItemCreatorUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Arrays;

public class BukkitGreenhouseManager extends GreenhouseManager {

    private final @NotNull BukkitFarmPlugin plugin;

    public BukkitGreenhouseManager(@NotNull BukkitFarmPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void setup() {
        BukkitConfiguration config = plugin.getConfig();
        if (!config.getBoolean("Main.greenhouses.enabled")) {
            plugin.logger().warning(" | Greenhouses are disabled.");
            return;
        }
        plugin.logger().info(" | All greenhouses loading...");
        BukkitFarmAPI.extractDefaultFolderFilesFromJarIfNoExist(plugin, "/greenhouses/");

        File[] folder = new File(plugin.getDataFolder(), "/greenhouses/").listFiles();
        if (folder != null) {
            for (File file : folder) {
                if (file.getName().endsWith(".yml")) {
                    final String id = file.getName().replace(".yml", "");
                    plugin.logger().info("  | Greenhouse of " + id + " loading...");

                    BukkitConfiguration configuration = new BukkitConfiguration("greenhouses/" + id, plugin);
                    if (!configuration.isConfigurationSection("Greenhouse")) {
                        plugin.logger().warning("  | Greenhouse of " + id + " has no 'Greenhouse' section, please check your file.");
                        continue;
                    }

                    Greenhouse greenhouse = fromSection(id, configuration.getConfigurationSection("Greenhouse"));
                    if (greenhouse == null) continue;

                    register(id, greenhouse);
                    plugin.logger().info(" | Greenhouse of " + id + " loaded.");
                }
            }
            plugin.logger().info(" | Amount of " + map().size() + " greenhouses loaded. (" + Arrays.toString(map().keySet().toArray()) + ")");
        }

    }

    @Override
    public @Nullable Greenhouse fromSection(String id, ConfigurationSection section) {
        if (!BukkitFarmAPI.validateConfigurationKeyIsSet(plugin, section, "name", "   | ")) return null;
        String name = section.getString("name", "");

        if (!BukkitFarmAPI.validateConfigurationKeyIsSet(plugin, section, "material", "   | ")) return null;
        ItemStack item = ItemCreatorUtil.getItemFromSection(section, "material");

        if (!BukkitFarmAPI.validateConfigurationKeyIsSet(plugin, section, "height", "   | ")) return null;
        int height = section.getInt("height", 0);

        return new Greenhouse(id, item, height);
    }

    @Override
    public @NotNull FarmPlugin plugin() {
        return this.plugin;
    }

}