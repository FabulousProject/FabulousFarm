package me.alpho320.fabulous.farm.api.pot;

import me.alpho320.fabulous.farm.api.mode.Mode;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Pot {
    
    private final @NotNull String id;
    private final @NotNull String name;
    
    private final int maxWater;
    
    private final @NotNull String dryModel;
    private final @NotNull String wetModel;
    
    private final @NotNull Mode cropsMode;
    private final @NotNull List<String> cropsModeList;

    public Pot(@NotNull String id, @NotNull String name, int maxWater, @NotNull String dryModel, @NotNull String wetModel, @NotNull Mode cropsMode, @NotNull List<String> cropsModeList) {
        this.id = id;
        this.name = name;
        this.maxWater = maxWater;
        this.dryModel = dryModel;
        this.wetModel = wetModel;
        this.cropsMode = cropsMode;
        this.cropsModeList = cropsModeList;
    }

    public @NotNull String id() {
        return this.id;
    }

    public @NotNull String name() {
        return this.name;
    }

    public int maxWater() {
        return this.maxWater;
    }

    public @NotNull String dryModel() {
        return this.dryModel;
    }

    public @NotNull String wetModel() {
        return this.wetModel;
    }

    public @NotNull Mode cropsMode() {
        return this.cropsMode;
    }

    public @NotNull List<String> cropsModeList() {
        return this.cropsModeList;
    }

    public boolean canCropGrow(@NotNull String cropId) {
        return (cropsMode == Mode.WHITELIST) == cropsModeList.contains(cropId);
    }

}