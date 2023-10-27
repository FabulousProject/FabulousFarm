package me.alpho320.fabulous.farm.hook;

import org.jetbrains.annotations.NotNull;

public interface Hook {

    boolean init();
    @NotNull String name();

    boolean enabled();
    void setEnabled(boolean enabled);

}