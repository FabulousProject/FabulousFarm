package me.alpho320.fabulous.farm.provider;

import me.alpho320.fabulous.farm.Callback;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface Provider {

    void init(@Nullable Callback callback);
    void close(@Nullable Callback callback);

    void loadAllData(boolean async, @Nullable Callback callback);
    void loadAllPlayerData(boolean async, @Nullable Callback callback);
    void loadPlayerData(@NotNull UUID id, boolean async, @Nullable Callback callback);

    void loadFarmRealmData(boolean async, @Nullable Callback callback);
    void loadFarmData(@NotNull UUID id, boolean async, @Nullable Callback callback);

    void deletePlayerData(@NotNull UUID id, boolean async, @Nullable Callback callback);
    void deleteFarmData(@NotNull UUID id, boolean async, @Nullable Callback callback);

    boolean isPlayerDataExists(@NotNull UUID id);
    boolean isFarmDataExists(@NotNull UUID id);

    void saveAllData(boolean async, @Nullable Callback callback);
    void saveAllPlayerData(boolean async, @Nullable Callback callback);
    void saveAllFarmData(boolean async, @Nullable Callback callback);

    void savePlayerData(@NotNull UUID id, boolean async, @Nullable Callback callback);
    void saveFarmData(@NotNull UUID id, boolean async, @Nullable Callback callback);

    @NotNull String name();

}