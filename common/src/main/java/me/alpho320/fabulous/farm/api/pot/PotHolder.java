package me.alpho320.fabulous.farm.api.pot;

import me.alpho320.fabulous.farm.api.fertilizer.Fertilizer;
import me.alpho320.fabulous.farm.util.serializable.SerializableLocation;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PotHolder {

    private final @NotNull Pot pot;

    private final @NotNull List<Fertilizer> fertilizers;
    private final @NotNull SerializableLocation location;

    private @NotNull String currentModel;
    private int water;

    public PotHolder(@NotNull Pot pot, @NotNull List<Fertilizer> fertilizers, @NotNull SerializableLocation location, @NotNull String currentModel, int water) {
        this.pot = pot;
        this.fertilizers = fertilizers;
        this.location = location;
        this.currentModel = currentModel;
        this.water = water;
    }

    public @NotNull Pot pot() {
        return this.pot;
    }

    public @NotNull List<Fertilizer> fertilizers() {
        return this.fertilizers;
    }

    public @NotNull SerializableLocation location() {
        return this.location;
    }

    public @NotNull String currentModel() {
        return this.currentModel;
    }

    public void setCurrentModel(String currentModel) {
        this.currentModel = currentModel;
    }

    public int water() {
        return this.water;
    }

    public void setWater(int water) {
        this.water = water;
    }

}