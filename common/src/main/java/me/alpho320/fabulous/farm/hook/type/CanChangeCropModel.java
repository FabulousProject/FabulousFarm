package me.alpho320.fabulous.farm.hook.type;

import me.alpho320.fabulous.farm.api.crop.CropHolder;
import org.jetbrains.annotations.NotNull;

public interface CanChangeCropModel {

    boolean changeCropModel(@NotNull CropHolder crop, @NotNull String model);
    boolean removeCropModel(@NotNull CropHolder crop);

}