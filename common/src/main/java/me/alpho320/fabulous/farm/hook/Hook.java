package me.alpho320.fabulous.farm.hook;

import me.alpho320.fabulous.farm.Callback;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Hook {

    /**
     * Initialize hook.
     * @param callback
     * @return if load type is SETUP_PLUGIN_AFTER, return false and callback will be called when operations complete, otherwise return true.
     */
    void init(@Nullable Callback callback);

    @NotNull String name();
    @NotNull Hook.LoadType loadType();

    /**
     * @return if hook is initialized.
     */
    boolean initialized();
    boolean enabled();

    enum LoadType {
        ON_ENABLE, SETUP_PLUGIN_AFTER
    }

}