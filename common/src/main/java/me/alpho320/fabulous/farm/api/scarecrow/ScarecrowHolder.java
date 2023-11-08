package me.alpho320.fabulous.farm.api.scarecrow;

import me.alpho320.fabulous.farm.util.serializable.SerializableLocation;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public class ScarecrowHolder {

    private final @NotNull Scarecrow scarecrow;
    private final @NotNull SerializableLocation location;

    public ScarecrowHolder(@NotNull Scarecrow scarecrow, @NotNull SerializableLocation location) {
        this.scarecrow = scarecrow;
        this.location = location;
    }

    public @NotNull Scarecrow scarecrow() {
        return this.scarecrow;
    }

    public @NotNull SerializableLocation location() {
        return this.location;
    }

    public boolean isInRange(@NotNull Location location) {
        if (!location.getWorld().getName().equals(location().world())) return false;

        int range = scarecrow().range();
        return (location().x() - location.getX()) <= range && (location().z() - location.getZ()) <= range;
    }

}