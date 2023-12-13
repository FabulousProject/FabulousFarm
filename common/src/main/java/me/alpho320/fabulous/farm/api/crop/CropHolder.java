package me.alpho320.fabulous.farm.api.crop;

import me.alpho320.fabulous.farm.FarmPlugin;
import me.alpho320.fabulous.farm.api.FarmManager;
import me.alpho320.fabulous.farm.api.event.EventType;
import me.alpho320.fabulous.farm.api.pot.PotHolder;
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
        this.growStage = growStage;
    }

    public boolean grow(@NotNull FarmPlugin plugin, boolean force) {
        final FarmManager farmManager = plugin.farmManager();
        final PotHolder pot = farmManager.potManager().findPotHolder(this);

        if (pot == null) {
            farmManager.cropManager().removeHolder(this);
            return false;
        }

        boolean conditions = farmManager.conditionManager().checkConditions(crop.growConditions(), location());
        if (!conditions && !force) return false;

        if (growStage() < crop.maxGrowStage()) {
            final CropStage prevStage = crop.stages().get(growStage());

            setGrowStage(growStage() + 1);
            CropStage stage = crop.stages().get(growStage());

            farmManager.cropManager().updateCropModel(this, stage.model());
            crop.growConditions().forEach(condition -> condition.remove(location(), pot, 1));

            farmManager.eventActionManager().checkActions(prevStage.events(), EventType.ON_GROW, location().loc());

            return true;
        } // todo: else increase insect chance.

        return false;
    }

}