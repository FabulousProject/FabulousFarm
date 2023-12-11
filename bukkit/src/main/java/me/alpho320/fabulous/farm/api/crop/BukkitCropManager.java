package me.alpho320.fabulous.farm.api.crop;

import me.alpho320.fabulous.core.bukkit.util.BukkitConfiguration;
import me.alpho320.fabulous.core.bukkit.util.FileUtil;
import me.alpho320.fabulous.farm.BukkitFarmAPI;
import me.alpho320.fabulous.farm.BukkitFarmPlugin;
import me.alpho320.fabulous.farm.FarmPlugin;
import me.alpho320.fabulous.farm.api.condition.Condition;
import me.alpho320.fabulous.farm.api.event.EventType;
import me.alpho320.fabulous.farm.api.event.action.EventAction;
import me.alpho320.fabulous.farm.util.item.ItemCreatorUtil;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BukkitCropManager extends CropManager {

    private final @NotNull BukkitFarmPlugin plugin;

    public BukkitCropManager(@NotNull BukkitFarmPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void setup() {
        try {
            plugin.logger().info(" | All crops loading...");
            BukkitFarmAPI.extractDefaultFolderFilesFromJarIfNoExist(plugin, "/crops/");

            File[] folder = new File(plugin.getDataFolder(), "/crops/").listFiles();
            if (folder != null) {
                for (File file : folder) {
                    if (file.getName().endsWith(".yml")) {
                        final String id = FileUtil.removeExtension(file.getName());
                        plugin.logger().info("  | Crop of " + id + " loading...");

                        BukkitConfiguration configuration = new BukkitConfiguration("/crops/" + id, plugin);
                        if (!configuration.isConfigurationSection("Crop")) {
                            plugin.logger().warning("  | Crop of " + id + " has no 'Crop' section, please check your file.");
                            continue;
                        }

                        Crop crop = fromSection(id, configuration.getConfigurationSection("Crop"));
                        if (crop == null) continue;

                        map().put(id, crop);
                        plugin.logger().info(" | Crop of " + id + " loaded.");
                    }
                }
                plugin.logger().info(" | Amount of " + map().size() + " crops loaded. (" + Arrays.toString(map().keySet().toArray()) + ")");
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
    public @Nullable Crop find(String id) {
        return map().getOrDefault(id, null);
    }

    @Override
    public @Nullable Crop fromSection(String id, ConfigurationSection section) {
        try {
            if (!BukkitFarmAPI.validateConfigurationSection(plugin, section, "seed-item", "   | ")) return null;
            ItemStack seedItem = ItemCreatorUtil.getItemFromSection(section.getConfigurationSection("seed-item"));

            if (!BukkitFarmAPI.validateConfigurationKeyIsSet(plugin, section, "death-model", "   | ")) return null;
            String deathModel = section.getString("death-model", "");

            if (!BukkitFarmAPI.validateConfigurationKeyIsSet(plugin, section, "max-grow-stage", "   | ")) return null;
            int maxGrowStage = section.getInt("max-grow-stage", 0);

            Map<EventType, List<EventAction>> actionsMap = new HashMap<>();
            if (section.isConfigurationSection("actions")) {
                actionsMap = plugin.farmManager().eventActionManager().actionMapFromSection(section.getConfigurationSection("actions"));
            }

            if (!BukkitFarmAPI.validateConfigurationSection(plugin, section, "grow-conditions", "   | ")) return null;
            List<Condition> conditions = plugin.farmManager().conditionManager().conditionsFromSection(section.getConfigurationSection("grow-conditions"));

            if (!BukkitFarmAPI.validateConfigurationSection(plugin, section, "stages", "   | ")) return null;
            Map<Integer, CropStage> stages = cropStagesFromSection(section.getConfigurationSection("stages"));

            Map<Integer, CropStar> stars = new HashMap<>();
            if (section.isConfigurationSection("stars")) {
                stars = cropStarsFromSection(section.getConfigurationSection("stars"));
            }

            return new Crop(id, seedItem, deathModel, maxGrowStage, actionsMap, conditions, stages, stars);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private @NotNull Map<Integer, CropStage> cropStagesFromSection(@NotNull ConfigurationSection section) {
        Map<Integer, CropStage> map = new HashMap<>();
        for (String key : section.getKeys(false)) {
            try {
                int stageId = Integer.parseInt(key);
                ConfigurationSection stageSection = section.getConfigurationSection(key);
                if (stageSection == null) continue;

                CropStage stage = cropStageFromSection(stageId, stageSection);
                if (stage == null) continue;

                map.put(stageId, stage);
            } catch (NumberFormatException e) {
                plugin.logger().warning("  | Crop stage id must be integer. (" + key + " is not integer)");
            }
        }
        if (!map.containsKey(1)) {
            plugin.logger().severe("  | Crop stage must have 1 stage. ('1' stage not found)");
            return new HashMap<>();
        }
        return map;
    }

    private @Nullable CropStage cropStageFromSection(int stageId, @NotNull ConfigurationSection section) {
        try {
            if (!BukkitFarmAPI.validateConfigurationKeyIsSet(plugin, section, "model", "   | ")) return null;
            return new CropStage(
                    stageId,
                    section.getString("model", ""),
                    plugin.farmManager().eventActionManager().actionMapFromSection(section.getConfigurationSection("events"))
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private @NotNull Map<Integer, CropStar> cropStarsFromSection(@NotNull ConfigurationSection section) {
        Map<Integer, CropStar> map = new HashMap<>();
        for (String key : section.getKeys(false)) {
            try {
                int starId = Integer.parseInt(key);
                ConfigurationSection stageSection = section.getConfigurationSection(key);
                if (stageSection == null) continue;

                CropStar staar = cropStarFromSection(starId, stageSection);
                if (staar == null) continue;

                map.put(starId, staar);
            } catch (NumberFormatException e) {
                plugin.logger().warning("  | Crop start id must be integer. (" + key + " is not integer)");
            }
        }
        return map;
    }

    private @Nullable CropStar cropStarFromSection(int starId, @NotNull ConfigurationSection section) {
        if (!BukkitFarmAPI.validateConfigurationKeyIsSet(plugin, section, "material", "   | ")) return null;
        return new CropStar(
                starId,
                section.getDouble("chance"),
                ItemCreatorUtil.getItemFromSection(section)
        );
    }

    @Override
    public @Nullable CropHolder plant(@NotNull Crop crop, @NotNull Location location) {
        return null;
    }

    @Override
    public @Nullable CropHolder plant(@NotNull String cropId, @NotNull Location location) {
        return null;
    }

}