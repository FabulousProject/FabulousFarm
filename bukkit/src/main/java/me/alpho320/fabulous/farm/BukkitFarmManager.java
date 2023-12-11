package me.alpho320.fabulous.farm;

import me.alpho320.fabulous.farm.api.BukkitEventActionManager;
import me.alpho320.fabulous.farm.api.FarmManager;
import me.alpho320.fabulous.farm.api.bee.BukkitBeeManager;
import me.alpho320.fabulous.farm.api.condition.BukkitConditionManager;
import me.alpho320.fabulous.farm.api.crop.BukkitCropManager;
import me.alpho320.fabulous.farm.api.greenhouse.BukkitGreenhouseManager;
import me.alpho320.fabulous.farm.api.pots.BukkitPotManager;
import me.alpho320.fabulous.farm.api.scarecrow.BukkitScarecrowManager;
import me.alpho320.fabulous.farm.api.season.BukkitSeasonManager;
import me.alpho320.fabulous.farm.api.vermin.BukkitVerminManager;
import me.alpho320.fabulous.farm.api.wateringcan.BukkitWateringCanManager;
import org.jetbrains.annotations.NotNull;

public class BukkitFarmManager extends FarmManager {

    private final @NotNull BukkitFarmPlugin plugin;

    public BukkitFarmManager(@NotNull BukkitFarmPlugin plugin) {
        this.plugin = plugin;
        this.conditionManager = new BukkitConditionManager(plugin);
        this.eventActionManager = new BukkitEventActionManager(plugin);
        this.wateringCanManager = new BukkitWateringCanManager(plugin);
        this.beeManager = new BukkitBeeManager(plugin);
        this.cropManager = new BukkitCropManager(plugin);
        this.potManager = new BukkitPotManager(plugin);
        this.scarecrowManager = new BukkitScarecrowManager(plugin);
        this.greenhouseManager = new BukkitGreenhouseManager(plugin);
        this.seasonManager = new BukkitSeasonManager(plugin);
        this.verminManager = new BukkitVerminManager(plugin);
    }

    @Override
    public void setup() {
        conditionManager.setup();
        eventActionManager.setup();

        beeManager.setup();
        cropManager.setup();
        potManager.setup();
        wateringCanManager.setup();
        scarecrowManager.setup();
        greenhouseManager.setup();
        seasonManager.setup();
        verminManager.setup();
    }

}