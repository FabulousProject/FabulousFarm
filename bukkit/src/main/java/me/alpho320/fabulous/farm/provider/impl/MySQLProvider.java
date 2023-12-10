package me.alpho320.fabulous.farm.provider.impl;

import me.alpho320.fabulous.farm.BukkitFarmPlugin;
import me.alpho320.fabulous.farm.Callback;
import me.alpho320.fabulous.farm.FarmPlugin;
import me.alpho320.fabulous.farm.data.PlayerData;
import me.alpho320.fabulous.farm.provider.Provider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class MySQLProvider implements Provider {

    private final @NotNull BukkitFarmPlugin plugin;
    private final @NotNull Map<UUID, PlayerData> playerDataMap = new ConcurrentHashMap<>();

    public MySQLProvider(@NotNull BukkitFarmPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void init(@Nullable Callback callback) {

    }

    @Override
    public void close(@Nullable Callback callback) {

    }

    @Override
    public void loadAllData(boolean async, @Nullable Callback callback) {

    }

    @Override
    public void loadAllPlayerData(boolean async, @Nullable Callback callback) {

    }

    @Override
    public void loadPlayerData(@NotNull UUID id, boolean async, @Nullable Callback callback) {

    }

    @Override
    public void loadAllPotData(boolean async, @Nullable Callback callback) {

    }

    @Override
    public void loadPotData(@NotNull String id, boolean async, @Nullable Callback callback) {

    }

    @Override
    public void loadAllCropData(boolean async, @Nullable Callback callback) {

    }

    @Override
    public void loadCropData(@NotNull String id, boolean async, @Nullable Callback callback) {

    }

    @Override
    public void loadAllGreenhouseData(boolean async, @Nullable Callback callback) {

    }

    @Override
    public void loadGreenhouseData(@NotNull String id, boolean async, @Nullable Callback callback) {

    }

    @Override
    public void loadAllScarecrowData(boolean async, @Nullable Callback callback) {

    }

    @Override
    public void loadScarecrowData(@NotNull String id, boolean async, @Nullable Callback callback) {

    }

    @Override
    public void deletePlayerData(@NotNull UUID id, boolean async, @Nullable Callback callback) {

    }

    @Override
    public void deletePotData(@NotNull String id, boolean async, @Nullable Callback callback) {

    }

    @Override
    public void deleteCropData(@NotNull String id, boolean async, @Nullable Callback callback) {

    }

    @Override
    public void deleteGreenhouseData(@NotNull String id, boolean async, @Nullable Callback callback) {

    }

    @Override
    public void deleteScarecrowData(@NotNull String id, boolean async, @Nullable Callback callback) {

    }

    @Override
    public boolean isPlayerDataExists(@NotNull UUID id) {
        return false;
    }

    @Override
    public boolean isPotDataExists(@NotNull String id) {
        return false;
    }

    @Override
    public boolean isCropDataExists(@NotNull String id) {
        return false;
    }

    @Override
    public boolean isGreenhouseDataExists(@NotNull String id) {
        return false;
    }

    @Override
    public boolean isScarecrowDataExists(@NotNull String id) {
        return false;
    }

    @Override
    public void saveAllData(boolean async, @Nullable Callback callback) {

    }

    @Override
    public void saveAllPlayerData(boolean async, @Nullable Callback callback) {

    }

    @Override
    public void saveAllPotData(boolean async, @Nullable Callback callback) {

    }

    @Override
    public void saveAllCropData(boolean async, @Nullable Callback callback) {

    }

    @Override
    public void saveAllGreenhouseData(boolean async, @Nullable Callback callback) {

    }

    @Override
    public void saveAllScarecrowData(boolean async, @Nullable Callback callback) {

    }

    @Override
    public void savePlayerData(@NotNull UUID id, boolean async, @Nullable Callback callback) {

    }

    @Override
    public void savePotData(@NotNull String id, boolean async, @Nullable Callback callback) {

    }

    @Override
    public void saveCropData(@NotNull String id, boolean async, @Nullable Callback callback) {

    }

    @Override
    public void saveGreenhouseData(@NotNull String id, boolean async, @Nullable Callback callback) {

    }

    @Override
    public void saveScarecrowData(@NotNull String id, boolean async, @Nullable Callback callback) {

    }

    @Override
    public @NotNull Map<UUID, PlayerData> playerDataMap() {
        return this.playerDataMap;
    }

    @Override
    public @Nullable PlayerData findPlayerData(@NotNull UUID id) {
        return null;
    }

    @Override
    public @NotNull CompletableFuture<@Nullable PlayerData> findPlayerDataAsync(@NotNull UUID id) {
        return null;
    }

    @Override
    public @NotNull String name() {
        return "mysql";
    }
}