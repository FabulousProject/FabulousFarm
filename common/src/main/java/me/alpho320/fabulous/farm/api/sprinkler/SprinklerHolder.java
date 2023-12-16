package me.alpho320.fabulous.farm.api.sprinkler;

import me.alpho320.fabulous.farm.FarmPlugin;
import me.alpho320.fabulous.farm.api.crop.CropHolder;
import me.alpho320.fabulous.farm.api.pot.PotHolder;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public class SprinklerHolder {

    private final @NotNull Sprinkler sprinkler;

    private @NotNull Sprinkler.State state = Sprinkler.State.IDLE;
    private final @NotNull Location location;

    private int water = 0;

    public SprinklerHolder(@NotNull Sprinkler sprinkler, @NotNull Sprinkler.State state, @NotNull Location location, int water) {
        this.sprinkler = sprinkler;
        this.state = state;
        this.location = location;
        this.water = water;
    }

    public SprinklerHolder(@NotNull Sprinkler sprinkler, @NotNull Location location) {
        this.sprinkler = sprinkler;
        this.location = location;
    }

    public @NotNull Sprinkler sprinkler() {
        return this.sprinkler;
    }

    public @NotNull Sprinkler.State state() {
        return this.state;
    }

    public @NotNull Location location() {
        return this.location.clone();
    }

    public void setState(@NotNull Sprinkler.State state) {
        this.state = state;
    }

    public void setWater(int water) {
        this.water = water;
    }

    public int water() {
        return this.water;
    }

    public void sprinkle(@NotNull FarmPlugin plugin) {
        setState(Sprinkler.State.WORKING);
        if (sprinkler.animation() != null) sprinkler.animation().animate(this);

        int range = sprinkler().range();
        final Location loc = location();

        for (int i = -range; i <= range; i++) {
            for (int j = -range; j <= range; j++) {
                Location potLoc = loc.add(i, -1, j); // -1 because of pot is on 1 block below.
                PotHolder pot = plugin.farmManager().potManager().findHolder(potLoc);

                if (pot == null) continue;
                if (pot.water() <= 0) {
                    plugin.farmManager().potManager().updatePotModel(pot, pot.pot().wetModel());
                    pot.setCurrentModel(pot.pot().wetModel());
                }
                pot.setWater(Math.min(pot.water() + sprinkler().fillAmount(), pot.pot().maxWater()));

            }
        }

        setWater(Math.max(water() - 1, 0));
        if (sprinkler.animation() == null) setState(Sprinkler.State.IDLE);
    }

}