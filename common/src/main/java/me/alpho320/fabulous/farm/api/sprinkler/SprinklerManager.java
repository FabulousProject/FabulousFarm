package me.alpho320.fabulous.farm.api.sprinkler;

import com.google.common.collect.ImmutableList;
import me.alpho320.fabulous.farm.api.TypedManager;
import me.alpho320.fabulous.farm.api.sprinkler.animation.SprinklerAnimationManager;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class SprinklerManager extends TypedManager<String, Sprinkler> {

    private final @NotNull Map<Location, SprinklerHolder> SPRINKLER_HOLDER_MAP = new ConcurrentHashMap<>();
    private final @NotNull Map<World, List<SprinklerHolder>> SPRINKLER_HOLDER_WORLD_MAP = new ConcurrentHashMap<>();

    @Override
    public @Nullable Sprinkler find(String id) {
        return map().getOrDefault(id, null);
    }

    public @Nullable SprinklerHolder findHolder(@NotNull Location location) {
        return SPRINKLER_HOLDER_MAP.getOrDefault(location, null);
    }

    public @NotNull ImmutableList<SprinklerHolder> sprinklerHolders() {
        return ImmutableList.copyOf(SPRINKLER_HOLDER_MAP.values());
    }

    public @NotNull ImmutableList<SprinklerHolder> sprinklerHoldersFromWorld(World world) {
        return ImmutableList.copyOf(SPRINKLER_HOLDER_WORLD_MAP.getOrDefault(world, ImmutableList.of()));
    }

    public void registerHolder(@NotNull SprinklerHolder sprinklerHolder) {
        SPRINKLER_HOLDER_MAP.put(sprinklerHolder.location(), sprinklerHolder);
        SPRINKLER_HOLDER_WORLD_MAP.computeIfAbsent(sprinklerHolder.location().getWorld(), world -> new ArrayList<>()).add(sprinklerHolder);
    }

    public void unregisterHolder(@NotNull SprinklerHolder sprinklerHolder) {
        SPRINKLER_HOLDER_MAP.remove(sprinklerHolder.location());
        SPRINKLER_HOLDER_WORLD_MAP.computeIfAbsent(sprinklerHolder.location().getWorld(), world -> new ArrayList<>()).remove(sprinklerHolder);
    }

    public void unregisterHolder(@NotNull Location location) {
        SPRINKLER_HOLDER_MAP.remove(location);
        SPRINKLER_HOLDER_WORLD_MAP.computeIfAbsent(location.getWorld(), world -> new ArrayList<>())
                .removeIf(sprinklerHolder -> sprinklerHolder.location().equals(location));
    }

    public abstract @NotNull SprinklerAnimationManager sprinklerAnimationManager();

}