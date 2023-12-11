package me.alpho320.fabulous.farm.api.wateringcan;

import de.tr7zw.changeme.nbtapi.NBTItem;
import me.alpho320.fabulous.farm.api.event.EventType;
import me.alpho320.fabulous.farm.api.event.action.EventAction;
import me.alpho320.fabulous.farm.api.mode.Mode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class WateringCan {

    private final @NotNull String id;

    private final @NotNull String name;
    private final @NotNull ItemStack item;

    private final int maxWater;
    private final int rangeWidth;
    private final int rangeHeight;

    private final @NotNull Mode potsMode;
    private final @NotNull Mode sprinklersMode;

    private final @NotNull Map<EventType, List<EventAction>> events;

    public WateringCan(@NotNull String id, @NotNull String name, @NotNull ItemStack item, int maxWater, int rangeWidth, int rangeHeight, @NotNull Mode potsMode, @NotNull Mode sprinklersMode, @NotNull Map<EventType, List<EventAction>> events) {
        this.id = id;
        this.name = name;
        this.item = item;
        this.maxWater = maxWater;
        this.rangeWidth = rangeWidth;
        this.rangeHeight = rangeHeight;
        this.potsMode = potsMode;
        this.sprinklersMode = sprinklersMode;
        this.events = events;
    }

    public @NotNull String id() {
        return this.id;
    }

    public @NotNull String name() {
        return this.name;
    }

    public @NotNull ItemStack item() {
        return this.item.clone();
    }

    public int maxWater() {
        return this.maxWater;
    }

    public int rangeWidth() {
        return this.rangeWidth;
    }

    public int rangeHeight() {
        return this.rangeHeight;
    }

    public @NotNull Mode potsMode() {
        return this.potsMode;
    }

    public @NotNull Mode sprinklersMode() {
        return this.sprinklersMode;
    }

    public @NotNull Map<EventType, List<EventAction>> events() {
        return this.events;
    }

    public void give(@NotNull Player player) {
        NBTItem nbtItem = new NBTItem(item());
        nbtItem.setString("WateringCan", id());
        nbtItem.setInteger("Water", 0);
        nbtItem.setInteger("MaxWater", maxWater());

        player.getInventory().addItem(nbtItem.getItem());
    }

}