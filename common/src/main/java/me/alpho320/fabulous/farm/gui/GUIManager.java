package me.alpho320.fabulous.farm.gui;

import me.alpho320.fabulous.core.util.inv.smartinventory.InventoryProvider;
import me.alpho320.fabulous.farm.api.TypedManager;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class GUIManager extends TypedManager<String, GUI> {

    public void open(Player player, InventoryProvider provider, String title, int row, boolean async, List<Interact> interacts) {
        open(player, provider, title, row, async, interacts, 10L);
    }

    public void open(Player player, InventoryProvider provider, String title, int row, boolean async, List<Interact> interacts, long startDelay) {
        open(player, provider, title, row, async, interacts, startDelay, null);
    }

    public abstract  void open(Player player, InventoryProvider provider, String title, int row, boolean async, List<Interact> interacts, long startDelay, Runnable whenClose);

}