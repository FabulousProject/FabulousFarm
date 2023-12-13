package me.alpho320.fabulous.farm.api.crop;

import com.google.common.collect.ImmutableList;
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

    public @NotNull ImmutableList<CropHolder> cropHolders() {
        return ImmutableList.copyOf(this.CROP_HOLDERS.values());
    }

    public @NotNull ImmutableList<CropHolder> cropHoldersFromWorld(@NotNull World world) {
        return ImmutableList.copyOf(this.CROP_HOLDERS_BY_WORLD.getOrDefault(new WeakReference<>(world), ImmutableList.of()));
    }

    public void registerHolder(@NotNull Location location, @NotNull CropHolder cropHolder) {
        this.CROP_HOLDERS.put(location, cropHolder);
        this.CROP_HOLDERS_BY_WORLD.computeIfAbsent(new WeakReference<>(location.getWorld()), world -> new ArrayList<>()).add(cropHolder);
    }

    public void unregisterHolder(@NotNull Location location) {
        this.CROP_HOLDERS.remove(location);
        this.CROP_HOLDERS_BY_WORLD.computeIfAbsent(new WeakReference<>(location.getWorld()), world -> new ArrayList<>())
                .removeIf(cropHolder -> cropHolder.location().loc().equals(location));
    }

}