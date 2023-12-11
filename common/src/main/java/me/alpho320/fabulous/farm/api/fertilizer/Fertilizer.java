package me.alpho320.fabulous.farm.api.fertilizer;

import me.alpho320.fabulous.farm.api.event.EventType;
import me.alpho320.fabulous.farm.api.event.action.EventAction;
import me.alpho320.fabulous.farm.api.mode.Mode;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Fertilizer<Type> {

    private final @NotNull String id;
    private final @NotNull String name;

    private final @NotNull ItemStack item;
    private final @NotNull FertilizerType type;

    private final double chance;
    private final @NotNull Type value;

    private final @NotNull Map<EventType, List<EventAction>> events;
    
    private final @NotNull Mode potMode;

    public Fertilizer(@NotNull String id, @NotNull String name, @NotNull ItemStack item, @NotNull FertilizerType type, double chance, @NotNull Type value, @NotNull Map<EventType, List<EventAction>> events, @NotNull Mode potMode) {
        this.id = id;
        this.name = name;
        this.item = item;
        this.type = type;
        this.chance = chance;
        this.value = value;
        this.events = events;
        this.potMode = potMode;
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

    public @NotNull FertilizerType type() {
        return this.type;
    }

    public double chance() {
        return this.chance;
    }

    public @NotNull Type value() {
        return this.value;
    }

    public @NotNull Map<EventType, List<EventAction>> events() {
        return this.events;
    }

    public @NotNull List<EventAction> eventActions(@NotNull EventType eventType) {
        return this.events.getOrDefault(eventType, new ArrayList<>());
    }

    public void doAction(@NotNull EventType eventType, @NotNull Event event) {
        for (EventAction action : eventActions(eventType)) {
            action.execute(event);
        }
    }

    public @NotNull Mode potMode() {
        return this.potMode;
    }


    public boolean isPotUseable(@NotNull String potId) {
        return potMode.check(potId);
    }

}