package me.alpho320.fabulous.farm.api.sprinkler;

import me.alpho320.fabulous.core.bukkit.util.BukkitConfiguration;
import me.alpho320.fabulous.farm.BukkitFarmAPI;
import me.alpho320.fabulous.farm.BukkitFarmPlugin;
import me.alpho320.fabulous.farm.FarmPlugin;
import me.alpho320.fabulous.farm.api.event.EventType;
import me.alpho320.fabulous.farm.api.event.action.EventAction;
import me.alpho320.fabulous.farm.api.mode.Mode;
import me.alpho320.fabulous.farm.api.sprinkler.animation.BukkitSprinklerAnimationManager;
import me.alpho320.fabulous.farm.api.sprinkler.animation.SprinklerAnimation;
import me.alpho320.fabulous.farm.api.sprinkler.animation.SprinklerAnimationManager;
import me.alpho320.fabulous.farm.util.item.ItemCreatorUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BukkitSprinklerManager extends SprinklerManager {

    private final @NotNull BukkitFarmPlugin plugin;
    private final @NotNull SprinklerAnimationManager animationManager;

    public BukkitSprinklerManager(@NotNull BukkitFarmPlugin plugin) {
        this.plugin = plugin;
        this.animationManager = new BukkitSprinklerAnimationManager(plugin);
    }

    @Override
    public void setup() {
        sprinklerAnimationManager().setup();

        plugin.logger().info(" | Sprinklers loading...");
        BukkitFarmAPI.extractDefaultFolderFilesFromJarIfNoExist(plugin, "/sprinklers/");

        File[] folder = new File(plugin.getDataFolder(), "/sprinklers/").listFiles();
        if (folder != null) {
            for (File file : folder) {
                if (file.getName().endsWith(".yml")) {
                    final String id = file.getName().replace(".yml", "");
                    plugin.logger().info("  | Sprinkler of " + id + " loading...");

                    BukkitConfiguration configuration = new BukkitConfiguration("sprinklers/" + id, plugin);
                    if (!configuration.isConfigurationSection("Sprinkler")) {
                        plugin.logger().warning("  | Sprinkler of " + id + " has no 'Sprinkler' section, please check your file.");
                        continue;
                    }

                    Sprinkler sprinkler = fromSection(id, configuration.getConfigurationSection("Sprinkler"));
                    if (sprinkler == null) continue;

                    register(id, sprinkler);
                    plugin.logger().info(" | Sprinkler of " + id + " loaded.");
                }
            }
            plugin.logger().info(" | Amount of " + map().size() + " sprinklers loaded. (" + Arrays.toString(map().keySet().toArray()) + ")");
        }


    }

    @Override
    public @Nullable Sprinkler fromSection(String id, ConfigurationSection section) {
        if (!BukkitFarmAPI.validateConfigurationKeyIsSet(plugin, section, "name", "   | ")) return null;
        String name = section.getString("name", "");

        if (!BukkitFarmAPI.validateConfigurationSection(plugin, section, "item", "   | ")) return null;
        ItemStack item = ItemCreatorUtil.getItemFromSection(section.getConfigurationSection("item"));

        if (!BukkitFarmAPI.validateConfigurationSection(plugin, section, "pots", "   | ")) return null;
        Mode mode = new Mode(Mode.Type.match(section.getString("pots.mode", "BLACKLIST")), section.getStringList("pots.list"));

        if (!BukkitFarmAPI.validateConfigurationKeyIsSet(plugin, section, "range", "   | ")) return null;
        int range = section.getInt("range", 0);

        Map<EventType, List<EventAction>> events = new HashMap<>();
        if (section.isConfigurationSection("events")) events = plugin.farmManager().eventActionManager().actionMapFromSection(section.getConfigurationSection("events"));

        if (!BukkitFarmAPI.validateConfigurationKeyIsSet(plugin, section, "max-water", "   | ")) return null;
        int maxWater = section.getInt("max-water", 0);

        if (!BukkitFarmAPI.validateConfigurationKeyIsSet(plugin, section, "fill-amount", "   | ")) return null;
        int fillAmount = section.getInt("fill-amount", 0);

        SprinklerAnimation animation = null;
        if (section.getBoolean("animation.enabled", false)) {
            if (!BukkitFarmAPI.validateConfigurationSection(plugin, section, "animation.type", "   | ")) return null;
            animation = plugin.farmManager().sprinklerManager().sprinklerAnimationManager().animationFromSection(section.getString("animation.type", ""), section.getConfigurationSection("animation"));
        }

        return new Sprinkler(id, name, item, mode, events, range, maxWater, fillAmount, animation);
    }

    @Override
    public @NotNull FarmPlugin plugin() {
        return this.plugin;
    }

    @Override
    public @NotNull SprinklerAnimationManager sprinklerAnimationManager() {
        return this.animationManager;
    }
}