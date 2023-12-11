package me.alpho320.fabulous.farm.api.sprinkler;

import me.alpho320.fabulous.farm.api.TypedManager;
import me.alpho320.fabulous.farm.api.sprinkler.animation.SprinklerAnimationManager;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class SprinklerManager extends TypedManager<String, Sprinkler> {

    private final @NotNull Map<Location, SprinklerHolder> SPRINKLER_HOLDER_MAP = new ConcurrentHashMap<>();

    @Override
    public @Nullable Sprinkler find(String id) {
        return map().getOrDefault(id, null);
    }

    public @Nullable SprinklerHolder findHolder(@NotNull Location location) {
        return SPRINKLER_HOLDER_MAP.getOrDefault(location, null);
    }

    public @NotNull Map<Location, SprinklerHolder> sprinklerHolderMap() {
        return this.SPRINKLER_HOLDER_MAP;
    }

    public abstract @NotNull SprinklerAnimationManager sprinklerAnimationManager();

}