package me.alpho320.fabulous.farm.provider;

import me.alpho320.fabulous.farm.FarmPlugin;
import me.alpho320.fabulous.farm.manager.AbstractDataManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public abstract class ProviderManager extends AbstractDataManager<String, Provider> {

    private @Nullable Provider provider;

    public ProviderManager(@NotNull FarmPlugin plugin) {
        super(plugin);
    }

    public void setProvider(@NotNull Provider provider) {
        Objects.requireNonNull(provider, "Provider cannot be null!");
        this.provider = provider;
    }

    public @NotNull Provider provider() {
        if (this.provider == null) throw new IllegalStateException("Provider is not initialized yet!");
        return this.provider;
    }

}