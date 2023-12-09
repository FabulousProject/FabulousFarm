package me.alpho320.fabulous.farm.provider;

import me.alpho320.fabulous.farm.Callback;
import me.alpho320.fabulous.farm.data.PlayerData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface Provider {

    void init(@Nullable Callback callback);
    void close(@Nullable Callback callback);

    void loadAllData(boolean async, @Nullable Callback callback);

    void loadAllPlayerData(boolean async, @Nullable Callback callback);
    void loadPlayerData(@NotNull UUID id, boolean async, @Nullable Callback callback);

    void loadAllPotData(boolean async, @Nullable Callback callback);
    void loadPotData(@NotNull String id, boolean async, @Nullable Callback callback);

    void loadAllCropData(boolean async, @Nullable Callback callback);
    void loadCropData(@NotNull String id, boolean async, @Nullable Callback callback);

    void loadAllGreenhouseData(boolean async, @Nullable Callback callback);
    void loadGreenhouseData(@NotNull String id, boolean async, @Nullable Callback callback);

    void loadAllScarecrowData(boolean async, @Nullable Callback callback);
    void loadScarecrowData(@NotNull String id, boolean async, @Nullable Callback callback);

    void deletePlayerData(@NotNull UUID id, boolean async, @Nullable Callback callback);
    void deletePotData(@NotNull String id, boolean async, @Nullable Callback callback);
    void deleteCropData(@NotNull String id, boolean async, @Nullable Callback callback);
    void deleteGreenhouseData(@NotNull String id, boolean async, @Nullable Callback callback);
    void deleteScarecrowData(@NotNull String id, boolean async, @Nullable Callback callback);

    boolean isPlayerDataExists(@NotNull UUID id);
    boolean isPotDataExists(@NotNull String id);
    boolean isCropDataExists(@NotNull String id);
    boolean isGreenhouseDataExists(@NotNull String id);
    boolean isScarecrowDataExists(@NotNull String id);

    void saveAllData(boolean async, @Nullable Callback callback);
    void saveAllPlayerData(boolean async, @Nullable Callback callback);
    void saveAllPotData(boolean async, @Nullable Callback callback);
    void saveAllCropData(boolean async, @Nullable Callback callback);
    void saveAllGreenhouseData(boolean async, @Nullable Callback callback);
    void saveAllScarecrowData(boolean async, @Nullable Callback callback);

    void savePlayerData(@NotNull UUID id, boolean async, @Nullable Callback callback);
    void savePotData(@NotNull String id, boolean async, @Nullable Callback callback);
    void saveCropData(@NotNull String id, boolean async, @Nullable Callback callback);
    void saveGreenhouseData(@NotNull String id, boolean async, @Nullable Callback callback);
    void saveScarecrowData(@NotNull String id, boolean async, @Nullable Callback callback);

    @NotNull Map<UUID, PlayerData> playerDataMap();

    @Nullable PlayerData findPlayerData(@NotNull UUID id);
    @NotNull CompletableFuture<@Nullable PlayerData> findPlayerDataAsync(@NotNull UUID id);


    @NotNull String name();

}