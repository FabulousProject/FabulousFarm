package me.alpho320.fabulous.farm.api.scheduler;

import me.alpho320.fabulous.farm.api.sprinkler.Sprinkler;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class SchedulerManager {

    public abstract void setup();
    public abstract boolean enabled();

    public abstract @NotNull String time();

    /**
     * @return time in ticks of the day to check.
     * */
    public abstract long checkTime();
    public abstract void check();

    public abstract void checkSprinklers(@NotNull List<Sprinkler> sprinklers);

    public abstract void runTaskLater(@NotNull Runnable runnable, long delay);
    public abstract void runTaskLaterAsync(@NotNull Runnable runnable, long delay);


}