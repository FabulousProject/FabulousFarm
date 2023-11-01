package me.alpho320.fabulous.farm.api.season;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;

public class Season {

    private final @NotNull String id;
    private final boolean enabled;
    private final int days;

    private @Nullable LocalDateTime time;

    public Season(@NotNull String id, boolean enabled, int days) {
        this.id = id;
        this.enabled = enabled;
        this.days = days;
    }

    public @NotNull String id() {
        return this.id;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public int days() {
        return this.days;
    }

    /**
     * @return null means not started yet, left time for next season.
     */
    public @Nullable LocalDateTime time() {
        return this.time;
    }

    public void setTime(@Nullable LocalDateTime time) {
        this.time = time;
    }

}