package me.alpho320.fabulous.farm.configuration;

import me.alpho320.fabulous.core.bukkit.BukkitCore;
import me.alpho320.fabulous.core.bukkit.util.BukkitConfiguration;
import me.alpho320.fabulous.core.bukkit.util.FileUtil;
import me.alpho320.fabulous.core.util.inv.smartinventory.InventoryProvider;
import me.alpho320.fabulous.core.util.inv.smartinventory.Page;
import me.alpho320.fabulous.farm.BukkitFarmAPI;
import me.alpho320.fabulous.farm.BukkitFarmPlugin;
import me.alpho320.fabulous.farm.FarmPlugin;
import me.alpho320.fabulous.farm.gui.Button;
import me.alpho320.fabulous.farm.gui.GUI;
import me.alpho320.fabulous.farm.gui.GUIManager;
import me.alpho320.fabulous.farm.gui.Interact;
import me.alpho320.fabulous.farm.util.SoundUtil;
import me.alpho320.fabulous.farm.util.item.ItemCreatorUtil;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BukkitGUIManager extends GUIManager {

    private final @NotNull BukkitFarmPlugin plugin;

    public BukkitGUIManager(@NotNull BukkitFarmPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void setup() {
        FileUtil.createNewFile(plugin.getDataFolder().toString(), "/guis/x.yml", true);
        FileUtil.createNewFile(plugin.getDataFolder().toString(), "/animations/x.yml", true);

        new BukkitConfiguration("guis/confirmation-gui", plugin);
        new BukkitConfiguration("guis/main-gui", plugin);

        plugin.logger().debug(" | All guis loading...");
        loadGUIS(plugin, "/guis/");
    }

    @Override
    public @Nullable GUI find(String id) {
        return map().getOrDefault(id, null);
    }

    @Override
    public @Nullable GUI fromSection(String id, ConfigurationSection section) {
        return loadGUI(id, section);
    }

    @Override
    public void open(Player player, InventoryProvider provider, String title, int row, boolean async, List<Interact> interacts, long startDelay, Runnable whenClose) {
        Page page = Page.build(BukkitFarmPlugin.instance().inventory(), provider)
                .title(BukkitCore.instance().message().coloredWithPlaceholders(player, title))
                .row(row)
                .tick(2L)
                .startDelay(0)
                .async(async)
                .whenOpen(ev -> interacts.stream().filter(interact -> interact.getType().equals(Interact.InteractType.WHEN_OPEN)).forEach(interact -> interact.run(player)))
                .whenInit(ev -> interacts.stream().filter(interact -> interact.getType().equals(Interact.InteractType.WHEN_OPEN)).forEach(interact -> interact.run(player)))
                .whenClose(ev -> {
                    interacts.stream().filter(interact -> interact.getType().equals(Interact.InteractType.WHEN_CLOSE)).forEach(interact -> interact.run(player));
                    if (whenClose != null) whenClose.run();
                })
                .whenBottomClick(ev -> {
                    ev.cancel();
                    interacts.stream().filter(interact -> interact.getType().equals(Interact.InteractType.WHEN_BOTTOM_CLICK)).forEach(interact -> interact.run(player));
                })
                .whenOutsideClick(ev -> interacts.stream().filter(interact -> interact.getType().equals(Interact.InteractType.WHEN_OUTSIDE_CLICK)).forEach(interact -> interact.run(player)))
                .whenEmptyClick(ev -> {
                    InventoryClickEvent event = ev.getEvent();
                    ItemStack currentItem = event.getCurrentItem();

                    interacts.stream().filter(interact -> interact.getType().equals(Interact.InteractType.WHEN_EMPTY_CLICK)).forEach(interact -> interact.run(player));

                    if (currentItem == null || currentItem.getType().equals(Material.AIR) || (currentItem != null && event.isShiftClick())) {
                        Inventory topInventory = event.getView().getTopInventory();
                        if (event.getClickedInventory() != null && event.getClickedInventory().equals(topInventory)) {
                            ev.cancel();
                        } else if (event.getClickedInventory() != null && event.getClickedInventory().equals(event.getView().getBottomInventory()) && event.isShiftClick()) {
                            ev.cancel();
                        }
                    }
                })
                .whenTick(ev -> interacts.stream().filter(interact -> interact.getType().equals(Interact.InteractType.WHEN_UPDATE)).forEach(interact -> interact.run(player)));
        plugin.runSYNC(() -> {
            int i = player.getOpenInventory().getTopInventory().getSize() / 9;

            if (i < page.row())
                page.open(player);
            else
                page.open(player, 0, new HashMap<>(), false);
        });
    }


    public void loadGUIS(@NotNull BukkitFarmPlugin plugin, @NotNull String path) {

        File[] folder = new File(plugin.getDataFolder(), path).listFiles();
        if ((folder != null ? folder.length : 0) > 0) {
            Arrays.stream(folder)
                    .forEach(file -> {
                            try {
                                loadGUI(
                                        FileUtil.removeExtension(file.getName()),
                                        new BukkitConfiguration(path + FileUtil.removeExtension(file.getName()), plugin)
                                );
                            } catch (Exception e) {
                                e.printStackTrace();
                                plugin.logger().severe(" | Failed to load gui of " + file.getName() + "!");
                            }
                        }
                    );
        }

    }

    public @Nullable GUI loadGUI(String gui, ConfigurationSection configuration) {
        Map<Integer, Button> buttonMap = new HashMap<>();
        plugin.logger().info("  | Gui of " + gui + " loading..");

        for(String id : configuration.getConfigurationSection("GUI.slots").getKeys(false)) {
            ConfigurationSection section = configuration.getConfigurationSection("GUI.slots." + id);

            for(int i = 0; i <= (id.contains("iter-") ? (Integer.parseInt(id.split("-")[1].split(":")[1]) - Integer.parseInt(id.split("-")[1].split(":")[0])) : 0); i++) {
                plugin.logger().debug("   | Slot of " + (id.contains("iter-") ? (Integer.parseInt(id.split("-")[1].split(":")[0]) + i) : Integer.parseInt(id)) + "- Material: " + section.getString("material", "DEFAULT!"));
                buttonMap.put(
                        (id.contains("iter-") ? (Integer.parseInt(id.split("-")[1].split(":")[0]) + i) : Integer.parseInt(id)),
                        new Button(
                                BukkitCore.instance().message().colored(section.getString("name", "")),
                                ItemCreatorUtil.getItemFromSection(section, "material"),
                                section.getString("material", "AIR"),
                                section.getString("button-value", "null"),
                                section.getString("button-type", "BASIC"),
                                section.getString("open-category", "null"),
                                section.getStringList("commands")
                        )
                );
            }
        }
        plugin.logger().info("   | Amount of " + buttonMap.size() + " slots loaded!");

        List<Interact> interacts = new ArrayList<>();
        if (configuration.isConfigurationSection("GUI.interacts")) {
            plugin.logger().info("   | Interacts loading...");

            for (String interact : configuration.getConfigurationSection("GUI.interacts").getKeys(false)) {
                ConfigurationSection section = configuration.getConfigurationSection("GUI.interacts." + interact);
                plugin.logger().debug("   | Interact of " + interact + " loading...");
                interacts.add(
                        new Interact(
                                plugin,
                                section.getStringList("commands"),
                                SoundUtil.getSoundFromList(section.getStringList("sounds")),
                                Interact.InteractType.getType(interact)
                        )
                );
            }
            plugin.logger().info("   | Amount of " + interacts.size() + " interacts loaded.");
            plugin.logger().info(" ");
        }

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
                                .filter(button -> button.name().contains("%") || BukkitFarmAPI.getItemLoreOrNew(button.item()).contains("%"))
                                .count(),
                        interacts,
                        buttonMap
                )
        );

        plugin.logger().info(" ");
        return find(gui);
    }

    @Override
    public @NotNull FarmPlugin plugin() {
        return this.plugin;
    }

}