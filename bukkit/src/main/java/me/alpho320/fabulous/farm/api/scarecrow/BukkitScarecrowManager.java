package me.alpho320.fabulous.farm.api.scarecrow;

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

public class BukkitScarecrowManager extends ScarecrowManager {

    private final @NotNull BukkitFarmPlugin plugin;

    public BukkitScarecrowManager(@NotNull BukkitFarmPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void setup() {
        final BukkitConfiguration config = plugin.getConfig();
        if (!config.getBoolean("Main.scarecrows.enabled")) {
            plugin.logger().info(" | Scarecrows are disabled.");
            return;
        }

        plugin.logger().info(" | All scarecrows loading...");
        BukkitFarmAPI.extractDefaultFolderFilesFromJarIfNoExist(plugin, "/scarecrows/");

        File[] folder = new File(plugin.getDataFolder(), "/scarecrows/").listFiles();
        if (folder != null) {
            for (File file : folder) {
                if (file.getName().endsWith(".yml")) {
                    final String id = file.getName().replace(".yml", "");
                    plugin.logger().info("  | Scarecrow of " + id + " loading...");

                    BukkitConfiguration configuration = new BukkitConfiguration("scarecrows/" + id, plugin);
                    if (!configuration.isConfigurationSection("Scarecrow")) {
                        plugin.logger().warning("  | Scarecrow of " + id + " has no 'Scarecrow' section, please check your file.");
                        continue;
                    }

                    Scarecrow scarecrow = fromSection(id, configuration.getConfigurationSection("Scarecrow"));
                    if (scarecrow == null) continue;

                    register(id, scarecrow);
                    plugin.logger().info(" | Scarecrow of " + id + " loaded.");
                }
            }
            plugin.logger().info(" | Amount of " + map().size() + " scarecrows loaded. (" + map().keySet().toArray() + ")");
        }

    }

    @Override
    public @Nullable Scarecrow fromSection(String id, ConfigurationSection section) {
        if (!BukkitFarmAPI.validateConfigurationKeyIsSet(plugin, section, "name", "   | ")) return null;
        String name = section.getString("name", "");

        if (!BukkitFarmAPI.validateConfigurationKeyIsSet(plugin, section, "material", "   | ")) return null;
        ItemStack item = ItemCreatorUtil.getItemFromSection(section, "material");

        if (!BukkitFarmAPI.validateConfigurationKeyIsSet(plugin, section, "range", "   | ")) return null;
        int range = section.getInt("range", 0);

        return new Scarecrow(id, item, range);
    }

    @Override
    public @NotNull FarmPlugin plugin() {
        return this.plugin;
    }

}