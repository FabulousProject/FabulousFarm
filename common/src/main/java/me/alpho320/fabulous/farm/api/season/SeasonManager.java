package me.alpho320.fabulous.farm.api.season;

import me.alpho320.fabulous.farm.api.TypedManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class SeasonManager extends TypedManager<String, Season> {

    public @Nullable Season find(@NotNull String id) {
        return map().getOrDefault(id, null);
    }

}