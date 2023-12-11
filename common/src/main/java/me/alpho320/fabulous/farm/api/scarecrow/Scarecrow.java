package me.alpho320.fabulous.farm.api.scarecrow;

import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class Scarecrow {

    private final @NotNull String id;
    private final @NotNull ItemStack item;

    private final int range;

    public Scarecrow(@NotNull String id, @NotNull ItemStack item, int range) {
        this.id = id;
        this.item = item;
        this.range = range;
    }

    public @NotNull String id() {
        return this.id;
    }

    public @NotNull ItemStack item() {
        return this.item.clone();
    }

    public int range() {
        return this.range;
    }

    public void give(@NotNull Player player, int amount) {
        NBTItem nbtItem = new NBTItem(this.item());
        nbtItem.setString("ScarecrowId", this.id());

        ItemStack item = nbtItem.getItem();
        item.setAmount(amount);

        player.getInventory().addItem(item.clone());
    }

}