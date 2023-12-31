package me.alpho320.fabulous.farm.api.crop;

import me.alpho320.fabulous.farm.FarmPlugin;
import me.alpho320.fabulous.farm.api.FarmManager;
import me.alpho320.fabulous.farm.api.event.EventType;
import me.alpho320.fabulous.farm.api.pot.PotHolder;
import me.alpho320.fabulous.farm.util.serializable.SerializableLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

    public @Nullable CropStage currentStage() {
        return crop.stages().get(growStage());
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
            final CropStage nextStage = crop.stages().getOrDefault(growStage()+1, null);

            if (nextStage == null) {
                plugin.logger().severe(" | Crop stage of '" + growStage() + "' not found for '" + crop.id() + "'!");
                plugin.logger().severe(" | Please check your crop stages. Add amount of " + crop.maxGrowStage() + " stages or decrease max grow stage.");
                return false;
            }
            setGrowStage(growStage() + 1);

            farmManager.cropManager().updateCropModel(this, nextStage.model());
            crop.growConditions().forEach(condition -> condition.remove(location(), pot, 1));

            farmManager.eventActionManager().checkActions(prevStage.events(), EventType.ON_GROW, location().loc());

            return true;
        } // todo: else increase insect chance.

        return false;
    }

}