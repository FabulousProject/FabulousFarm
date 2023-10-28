package me.alpho320.fabulous.farm.inventory;

import me.alpho320.fabulous.core.bukkit.BukkitCore;
import me.alpho320.fabulous.core.bukkit.util.BukkitItemCreator;
import me.alpho320.fabulous.core.util.inv.smartinventory.Icon;
import me.alpho320.fabulous.core.util.inv.smartinventory.InventoryContents;
import me.alpho320.fabulous.core.util.inv.smartinventory.InventoryProvider;
import me.alpho320.fabulous.farm.FarmPlugin;
import me.alpho320.fabulous.farm.gui.Button;
import me.alpho320.fabulous.farm.gui.GUI;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.stream.Collectors;

public class MainInventoryProvider implements InventoryProvider {

    private final FarmPlugin plugin;
    private final GUI gui;

    public MainInventoryProvider(FarmPlugin plugin, GUI gui) {
        this.plugin = plugin;
        this.gui = gui;
        Particle.BL
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
                    .lore(FarmAPI.getItemLoreOrNew(button.getItem())
                            .stream()
                            .map(line -> BukkitCore.instance().message().coloredWithPlaceholders(player, line))
                            .collect(Collectors.toList()))
                    .create(player);

            int slot = entry.getKey();
            ItemStack clone = item.clone();

            FarmAPI.runSYNC(() -> {
                contents.set(slot, Icon.click(clone, event -> {
                    FarmAPI.executeCommands(player, button.getCommands());

                    if (button.getType().equalsIgnoreCase("exit") || button.getType().equalsIgnoreCase("back"))
                        FarmAPI.runSYNC(player::closeInventory);

                }));
            });

        }
    }

}