package me.alpho320.fabulous.farm.gui;

import me.alpho320.fabulous.core.bukkit.BukkitCore;
import me.alpho320.fabulous.core.util.inv.smartinventory.InventoryProvider;
import me.alpho320.fabulous.core.util.inv.smartinventory.Page;
import me.alpho320.fabulous.farm.FarmAPI;
import me.alpho320.fabulous.farm.FarmPlugin;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

public class GUIManager {

    private final @NotNull FarmPlugin plugin;
    private static GUIManager instance;

    public GUIManager(@NotNull FarmPlugin plugin) {
        this.plugin = plugin;
    }

    public void openGUI(Player player, InventoryProvider provider, String title, int row, boolean async, List<Interact> interacts) {
        openGUI(player, provider, title, row, async, interacts, 10L);
    }

    public void openGUI(Player player, InventoryProvider provider, String title, int row, boolean async, List<Interact> interacts, long startDelay) {
        openGUI(player, provider, title, row, async, interacts, startDelay, null);
    }

    public void openGUI(Player player, InventoryProvider provider, String title, int row, boolean async, List<Interact> interacts, long startDelay, Runnable whenClose) {
        Page page = Page.build(plugin.inventory(), provider)
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
        FarmAPI.runSYNC(() -> {
            int i = player.getOpenInventory().getTopInventory().getSize() / 9;

            if (i < page.row())
                page.open(player);
            else
                page.open(player, 0, new HashMap<>(), false);
        });
    }


    public static @NotNull GUIManager instance() {
        if (instance == null) throw new IllegalStateException("Plugin is not initialized yet!");
        return instance;
    }

}