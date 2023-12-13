package me.alpho320.fabulous.farm.api;

import me.alpho320.fabulous.farm.api.crop.Crop;
import me.alpho320.fabulous.farm.api.crop.CropHolder;
import me.alpho320.fabulous.farm.api.fertilizer.Fertilizer;
import me.alpho320.fabulous.farm.api.greenhouse.Greenhouse;
import me.alpho320.fabulous.farm.api.greenhouse.GreenhouseHolder;
import me.alpho320.fabulous.farm.api.pot.Pot;
import me.alpho320.fabulous.farm.api.pot.PotHolder;
import me.alpho320.fabulous.farm.api.scarecrow.Scarecrow;
import me.alpho320.fabulous.farm.api.scarecrow.ScarecrowHolder;
import me.alpho320.fabulous.farm.api.season.Season;
import me.alpho320.fabulous.farm.api.insect.Insect;
import me.alpho320.fabulous.farm.data.PlayerData;
import me.alpho320.fabulous.farm.hook.Hook;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface FarmAPI {

    @Nullable PotHolder findPotHolder(@NotNull Location location);
    @Nullable CropHolder findCropHolder(@NotNull Location location);
    @Nullable ScarecrowHolder findScarecrowHolder(@NotNull Location location);
    @Nullable GreenhouseHolder findGreenhouseHolder(@NotNull Location location);

    @NotNull Season currentSeason();

    @Nullable Pot findPot(@NotNull String id);
    @Nullable Crop findCrop(@NotNull String id);
    @Nullable Insect findCrow(@NotNull String id);
    @Nullable Season findSeason(@NotNull String id);
    @Nullable Scarecrow findScarecrow(@NotNull String id);
    @Nullable Fertilizer findFertilizer(@NotNull String id);
    @Nullable Greenhouse findGreenhouse(@NotNull String id);

    @Nullable Hook findHook(@NotNull String id);

    @Nullable PlayerData findPlayerData(String name);
    @Nullable PlayerData findPlayerData(UUID id);
    @NotNull CompletableFuture<@Nullable PlayerData> findPlayerData(@NotNull UUID id, boolean load);

    @NotNull String format(double i);

}