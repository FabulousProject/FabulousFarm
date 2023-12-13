package me.alpho320.fabulous.farm.hook.type;

import me.alpho320.fabulous.farm.api.pot.PotHolder;
import org.jetbrains.annotations.NotNull;

public interface CanChangePotModel {

    boolean changePotModel(@NotNull PotHolder pot, @NotNull String model);

}