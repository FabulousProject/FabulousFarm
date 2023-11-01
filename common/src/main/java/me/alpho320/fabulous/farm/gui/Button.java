package me.alpho320.fabulous.farm.gui;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Button {

    private final @NotNull String name;
    private final @NotNull ItemStack item;

    private final @NotNull String material;

    private final @NotNull String value;
    private final @NotNull String type;
    private final @NotNull String category;

    private @NotNull List<String> commands;

    public Button(@NotNull String name, @NotNull ItemStack item, @NotNull String material, @NotNull String value, @NotNull String type, @NotNull String category, @NotNull List<String> commands) {
        this.name = name;
        this.item = item;
        this.material = material;
        this.value = value;
        this.type = type;
        this.category = category;
        this.commands = commands;
    }

    public @NotNull String name() {
        return this.name;
    }

    public @NotNull ItemStack item() {
        return this.item.clone();
    }

    public @NotNull String material() {
        return this.material;
    }

    public @NotNull String value() {
        return this.value;
    }

    public @NotNull String type() {
        return this.type;
    }

    public @NotNull String category() {
        return this.category;
    }

    public @NotNull List<String> commands() {
        return this.commands;
    }

}