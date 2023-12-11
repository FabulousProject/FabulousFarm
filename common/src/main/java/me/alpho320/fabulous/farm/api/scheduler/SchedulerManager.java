package me.alpho320.fabulous.farm.api.scheduler;

import me.alpho320.fabulous.farm.api.sprinkler.Sprinkler;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class SchedulerManager {

    public abstract boolean enabled();

    public abstract void setup();

    public abstract @NotNull String time();
    public abstract void check();

    public abstract void checkSprinklers(@NotNull List<Sprinkler> sprinklers);


}