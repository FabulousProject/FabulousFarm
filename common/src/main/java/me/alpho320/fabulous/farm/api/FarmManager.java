package me.alpho320.fabulous.farm.api;

import me.alpho320.fabulous.farm.FarmPlugin;
import me.alpho320.fabulous.farm.api.condition.ConditionManager;
import me.alpho320.fabulous.farm.api.crop.CropManager;
import me.alpho320.fabulous.farm.api.crow.CrowManager;
import me.alpho320.fabulous.farm.api.event.action.EventActionManager;
import me.alpho320.fabulous.farm.api.fertilizer.FertilizerManager;
import me.alpho320.fabulous.farm.api.greenhouse.GreenhouseManager;
import me.alpho320.fabulous.farm.api.pot.PotManager;
import me.alpho320.fabulous.farm.api.scarecrow.ScarecrowManager;
import me.alpho320.fabulous.farm.api.season.SeasonManager;
import me.alpho320.fabulous.farm.api.wateringcan.WateringCanManager;
import org.jetbrains.annotations.NotNull;

public abstract class FarmManager {

    protected CropManager cropManager;
    protected CrowManager crowManager;
    protected FertilizerManager fertilizerManager;
    protected GreenhouseManager greenhouseManager;
    protected PotManager potManager;
    protected ScarecrowManager scarecrowManager;
    protected SeasonManager seasonManager;
    protected EventActionManager eventActionManager;
    protected ConditionManager conditionManager;
    protected WateringCanManager wateringCanManager;

    public abstract void init(@NotNull FarmPlugin plugin);

    public @NotNull CropManager cropManager() {
        return this.cropManager;
    }

    public @NotNull CrowManager crowManager() {
        return this.crowManager;
    }

    public @NotNull FertilizerManager fertilizerManager() {
        return this.fertilizerManager;
    }

    public @NotNull GreenhouseManager greenhouseManager() {
        return this.greenhouseManager;
    }

    public @NotNull PotManager potManager() {
        return this.potManager;
    }

    public @NotNull ScarecrowManager scarecrowManager() {
        return this.scarecrowManager;
    }

    public @NotNull SeasonManager seasonManager() {
        return this.seasonManager;
    }

    public @NotNull EventActionManager eventActionManager() {
        return this.eventActionManager;
    }

    public ConditionManager conditionManager() {
        return this.conditionManager;
    }

    public @NotNull WateringCanManager wateringCanManager() {
        return this.wateringCanManager;
    }

}