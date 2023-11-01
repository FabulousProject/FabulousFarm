package me.alpho320.fabulous.farm.api.crop;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class CropStar {

    private final int star;
    private final double chance;

    private final @NotNull ItemStack item;

    public CropStar(int star, double chance, @NotNull ItemStack item) {
        this.star = star;
        this.chance = chance;
        this.item = item;
    }

    public int star() {
        return this.star;
    }

    public double chance() {
        return this.chance;
    }

    public @NotNull ItemStack item() {
        return this.item.clone();
    }

}