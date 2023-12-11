package me.alpho320.fabulous.farm.api.crop;

import me.alpho320.fabulous.farm.api.TypedManager;
import me.alpho320.fabulous.farm.util.serializable.SerializableLocation;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class CropManager extends TypedManager<String, Crop> {

    private final @NotNull Map<Location, CropHolder> CROP_HOLDERS = new ConcurrentHashMap<>();
    private final @NotNull Map<WeakReference<World>, List<CropHolder>> CROP_HOLDERS_BY_WORLD = new ConcurrentHashMap<>();

    public abstract @Nullable CropHolder plant(@NotNull Crop crop, @NotNull Location location);
    public abstract @Nullable CropHolder plant(@NotNull String cropId, @NotNull Location location);

    public @Nullable CropHolder findHolder(@NotNull Location location) {
        return this.CROP_HOLDERS.getOrDefault(location, null);
    }

    public @Nullable CropHolder findHolder(@NotNull SerializableLocation location) {
        return this.CROP_HOLDERS.getOrDefault(location.loc(), null);
    }

    public @NotNull Map<Location, CropHolder> cropHolders() {
        return this.CROP_HOLDERS;
    }

    public Map<WeakReference<World>, List<CropHolder>> cropHoldersByWorld() {
        return this.CROP_HOLDERS_BY_WORLD;
    }

    public @NotNull List<CropHolder> cropHoldersByWorld(@NotNull World world) {
        return this.CROP_HOLDERS_BY_WORLD.getOrDefault(new WeakReference<>(world), new ArrayList<>());
    }

}