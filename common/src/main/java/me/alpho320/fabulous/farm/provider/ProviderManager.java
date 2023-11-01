package me.alpho320.fabulous.farm.provider;

import me.alpho320.fabulous.farm.Callback;
import me.alpho320.fabulous.farm.FarmPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class ProviderManager {

    private final @NotNull FarmPlugin plugin;

    private final @NotNull Map<String, Provider> providers = new HashMap<>();
    private @NotNull Provider provider;

    public ProviderManager(@NotNull FarmPlugin plugin) {
        this.plugin = plugin;
    }

    public abstract void setup(boolean loadAllData, @Nullable Callback callback);

    public @Nullable Provider find(@NotNull String name) {
        return this.providers.getOrDefault(name, null);
    }

    public void register(@NotNull String id, @NotNull Provider provider) {
        Objects.requireNonNull(id, "Provider ID cannot be null!");
        Objects.requireNonNull(provider, "Provider cannot be null!");
        this.providers.put(id, provider);
    }

    public void register(@NotNull Provider...providers) {
        Arrays.stream(providers).forEach(provider -> register(provider.name(), provider));
    }

    public void unregister(@NotNull String id) {
        Objects.requireNonNull(id, "Provider ID cannot be null!");
        this.providers.remove(id);
    }

    public @NotNull Map<String, Provider> map() {
        return this.providers;
    }

    public void setProvider(@NotNull Provider provider) {
        Objects.requireNonNull(provider, "Provider cannot be null!");
        this.provider = provider;
    }

    public @NotNull Provider provider() {
        if (this.provider == null) throw new IllegalStateException("Provider is not initialized yet!");
        return this.provider;
    }

    public @NotNull FarmPlugin plugin() {
        return this.plugin;
    }


}