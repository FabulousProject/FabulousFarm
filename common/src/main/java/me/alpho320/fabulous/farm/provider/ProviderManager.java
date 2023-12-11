package me.alpho320.fabulous.farm.provider;

import me.alpho320.fabulous.farm.Callback;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class ProviderManager {

    private @Nullable Provider provider;
    private final @NotNull Map<String, Provider> PROVIDERS = new HashMap<>();

    public abstract void setup(@NotNull ProviderLoadType type, @Nullable Callback callback);

    public void setProvider(@NotNull Provider provider) {
        Objects.requireNonNull(provider, "Provider cannot be null!");
        this.provider = provider;
    }

    public @NotNull Provider provider() {
        if (this.provider == null) throw new IllegalStateException("Provider is not initialized yet!");
        return this.provider;
    }

    public @Nullable Provider find(@NotNull String id) {
        return PROVIDERS.getOrDefault(id, null);
    }

    public void register(@NotNull String id, @NotNull Provider object) {
        Objects.requireNonNull(id, "id cannot be null!");
        Objects.requireNonNull(object, "object cannot be null!");

        PROVIDERS.put(id, object);
    }

    public void unregister(@NotNull String id) {
        map().remove(id);
    }

    public Map<String, Provider> map() {
        return this.PROVIDERS;
    }

    public void clear() {
        this.map().clear();
    }

    public enum ProviderLoadType {
        // ALL -> All data
        // ALL_FARMS -> All farms data (Pot, Crop, Greenhouse, Scarecrow)
        // PLAYER -> All players data
        ALL, ALL_FARMS, PLAYER;

        public static @NotNull ProviderLoadType fromString(@NotNull String string) {
            Objects.requireNonNull(string, "ProviderLoadType value cannot be null!");

            for (ProviderLoadType type : values()) {
                if (type.name().equalsIgnoreCase(string)) return type;
            }

            throw new IllegalArgumentException("ProviderLoadType with name '" + string + "' not found!");
        }

    }

}