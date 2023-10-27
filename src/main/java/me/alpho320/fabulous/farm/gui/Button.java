package me.alpho320.fabulous.farm.gui;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Button {

    private String name;
    private ItemStack item;

    private final String material;

    private final String value;
    private final String type;
    private final String category;

    private List<String> commands;

    public Button(String name, ItemStack item, String material, String value, String type, String category, List<String> commands) {
        this.name = name;
        this.item = item.clone();
        this.material = material;
        this.value = value;
        this.type = type;
        this.category = category;
        this.commands = commands;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemStack getItem() {
        return item.clone();
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public String getValue() {
        return value;
    }

    public List<String> getCommands() {
        return commands;
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }

    public String getMaterial() {
        return material;
    }

    public String getCategory() {
        return category;
    }

    public String getType() {
        return type;
    }
}