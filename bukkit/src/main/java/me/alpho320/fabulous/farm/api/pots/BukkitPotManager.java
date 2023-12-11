package me.alpho320.fabulous.farm.api.pots;

import me.alpho320.fabulous.core.bukkit.util.BukkitConfiguration;
import me.alpho320.fabulous.core.bukkit.util.FileUtil;
import me.alpho320.fabulous.farm.BukkitFarmAPI;
import me.alpho320.fabulous.farm.BukkitFarmPlugin;
import me.alpho320.fabulous.farm.FarmPlugin;
import me.alpho320.fabulous.farm.api.mode.Mode;
import me.alpho320.fabulous.farm.api.pot.Pot;
import me.alpho320.fabulous.farm.api.pot.PotManager;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class BukkitPotManager extends PotManager {

    private final @NotNull BukkitFarmPlugin plugin;

    public BukkitPotManager(@NotNull BukkitFarmPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void setup() {
        try {
            plugin.logger().info(" | All pots loading...");
            BukkitFarmAPI.extractDefaultFolderFilesFromJarIfNoExist(plugin, "/pots/");

            File[] folder = new File(plugin.getDataFolder(), "/pots/").listFiles();
            if (folder != null) {
                for (File file : folder) {
                    if (file.getName().endsWith(".yml")) {
                        final String id = FileUtil.removeExtension(file.getName());
                        plugin.logger().info("  | Pot of " + id + " loading...");

                        BukkitConfiguration configuration = new BukkitConfiguration("/pots/" + id, plugin);
                        if (!configuration.isConfigurationSection("Pot")) {
                            plugin.logger().warning("  | Pot of " + id + " has no 'Pot' section, please check your file.");
                            continue;
                        }

                        Pot pot = fromSection(id, configuration.getConfigurationSection("Pot"));
                        if (pot == null) continue;

                        map().put(id, pot);
                        plugin.logger().info(" | Pot of " + id + " loaded.");
                    }
                }
                plugin.logger().info(" | Amount of " + map().size() + " pots loaded. (" + Arrays.toString(map().keySet().toArray()) + ")");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public @NotNull FarmPlugin plugin() {
        return this.plugin;
    }

    @Override
    public @Nullable Pot fromSection(String id, ConfigurationSection section) {
        try {
            if (!BukkitFarmAPI.validateConfigurationKeyIsSet(plugin, section, "name", "   | ")) return null;
            String name = section.getString("name", "");

            if (!BukkitFarmAPI.validateConfigurationKeyIsSet(plugin, section, "max-water", "   | ")) return null;
            int maxWater = section.getInt("max-water", 0);

            if (!BukkitFarmAPI.validateConfigurationKeyIsSet(plugin, section, "model.dry", "   | ")) return null;
            String dryModel = section.getString("dry-model", "");

            if (!BukkitFarmAPI.validateConfigurationKeyIsSet(plugin, section, "model.wet", "   | ")) return null;
            String wetModel = section.getString("wet-model", "");

            if (!BukkitFarmAPI.validateConfigurationKeyIsSet(plugin, section, "crops.mode", "   | ")) return null;
            Mode cropsMode = new Mode(Mode.Type.match(section.getString("crops.mode", "")), section.getStringList("crops.list"));

            return new Pot(id, name, maxWater, dryModel, wetModel, cropsMode);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}