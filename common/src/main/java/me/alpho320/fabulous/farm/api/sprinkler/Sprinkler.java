package me.alpho320.fabulous.farm.api.sprinkler;

import me.alpho320.fabulous.farm.api.event.EventType;
import me.alpho320.fabulous.farm.api.event.action.EventAction;
import me.alpho320.fabulous.farm.api.mode.Mode;
import me.alpho320.fabulous.farm.api.sprinkler.animation.SprinklerAnimation;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class Sprinkler {

    private final @NotNull String id;

    private final @NotNull String name;
    private final @NotNull ItemStack item;
    private final @NotNull Mode potMode;

    private final @NotNull Map<EventType, List<EventAction>> events;

    private final int range;
    private final int maxWater;
    private final int fillAmount;

    private final @Nullable SprinklerAnimation animation;

    public Sprinkler(@NotNull String id, @NotNull String name, @NotNull ItemStack item, @NotNull Mode potMode, @NotNull Map<EventType, List<EventAction>> events, int range, int maxWater, int fillAmount, @Nullable SprinklerAnimation animation) {
        this.id = id;
        this.name = name;
        this.item = item;
        this.potMode = potMode;
        this.events = events;
        this.range = range;
        this.maxWater = maxWater;
        this.fillAmount = fillAmount;
        this.animation = animation;
    }

    public @NotNull String id() {
        return this.id;
    }

    public @NotNull String name() {
        return this.name;
    }

    public @NotNull ItemStack item() {
        return this.item;
    }

    public @NotNull Mode potMode() {
        return this.potMode;
    }

    public @NotNull Map<EventType, List<EventAction>> events() {
        return this.events;
    }

    public int range() {
        return this.range;
    }

    public int maxWater() {
        return this.maxWater;
    }

    public int fillAmount() {
        return this.fillAmount;
    }

    public @Nullable SprinklerAnimation animation() {
        return this.animation;
    }

    public enum State {
        WORKING, IDLE;
    }

}