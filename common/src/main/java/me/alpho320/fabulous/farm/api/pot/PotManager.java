package me.alpho320.fabulous.farm.api.pot;

import me.alpho320.fabulous.farm.api.TypedManager;
import me.alpho320.fabulous.farm.api.crop.CropHolder;
import me.alpho320.fabulous.farm.util.serializable.SerializableLocation;
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

    public @Nullable PotHolder findPotHolder(@NotNull CropHolder cropHolder) {
        Location location = cropHolder.location().loc().clone().subtract(0, 1, 0); // pot always under the crop.
        return plugin().farmManager().potManager().findHolder(location);
    }

    public @Nullable PotHolder findHolder(@NotNull Location location) {
        return POT_HOLDERS.getOrDefault(location, null);
    }


    public @Nullable PotHolder findHolder(@NotNull SerializableLocation location) {
        return POT_HOLDERS.getOrDefault(location.loc(), null);
    }
}