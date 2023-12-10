package me.alpho320.fabulous.farm.inventory;

import me.alpho320.fabulous.core.bukkit.BukkitCore;
import me.alpho320.fabulous.core.bukkit.util.BukkitItemCreator;
import me.alpho320.fabulous.core.util.inv.smartinventory.Icon;
import me.alpho320.fabulous.core.util.inv.smartinventory.InventoryContents;
import me.alpho320.fabulous.core.util.inv.smartinventory.InventoryProvider;
import me.alpho320.fabulous.farm.BukkitFarmAPI;
import me.alpho320.fabulous.farm.BukkitFarmPlugin;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class ConfirmationInventoryProvider implements InventoryProvider {

    private final GUI gui;

    private final Runnable confirm;
    private final Runnable reject;

    private final List<String> confirmLore;
    private final List<String> rejectLore;

    private long delay = 1;

    public ConfirmationInventoryProvider(GUI gui, Runnable confirm, Runnable reject, List<String> confirmLore, List<String> rejectLore) {
        this.gui = gui;
        this.confirm = confirm;
        this.reject = reject;
        this.confirmLore = confirmLore;
        this.rejectLore = rejectLore;
    }

    public ConfirmationInventoryProvider(GUI gui, Runnable confirm, Runnable reject, List<String> confirmLore, List<String> rejectLore, long delay) {
        this.gui = gui;
        this.confirm = confirm;
        this.reject = reject;
        this.confirmLore = confirmLore;
        this.rejectLore = rejectLore;
        this.delay = delay;
    }

    @Override
    public void init(@NotNull InventoryContents contents) {
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    IntStream.rangeClosed(0, gui.getSize() * 9).forEach(i -> contents.set(i, Icon.cancel(new ItemStack(Material.AIR))));
                } catch (Exception ignored) {}
                Player player = contents.player();

                if (gui.isFrame())
                    contents.fillBorders(Icon.from(gui.getFrameItem()));

                for(Map.Entry<Integer, Button> entry : gui.getSlotMap().entrySet()) {
                    Button button = entry.getValue();

                    List<String> lore = new ArrayList<>();

                    for (String line : BukkitFarmAPI.getItemLoreOrNew(button.getItem())) {
                        if (line.contains("%confirm-lore%")) {
                            lore.addAll(confirmLore);
                        } else if (line.contains("%reject-lore%")) {
                            lore.addAll(rejectLore);
                        } else {
                            lore.add(BukkitCore.instance().message().coloredWithPlaceholders(player, line));
                        }
                    }

                    int slot = entry.getKey();
                    ItemStack clone = (ItemStack) new BukkitItemCreator()
                            .type(button.getMaterial())
                            .name(BukkitCore.instance().message().colored(button.getName()))
                            .damage(button.getItem().getDurability())
                            .lore(lore)
                            .create();

                    BukkitFarmAPI.runSYNC(() -> {
                        contents.set(slot, Icon.click(clone, clickEvent -> {
                            BukkitFarmAPI.executeCommands(player, button.getCommands());

                            if (button.getType().equalsIgnoreCase("confirm")) {
                                BukkitFarmAPI.runSYNC(confirm);
                            } else if (button.getType().equalsIgnoreCase("reject")) {
                                BukkitFarmAPI.runSYNC(reject);
                            }

                        }));
                    });

                }

            }
        }.runTaskLater(BukkitFarmPlugin.instance(), delay);
    }

}