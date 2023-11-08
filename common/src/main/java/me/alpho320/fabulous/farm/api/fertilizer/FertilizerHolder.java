package me.alpho320.fabulous.farm.api.fertilizer;

import me.alpho320.fabulous.farm.util.serializable.SerializableLocation;
import org.jetbrains.annotations.NotNull;

public class FertilizerHolder {

    private final @NotNull Fertilizer fertilizer;
    private final @NotNull SerializableLocation location;

    public FertilizerHolder(@NotNull Fertilizer fertilizer, @NotNull SerializableLocation location) {
        this.fertilizer = fertilizer;
        this.location = location;
    }

    public @NotNull Fertilizer fertilizer() {
        return this.fertilizer;
    }

    public @NotNull SerializableLocation location() {
        return this.location;
    }

}