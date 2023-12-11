package me.alpho320.fabulous.farm.api.scarecrow;

import me.alpho320.fabulous.farm.api.TypedManager;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class ScarecrowManager extends TypedManager<String, Scarecrow> {

    private final @NotNull Map<Location, ScarecrowHolder> SCARECROW_HOLDER_MAP = new ConcurrentHashMap<>();
    private boolean enabled = false;

    public @Nullable Scarecrow find(@NotNull String id) {
        return map().getOrDefault(id, null);
    }

    public @Nullable ScarecrowHolder findHolder(@NotNull Location location) {
        return SCARECROW_HOLDER_MAP.getOrDefault(location, null);
    }

    public @NotNull Map<Location, ScarecrowHolder> scarecrowHolderMap() {
        return SCARECROW_HOLDER_MAP;
    }

    public boolean enabled() {
        return this.enabled;
    }

}