package me.alpho320.fabulous.farm.api.crop;

import me.alpho320.fabulous.farm.api.TypedManager;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class CropManager extends TypedManager<String, Crop> {

    private final @NotNull Map<Location, CropHolder> CROP_HOLDERS = new ConcurrentHashMap<>();

    public @Nullable CropHolder findCropHolder(@NotNull Location location) {
        return this.CROP_HOLDERS.getOrDefault(location, null);
    }

    public @NotNull Map<Location, CropHolder> cropHolders() {
        return this.CROP_HOLDERS;
    }

}