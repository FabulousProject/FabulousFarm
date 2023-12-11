package me.alpho320.fabulous.farm.api;

import me.alpho320.fabulous.farm.api.bee.BeeManager;
import me.alpho320.fabulous.farm.api.condition.ConditionManager;
import me.alpho320.fabulous.farm.api.crop.CropManager;
import me.alpho320.fabulous.farm.api.event.action.EventActionManager;
import me.alpho320.fabulous.farm.api.fertilizer.FertilizerManager;
import me.alpho320.fabulous.farm.api.greenhouse.GreenhouseManager;
import me.alpho320.fabulous.farm.api.pot.PotManager;
import me.alpho320.fabulous.farm.api.scarecrow.ScarecrowManager;
import me.alpho320.fabulous.farm.api.season.SeasonManager;
import me.alpho320.fabulous.farm.api.vermin.VerminManager;
import me.alpho320.fabulous.farm.api.wateringcan.WateringCanManager;
import org.jetbrains.annotations.NotNull;

public abstract class FarmManager {

    protected CropManager cropManager;
    protected VerminManager verminManager;
    protected FertilizerManager fertilizerManager;
    protected GreenhouseManager greenhouseManager;
    protected PotManager potManager;
    protected ScarecrowManager scarecrowManager;
    protected SeasonManager seasonManager;
    protected EventActionManager eventActionManager;
    protected ConditionManager conditionManager;
    protected WateringCanManager wateringCanManager;
    protected BeeManager beeManager;

    public abstract void setup();

    public @NotNull CropManager cropManager() {
        return this.cropManager;
    }

    public @NotNull VerminManager crowManager() {
        return this.verminManager;
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

    public @NotNull BeeManager beeManager() {
        return this.beeManager;
    }

}