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
import me.alpho320.fabulous.farm.api.sprinkler.SprinklerManager;
import me.alpho320.fabulous.farm.api.insect.InsectManager;
import me.alpho320.fabulous.farm.api.wateringcan.WateringCanManager;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class FarmManager {

    protected CropManager cropManager;
    protected InsectManager insectManager;
    protected FertilizerManager fertilizerManager;
    protected GreenhouseManager greenhouseManager;
    protected PotManager potManager;
    protected ScarecrowManager scarecrowManager;
    protected SeasonManager seasonManager;
    protected EventActionManager eventActionManager;
    protected ConditionManager conditionManager;
    protected WateringCanManager wateringCanManager;
    protected BeeManager beeManager;
    protected SprinklerManager sprinklerManager;

    protected final @NotNull List<String> worldBlacklist = new ArrayList<>();

    public abstract void setup();

    public @NotNull CropManager cropManager() {
        return this.cropManager;
    }

    public @NotNull InsectManager crowManager() {
        return this.insectManager;
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

    public @NotNull SprinklerManager sprinklerManager() {
        return this.sprinklerManager;
    }

    public @NotNull List<String> worldBlacklist() {
        return this.worldBlacklist;
    }

}