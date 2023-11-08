package me.alpho320.fabulous.farm.api.crop;

import me.alpho320.fabulous.farm.api.event.EventType;
import me.alpho320.fabulous.farm.api.event.action.EventAction;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class CropStage {

    private final int stageId;

    private final @NotNull String model;
    private final @NotNull Map<EventType, List<EventAction>> events;

    public CropStage(int stageId, @NotNull String model, @NotNull Map<EventType, List<EventAction>> events) {
        this.stageId = stageId;
        this.model = model;
        this.events = events;
    }

    public int stageId() {
        return this.stageId;
    }

    public @NotNull String model() {
        return this.model;
    }

    public @NotNull Map<EventType, List<EventAction>> events() {
        return this.events;
    }

}