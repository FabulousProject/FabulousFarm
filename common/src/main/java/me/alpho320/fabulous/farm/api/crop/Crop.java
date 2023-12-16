package me.alpho320.fabulous.farm.api.crop;

import me.alpho320.fabulous.farm.api.condition.Condition;
import me.alpho320.fabulous.farm.api.event.EventType;
import me.alpho320.fabulous.farm.api.event.action.EventAction;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class Crop {

    private final @NotNull String id;
    private final @NotNull ItemStack seedItem;

    private final @NotNull String deathModel;
    private final int maxGrowStage;

    private final @NotNull Map<EventType, List<EventAction>> actions;
    private final @NotNull List<Condition> growConditions;

    private final @NotNull Map<Integer, CropStage> stages;
    private final @NotNull Map<Integer, CropStar> stars;

    public Crop(@NotNull String id, @NotNull ItemStack seedItem, @NotNull String deathModel, int maxGrowStage, @NotNull Map<EventType, List<EventAction>> actions, @NotNull List<Condition> growConditions, @NotNull Map<Integer, CropStage> stages, @NotNull Map<Integer, CropStar> stars) {
        this.id = id;
        this.seedItem = seedItem;
        this.deathModel = deathModel;
        this.maxGrowStage = maxGrowStage;
        this.actions = actions;
        this.growConditions = growConditions;
        this.stages = stages;
        this.stars = stars;
    }

    public @NotNull String id() {
        return this.id;
    }

    public @NotNull ItemStack seedItem() {
        return this.seedItem.clone();
    }

    public @NotNull String deathModel() {
        return this.deathModel;
    }

    public int maxGrowStage() {
        return this.maxGrowStage;
    }

    public @NotNull Map<EventType, List<EventAction>> actions() {
        return this.actions;
    }

    public @NotNull List<EventAction> actions(@NotNull EventType eventType) {
        return this.actions.getOrDefault(eventType, new ArrayList<>());
    }

    public @NotNull List<Condition> growConditions() {
        return this.growConditions;
    }

    public @NotNull Map<Integer, CropStage> stages() {
        return this.stages;
    }

    public @NotNull Map<Integer, CropStar> stars() {
        return this.stars;
    }

    public @Nullable CropStar star() {
        for (CropStar star : stars.values()) {
            if (ThreadLocalRandom.current().nextDouble(100) <= star.chance()) {
                return star;
            }
        }
        return null;
    }

}