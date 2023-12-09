package me.alpho320.fabulous.farm.provider;

import me.alpho320.fabulous.farm.FarmPlugin;
import me.alpho320.fabulous.farm.data.PlayerData;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class BukkitProviderManager extends ProviderManager {

    public BukkitProviderManager(@NotNull FarmPlugin plugin) {
        super(plugin);
    }

    @Override
    public @NotNull CompletableFuture<Boolean> loadAll(boolean async) {
        return null;
    }

    @Override
    public @NotNull CompletableFuture<Boolean> unloadAll(boolean async) {
        return null;
    }

    @Override
    public @NotNull CompletableFuture<Boolean> saveAll(boolean async) {
        return null;
    }

    @Override
    public @NotNull CompletableFuture<@Nullable Provider> load(String s, boolean async) {
        return null;
    }

    @Override
    public @Nullable Provider load(String s) {
        return null;
    }

    @Override
    public @NotNull CompletableFuture<Boolean> unload(String s, boolean async) {
        return null;
    }

    @Override
    public @NotNull CompletableFuture<Boolean> save(String s, boolean async) {
        return null;
    }


}