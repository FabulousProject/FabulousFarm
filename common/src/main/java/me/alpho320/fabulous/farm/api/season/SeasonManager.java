package me.alpho320.fabulous.farm.api.season;

import me.alpho320.fabulous.farm.api.TypedManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;

public abstract class SeasonManager extends TypedManager<String, Season> {

    private @NotNull Season currentSeason;
    private @NotNull LocalDateTime time; // time for next season.

    public @Nullable Season find(@NotNull String id) {
        return map().getOrDefault(id, null);
    }

    public @Nullable Season find(int order) {
        return map().values().stream()
                .filter(season -> season.order() == order)
                .findFirst().orElse(null);
    }

    /**
     * Check season and update if needed.
     * must be called on new day.
     * @return true if season updated.
     */
    public boolean checkSeason() {
        if (time.isBefore(LocalDateTime.now())) {
            currentSeason.setActive(false);
            Season nextSeason = find(currentSeason.order() + 1);
            if (nextSeason == null) nextSeason = find(1);

            nextSeason.setActive(true);
            setCurrentSeason(nextSeason);
            setTime(LocalDateTime.now().plusMinutes(nextSeason.days()* 20L)); // 20 minutes = 1 day.

            return true;
        }
        return false;
    }

    public void setCurrentSeason(@NotNull Season currentSeason) {
        this.currentSeason = currentSeason;
    }

    public @NotNull Season currentSeason() {
        return this.currentSeason;
    }

    public void setTime(@NotNull LocalDateTime time) {
        this.time = time;
    }

    public @NotNull LocalDateTime time() {
        return this.time;
    }

}