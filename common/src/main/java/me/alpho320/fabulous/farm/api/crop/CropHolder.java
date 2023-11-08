package me.alpho320.fabulous.farm.api.crop;

import me.alpho320.fabulous.farm.util.serializable.SerializableLocation;
import org.jetbrains.annotations.NotNull;

public class CropHolder {

    private final @NotNull Crop crop;
    private final @NotNull SerializableLocation location;

    private @NotNull String currentModel;
    private int growStage;

    public CropHolder(@NotNull Crop crop, @NotNull SerializableLocation location, @NotNull String currentModel, int growStage) {
        this.crop = crop;
        this.location = location;
        this.currentModel = currentModel;
        this.growStage = growStage;
    }

    public @NotNull Crop crop() {
        return this.crop;
    }

    public @NotNull SerializableLocation location() {
        return this.location;
    }

    public @NotNull String currentModel() {
        return this.currentModel;
    }

    public void setCurrentModel(@NotNull String currentModel) {
        this.currentModel = currentModel;
    }

    public int growStage() {
        return this.growStage;
    }

    public void setGrowStage(int growStage) {
        this.growStage = growStage; //todo update model-block etc.
    }


}