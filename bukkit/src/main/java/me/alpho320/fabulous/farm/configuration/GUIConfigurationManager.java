package me.alpho320.fabulous.farm.configuration;

import me.alpho320.fabulous.core.bukkit.BukkitCore;
import me.alpho320.fabulous.core.bukkit.util.BukkitConfiguration;
import me.alpho320.fabulous.core.bukkit.util.FileUtil;
import me.alpho320.fabulous.core.bukkit.util.debugger.Debug;
import me.alpho320.fabulous.farm.FarmAPI;
import me.alpho320.fabulous.farm.FarmPlugin;
import me.alpho320.fabulous.farm.gui.Button;
import me.alpho320.fabulous.farm.gui.GUI;
import me.alpho320.fabulous.farm.gui.Interact;
import me.alpho320.fabulous.farm.util.SoundUtil;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GUIConfigurationManager {

    private static final Map<String, GUI> guiMap = new HashMap<>();

    public static void reload(FarmPlugin plugin) {
        FileUtil.createNewFile(plugin.getDataFolder().toString(), "/guis/x.yml", true);
        FileUtil.createNewFile(plugin.getDataFolder().toString(), "/animations/x.yml", true);

        new BukkitConfiguration("guis/confirmation-gui", plugin);
        new BukkitConfiguration("guis/main-gui", plugin);
        new BukkitConfiguration("guis/realm-banneds-gui", plugin);
        new BukkitConfiguration("guis/realm-deleted-realms-gui", plugin);
        new BukkitConfiguration("guis/realm-main-gui", plugin);
        new BukkitConfiguration("guis/realm-main-extra-gui", plugin);
        new BukkitConfiguration("guis/realm-main-extra2-gui", plugin);
        new BukkitConfiguration("guis/realm-manage-members-gui", plugin);
        new BukkitConfiguration("guis/realm-member-gui", plugin);
        new BukkitConfiguration("guis/realm-members-gui", plugin);
        new BukkitConfiguration("guis/realm-membership-gui", plugin);
        new BukkitConfiguration("guis/realm-settings-gui", plugin);
        new BukkitConfiguration("guis/realm-visitor-gui", plugin);
        new BukkitConfiguration("guis/realm-visitor-settings-gui", plugin);
        new BukkitConfiguration("guis/realms-gui", plugin);
        new BukkitConfiguration("guis/select-realm-gui", plugin);

        loadGUIS(plugin, "/guis/");
    }

    public static Map<String, GUI> map() {
        return guiMap;
    }

    public static void register(String id, GUI gui) {
        guiMap.put(id, gui);
    }

    public static @Nullable GUI find(String name) {
        return guiMap.getOrDefault(name, null);
    }

    public static void loadGUIS(@NotNull FarmPlugin plugin, @NotNull String path) {
        File[] folder = new File(plugin.getDataFolder(), path).listFiles();
        if ((folder != null ? folder.length : 0) > 0) {
            Arrays.stream(folder)
                    .forEach(file -> loadGUI(
                                    plugin,
                                    FileUtil.removeExtension(file.getName()),
                                    new BukkitConfiguration(path + FileUtil.removeExtension(file.getName()), plugin)
                            )
                    );
        }
    }

    public static void loadGUI(FarmPlugin plugin, String gui, BukkitConfiguration configuration) {
        Map<Integer, Button> buttonMap = new HashMap<>();

        Debug.debug(2, "----------------------------------------------------------------");
        Debug.debug(2, "Gui of " + gui + " loading..");

        for(String id : configuration.getConfigurationSection("GUI.slots").getKeys(false)) {
            ConfigurationSection section = configuration.getConfigurationSection("GUI.slots." + id);

            for(int i = 0; i <= (id.contains("iter-") ? (Integer.parseInt(id.split("-")[1].split(":")[1]) - Integer.parseInt(id.split("-")[1].split(":")[0])) : 0); i++) {
                Debug.debug(2, (id.contains("iter-") ? (Integer.parseInt(id.split("-")[1].split(":")[0]) + i) : Integer.parseInt(id)) + "- Material: " + section.getString("material", "DEFAULT!"));
                buttonMap.put(
                        (id.contains("iter-") ? (Integer.parseInt(id.split("-")[1].split(":")[0]) + i) : Integer.parseInt(id)),
                        new Button(
                                BukkitCore.instance().message().colored(section.getString("name", "")),
                                FarmAPI.getItemFromSection(plugin, section),
                                section.getString("material", "AIR"),
                                section.getString("button-value", "null"),
                                section.getString("button-type", "BASIC"),
                                section.getString("open-category", "null"),
                                section.getStringList("commands")
                        )
                );
            }
        }
        Debug.debug(2, buttonMap.size() + " slots loaded!");
        Debug.debug(2, " ");

        List<Interact> interacts = new ArrayList<>();
        if (configuration.isConfigurationSection("GUI.interacts")) {
            Debug.debug(2, "Interacts loading...");
            for (String interact : configuration.getConfigurationSection("GUI.interacts").getKeys(false)) {
                ConfigurationSection section = configuration.getConfigurationSection("GUI.interacts." + interact);
                Debug.debug(2, "Interact of " + interact + " loading...");
                interacts.add(
                        new Interact(
                                section.getStringList("commands"),
                                SoundUtil.getSoundFromList(section.getStringList("sounds")),
                                Interact.InteractType.getType(interact)
                        )
                );
            }
            Debug.debug(2, interacts.size() + " interacts loaded.");
            Debug.debug(2, " ");
        }

        Debug.debug(2, "frame-material: " + Material.matchMaterial(configuration.getString("GUI.frame-material", "AIR")));
        register(
                gui,
                new GUI(
                        gui,
                        configuration.getString("GUI.name", ""),
                        configuration.getInt("GUI.size", 1),
                        configuration.getBoolean("GUI.frame", false),
                        new ItemStack(Material.matchMaterial(configuration.getString("GUI.frame-material", "AIR")), 1, (short)configuration.getInt("GUI.frame-material-damage", 0)),
                        configuration.getBoolean("GUI.animation", false),
                        configuration.getString("GUI.animation-type", "null"),
                        (int) buttonMap.values()
                                .stream()
                                .filter(button -> button.getName().contains("%") || FarmAPI.getItemLoreOrNew(button.getItem()).contains("%"))
                                .count(),
                        interacts,
                        buttonMap
                )
        );
        Debug.debug(2, "Gui of " + gui + " loaded!");
        Debug.debug(2, "----------------------------------------------------------------");
        Debug.debug(2, " ");
    }


}