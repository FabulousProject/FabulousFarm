package me.alpho320.fabulous.farm.api.fertilizer;

import me.alpho320.fabulous.farm.api.pot.PotHolder;
import me.alpho320.fabulous.farm.util.serializable.SerializableLocation;
import org.jetbrains.annotations.NotNull;

public class FertilizerHolder {

    private final @NotNull Fertilizer fertilizer;
    private final @NotNull PotHolder potHolder;

    public FertilizerHolder(@NotNull Fertilizer fertilizer, @NotNull PotHolder potHolder) {
        this.fertilizer = fertilizer;
        this.potHolder = potHolder;
    }

    public @NotNull Fertilizer fertilizer() {
        return this.fertilizer;
    }

    public @NotNull PotHolder potHolder() {
        return this.potHolder;
    }

}