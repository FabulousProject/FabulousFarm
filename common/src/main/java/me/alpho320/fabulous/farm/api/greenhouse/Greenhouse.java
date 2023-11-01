package me.alpho320.fabulous.farm.api.greenhouse;

import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class Greenhouse {

    private final @NotNull String id;

    private final @NotNull ItemStack item;
    private final int height;

    public Greenhouse(@NotNull String id, @NotNull ItemStack item, int height) {
        this.id = id;
        this.item = item;
        this.height = height;
    }

    public @NotNull String id() {
        return this.id;
    }

    public @NotNull ItemStack item() {
        return this.item.clone();
    }

    public int height() {
        return this.height;
    }

    public void give(@NotNull Player player, int amount) {
        NBTItem nbtItem = new NBTItem(item());
        nbtItem.setString("GreenHouseId", this.id());

        ItemStack item = nbtItem.getItem();
        item.setAmount(amount);

        player.getInventory().addItem(item.clone());
    }

}