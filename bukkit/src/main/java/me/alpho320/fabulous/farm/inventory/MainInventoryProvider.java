package me.alpho320.fabulous.farm.inventory;

import me.alpho320.fabulous.core.bukkit.BukkitCore;
import me.alpho320.fabulous.core.bukkit.util.BukkitItemCreator;
import me.alpho320.fabulous.core.util.inv.smartinventory.Icon;
import me.alpho320.fabulous.core.util.inv.smartinventory.InventoryContents;
import me.alpho320.fabulous.core.util.inv.smartinventory.InventoryProvider;
import me.alpho320.fabulous.farm.BukkitFarmAPI;
import me.alpho320.fabulous.farm.BukkitFarmPlugin;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.stream.Collectors;

public class MainInventoryProvider implements InventoryProvider {

    private final BukkitFarmPlugin plugin;
    private final GUI gui;

    public MainInventoryProvider(BukkitFarmPlugin plugin, GUI gui) {
        this.plugin = plugin;
        this.gui = gui;
    }

    @Override
    public void init(@NotNull InventoryContents contents) {
        Player player = contents.player();

        if (gui.isFrame())
            contents.fillBorders(Icon.from(gui.getFrameItem()));

        for(Map.Entry<Integer, Button> entry : gui.getSlotMap().entrySet()) {
            Button button = entry.getValue();

            ItemStack item = new BukkitItemCreator()
                    .type(button.getMaterial().equalsIgnoreCase("%player_head%") ? "head_" + player.getName() : button.getMaterial())
                    .name(BukkitCore.instance().message().colored(button.getName()))
                    .damage(button.getItem().getDurability())
                    .lore(BukkitFarmAPI.getItemLoreOrNew(button.getItem())
                            .stream()
                            .map(line -> BukkitCore.instance().message().coloredWithPlaceholders(player, line))
                            .collect(Collectors.toList()))
                    .create(player);

            int slot = entry.getKey();
            ItemStack clone = item.clone();

            BukkitFarmAPI.runSYNC(() -> {
                contents.set(slot, Icon.click(clone, event -> {
                    BukkitFarmAPI.executeCommands(player, button.getCommands());

                    if (button.getType().equalsIgnoreCase("exit") || button.getType().equalsIgnoreCase("back"))
                        BukkitFarmAPI.runSYNC(player::closeInventory);

                }));
            });

        }
    }

}