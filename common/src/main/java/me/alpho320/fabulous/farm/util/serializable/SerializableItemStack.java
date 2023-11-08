package me.alpho320.fabulous.farm.util.serializable;

import me.alpho320.fabulous.farm.util.item.ItemCreatorUtil;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class SerializableItemStack implements Serializable {

    private final @NotNull String base64;

    public SerializableItemStack(@NotNull String base64) {
        this.base64 = base64;
    }

    public SerializableItemStack(@NotNull ItemStack stack) {
        ItemStack[] items = new ItemStack[1];
        items[0] = stack;
        this.base64 = ItemCreatorUtil.itemStackArrayToBase64(items);
    }

    public SerializableItemStack(SerializableItemStack stack) {
        this.base64 = stack.base64;
    }


    public @NotNull String base64() {
        return this.base64;
    }

    public @NotNull ItemStack toItemStack() {
        return ItemCreatorUtil.itemStackArrayFromBase64(base64)[0];
    }

    @Override
    public @NotNull SerializableItemStack clone() {
        return new SerializableItemStack(base64);
    }

}