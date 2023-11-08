package me.alpho320.fabulous.farm.api.pot;

import me.alpho320.fabulous.farm.api.TypedManager;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class PotManager extends TypedManager<String, Pot> {

    private final @NotNull Map<Location, PotHolder> POT_HOLDERS = new ConcurrentHashMap<>();

    @Override
    public @Nullable Pot find(String id) {
        return map().getOrDefault(id, null);
    }

    public @Nullable PotHolder findHolder(@NotNull Location location) {
        return POT_HOLDERS.getOrDefault(location, null);
    }


}