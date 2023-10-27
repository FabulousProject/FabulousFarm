package me.alpho320.fabulous.farm.provider;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class Provider {

    //                PlayerID - Data
    private final Map<UUID, PlayerData> map = new HashMap<>();

    public abstract void init(@Nullable Callback callback);
    public abstract void close(@Nullable Callback callback);

    public abstract void loadAllData(boolean async, @Nullable Callback callback);
    public abstract void loadAllPlayerData(boolean async, @Nullable Callback callback);
    public abstract void loadPlayerData(@NotNull UUID id, boolean async, @Nullable Callback callback);

    public abstract void loadFarmRealmData(boolean async, @Nullable Callback callback);
    public abstract void loadFarmData(@NotNull UUID id, boolean async, @Nullable Callback callback);

    public abstract void deletePlayerData(@NotNull UUID id, boolean async, @Nullable Callback callback);
    public abstract void deleteFarmData(@NotNull UUID id, boolean async, @Nullable Callback callback);

    public abstract boolean isPlayerDataExists(@NotNull UUID id);
    public abstract boolean isFarmDataExists(@NotNull UUID id);

    public abstract void saveAllData(boolean async, @Nullable Callback callback);
    public abstract void saveAllPlayerData(boolean async, @Nullable Callback callback);
    public abstract void saveAllFarmData(boolean async, @Nullable Callback callback);

    public abstract void savePlayerData(@NotNull UUID id, boolean async, @Nullable Callback callback);
    public abstract void saveFarmData(@NotNull UUID id, boolean async, @Nullable Callback callback);

    public @Nullable PlayerData findPlayerData(@NotNull Player player) {
        return findPlayerData(player.getUniqueId());
    }

    public @Nullable PlayerData findPlayerData(@NotNull UUID uuid) {
        return map.getOrDefault(uuid, null);
    }

    public abstract @NotNull String name();

    public @NotNull Map<UUID, PlayerData> map() {
        return this.map;
    }

    public interface Callback {
        void complete(boolean state);
    }

    public void checkCallback(@Nullable Callback callback, boolean state) {
        if (callback != null)
            callback.complete(state);
    }

}