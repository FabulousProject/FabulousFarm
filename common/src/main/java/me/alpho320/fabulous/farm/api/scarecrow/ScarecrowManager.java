package me.alpho320.fabulous.farm.api.scarecrow;

import me.alpho320.fabulous.farm.api.TypedManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ScarecrowManager extends TypedManager<String, Scarecrow> {

    public @Nullable Scarecrow find(@NotNull String id) {
        return map().getOrDefault(id, null);
    }

}