package me.alpho320.fabulous.farm.api.wateringcan;

import me.alpho320.fabulous.core.bukkit.util.BukkitConfiguration;
import me.alpho320.fabulous.farm.BukkitFarmAPI;
import me.alpho320.fabulous.farm.BukkitFarmPlugin;
import me.alpho320.fabulous.farm.FarmPlugin;
import me.alpho320.fabulous.farm.api.event.EventType;
import me.alpho320.fabulous.farm.api.event.action.EventAction;
import me.alpho320.fabulous.farm.api.mode.Mode;
import me.alpho320.fabulous.farm.util.item.ItemCreatorUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BukkitWateringCanManager extends WateringCanManager {

    private final @NotNull BukkitFarmPlugin plugin;

    public BukkitWateringCanManager(@NotNull BukkitFarmPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void setup() {
        plugin.logger().info(" | All watering cans loading...");
        BukkitFarmAPI.extractDefaultFolderFilesFromJarIfNoExist(plugin, "/watering-cans/");

        File[] folder = new File(plugin.getDataFolder(), "/watering-cans/").listFiles();
        if (folder != null) {
            for (File file : folder) {
                if (file.getName().endsWith(".yml")) {
                    final String id = file.getName().replace(".yml", "");
                    plugin.logger().info("  | Watering can of " + id + " loading...");

                    BukkitConfiguration configuration = new BukkitConfiguration("/watering-cans/" + id, plugin);
                    if (!configuration.isConfigurationSection("Can")) {
                        plugin.logger().warning("  | Watering can of " + id + " has no 'Can' section, please check your file.");
                        continue;
                    }

                    WateringCan wateringCan = fromSection(id, configuration.getConfigurationSection("Can"));
                    if (wateringCan == null) continue;

                    map().put(id, wateringCan);
                    plugin.logger().info(" | Watering can of " + id + " loaded.");
                }
            }
            plugin.logger().info(" | Amount of " + map().size() + " watering cans loaded. (" + map().keySet().toArray() + ")");
        }

    }

    @Override
    public @NotNull FarmPlugin plugin() {
        return this.plugin;
    }

    @Override
    public @Nullable WateringCan fromSection(String id, ConfigurationSection section) {
        try {
            if (!BukkitFarmAPI.validateConfigurationKeyIsSet(plugin, section, "name", "  | ")) return null;
            String name = section.getString("name", "");

            if (!BukkitFarmAPI.validateConfigurationSection(plugin, section, "item", "  | ")) return null;
            ItemStack item = ItemCreatorUtil.getItemFromSection(section.getConfigurationSection("item"));

            if (!BukkitFarmAPI.validateConfigurationKeyIsSet(plugin, section, "max-water", "  | ")) return null;
            int maxWater = section.getInt("max-water", 0);

            if (!BukkitFarmAPI.validateConfigurationKeyIsSet(plugin, section, "range-width", "  | ")) return null;
            int rangeWidth = section.getInt("range-width", 0);

            if (!BukkitFarmAPI.validateConfigurationKeyIsSet(plugin, section, "range-height", "  | ")) return null;
            int rangeHeight = section.getInt("range-height", 0);

            if (!BukkitFarmAPI.validateConfigurationKeyIsSet(plugin, section, "pots.mode", "  | ")) return null;
            Mode potsMode = new Mode(Mode.Type.match(section.getString("pots.mode", "")), section.getStringList("pots.list"));

            if (!BukkitFarmAPI.validateConfigurationKeyIsSet(plugin, section, "sprinklers.mode", "  | ")) return null;
            Mode sprinklersMode = new Mode(Mode.Type.match(section.getString("sprinklers.mode", "")), section.getStringList("sprinklers.list"));

            Map<EventType, List<EventAction>> events = new HashMap<>();
            if (section.isConfigurationSection("events")) {
                events = plugin.farmManager().eventActionManager().actionMapFromSection(section.getConfigurationSection("events"));
            }

            return new WateringCan(id, name, item, maxWater, rangeWidth, rangeHeight, potsMode, sprinklersMode, events);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}