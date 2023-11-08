package me.alpho320.fabulous.farm.api.greenhouse;

import me.alpho320.fabulous.farm.util.serializable.SerializableLocation;
import org.jetbrains.annotations.NotNull;

public class GreenhouseHolder {

    private final @NotNull Greenhouse greenhouse;
    private final @NotNull SerializableLocation location;

    public GreenhouseHolder(@NotNull Greenhouse greenhouse, @NotNull SerializableLocation location) {
        this.greenhouse = greenhouse;
        this.location = location;
    }

    public @NotNull Greenhouse greenhouse() {
        return this.greenhouse;
    }

    public @NotNull SerializableLocation location() {
        return this.location;
    }

}